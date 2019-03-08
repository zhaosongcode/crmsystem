package com.crm.graduation.crmsystem.service.system.role;

import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmPermissionMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmRoleMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmRolePermissionMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserRoleMapper;
import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.entity.system.permission.CrmPermission;
import com.crm.graduation.crmsystem.entity.system.role.CrmRole;
import com.crm.graduation.crmsystem.entity.system.user.CrmRolePermission;
import com.crm.graduation.crmsystem.entity.system.user.CrmUserRole;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.system.permission.AddRoleVO;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色service
 */
@Service
public class CrmRoleService {

    @Resource
    private CrmUserRoleMapper userRoleMapper;

    @Resource
    private CrmRoleMapper roleMapper;

    @Resource
    private CrmPermissionMapper crmPermissionMapper;

    @Resource
    private CrmRolePermissionMapper crmRolePermissionMapper;

    @Resource
    private CrmUserRoleMapper crmUserRoleMapper;

    @Resource
    private CrmDictMapper crmDictMapper;

    /**
     * 根据用户id查询角色集合
     */
    public List<CrmRole> getListRoleByUserId(String userId) throws Exception{
        CrmUserRole crmUserRole = new CrmUserRole();
        crmUserRole.setUserId(userId);
        List<CrmUserRole> userRoles = userRoleMapper.select(crmUserRole);
        List<CrmRole> roles = new ArrayList<>();
        for(CrmUserRole userRole : userRoles){
            CrmRole crmRole = roleMapper.selectByPrimaryKey(userRole.getRoleId());
            roles.add(crmRole);
        }
        return roles;
    }

    /**
     * 查询所有角色
     */
    public List<CrmRole> getList(CrmRole crmRole)throws Exception{
        List<CrmRole> crmRoles = null;
        if(crmRole != null){
            if(crmRole.getRoleName()!=null&&!"".equals(crmRole.getRoleName())){
                Example example = new Example(CrmRole.class);
                example.createCriteria().andLike("roleName","%"+crmRole.getRoleName()+"%");
                crmRoles = roleMapper.selectByExample(example);
            }else{
                crmRoles = roleMapper.selectAll();
            }
        }else{
            crmRoles = roleMapper.selectAll();
        }

        return crmRoles;
    }

    /**
     * 删除角色
     */
    public String deleteRole(String roleIds)throws Exception{
        String[] split = roleIds.split(",");
        String message = "fail";
        if(split.length>1){
            for(String roleId : split){
                CrmRole crmRole = roleMapper.selectByPrimaryKey(roleId);
                if(crmRole!=null){
                    roleMapper.deleteByPrimaryKey(roleId);
                    CrmRolePermission crmRolePermission = new CrmRolePermission();
                    crmRolePermission.setRoleId(roleId);
                    List<CrmRolePermission> select = crmRolePermissionMapper.select(crmRolePermission);
                    for(CrmRolePermission crmRolePermission1 : select){
                        crmRolePermissionMapper.deleteByPrimaryKey(crmRolePermission1);
                    }
                    CrmUserRole crmUserRole = new CrmUserRole();
                    crmUserRole.setRoleId(roleId);
                    CrmUserRole crmUserRole1 = crmUserRoleMapper.selectOne(crmUserRole);
                    if(crmUserRole1!=null){
                        crmUserRoleMapper.deleteByPrimaryKey(crmUserRole1);
                    }
                    //删除字典
                    CrmDataDict crmDataDict = crmDictMapper.selectByPrimaryKey(roleId);
                    if(crmDataDict!=null){
                        crmDictMapper.deleteByPrimaryKey(crmDataDict);
                    }
                }
            }
            message = "success";
        }else{
            CrmRole crmRole = roleMapper.selectByPrimaryKey(roleIds);
            if(crmRole!=null){
                //删除与其相关联的数据
                String roleId = crmRole.getRoleId();
                roleMapper.deleteByPrimaryKey(roleIds);
                CrmRolePermission crmRolePermission = new CrmRolePermission();
                crmRolePermission.setRoleId(roleId);
                List<CrmRolePermission> select = crmRolePermissionMapper.select(crmRolePermission);
                for(CrmRolePermission crmRolePermission1 : select){
                    crmRolePermissionMapper.deleteByPrimaryKey(crmRolePermission1);
                }
                CrmUserRole crmUserRole = new CrmUserRole();
                crmUserRole.setRoleId(roleId);
                CrmUserRole crmUserRole1 = crmUserRoleMapper.selectOne(crmUserRole);
                if(crmUserRole1!=null){
                    crmUserRoleMapper.deleteByPrimaryKey(crmUserRole1);
                }
                //删除字典
                CrmDataDict crmDataDict = crmDictMapper.selectByPrimaryKey(roleId);
                if(crmDataDict!=null){
                    crmDictMapper.deleteByPrimaryKey(crmDataDict);
                }
                message = "success";
            }
        }
        return message;
    }

    /**
     * 新增操作
     */
    public String addDo(AddRoleVO addRoleVO)throws Exception{
        String message = "fail";
        String roleName = addRoleVO.getRoleName();
        if(roleName!=null&&!"".equals(roleName)){
            CrmRole crmRole = new CrmRole();
            crmRole.setRoleId(Tools.get32UUID());
            crmRole.setRoleName(roleName);
            //保存角色
            roleMapper.insert(crmRole);
            //同时更新字典
            CrmDataDict crmDataDict = new CrmDataDict();
            crmDataDict.setDictCode(Consts.DICT_STAFF_TYPE_CODE);
            CrmDataDict crmDataDict1 = crmDictMapper.selectOne(crmDataDict);
            String dictId = crmDataDict1.getDictId();
            CrmDataDict dataDict = new CrmDataDict();
            dataDict.setParentId(dictId);
            dataDict.setDictId(crmRole.getRoleId());
            //排序
            CrmDataDict count = new CrmDataDict();
            count.setParentId(dictId);
            int i = crmDictMapper.selectCount(count);
            i++;
            String dictCode = (new DecimalFormat("00")).format(i);
            dataDict.setDictCode(dictCode);
            dataDict.setDictSort(i);
            dataDict.setDictName(crmRole.getRoleName());
            crmDictMapper.insert(dataDict);
            //处理权限关系
            if(addRoleVO.getPermissionIds()!=null){
                String permissionIds = addRoleVO.getPermissionIds();
                if(!"".equals(permissionIds)){
                    String[] split = permissionIds.split(",");
                    for(String permissionId : split){
                        CrmRolePermission crmRolePermission = new CrmRolePermission();
                        crmRolePermission.setRolePermissionId(Tools.get32UUID());
                        crmRolePermission.setRoleId(crmRole.getRoleId());
                        crmRolePermission.setPermissionId(permissionId);
                        crmRolePermissionMapper.insert(crmRolePermission);
                        CrmPermission crmPermission = crmPermissionMapper.selectByPrimaryKey(permissionId);
                        String permissionParentId = crmPermission.getPermissionParentId();
                        CrmRolePermission rolePermission = new CrmRolePermission();
                        rolePermission.setRoleId(crmRole.getRoleId());
                        rolePermission.setPermissionId(permissionParentId);
                        CrmRolePermission partenRolePermission = crmRolePermissionMapper.selectOne(rolePermission);
                        if(partenRolePermission==null){
                            CrmRolePermission permission = new CrmRolePermission();
                            permission.setRolePermissionId(Tools.get32UUID());
                            permission.setPermissionId(permissionParentId);
                            permission.setRoleId(crmRole.getRoleId());
                            crmRolePermissionMapper.insert(permission);
                        }
                    }
                }
            }
            message = "success";
        }
        return message;
    }

    /**
     * 查询一个角色的相关信息
     */
    public AddRoleVO getRole(String roleId)throws Exception{
        //查询角色信息
        CrmRolePermission crmRolePermission = new CrmRolePermission();
        crmRolePermission.setRoleId(roleId);
        List<CrmRolePermission> rolePermissions = crmRolePermissionMapper.select(crmRolePermission);
        //拼接permissionId字符串
        StringBuffer perIds = new StringBuffer();
        for(CrmRolePermission rolePermission : rolePermissions){
            String permissionId = rolePermission.getPermissionId();
            //去除父菜单id
            CrmPermission crmPermission = crmPermissionMapper.selectByPrimaryKey(permissionId);
            if(crmPermission.getPermissionParentId()!=null && !"".equals(crmPermission.getPermissionParentId())){
                perIds.append(permissionId+",");
            }
        }
        String permissionIds = new String(perIds);
        AddRoleVO addRoleVO = new AddRoleVO();
        addRoleVO.setPermissionIds(permissionIds);
        //角色名称查询
        CrmRole crmRole = roleMapper.selectByPrimaryKey(roleId);
        if(crmRole!= null){
            String roleName = crmRole.getRoleName();
            addRoleVO.setRoleName(roleName);
        }
        addRoleVO.setRoleId(roleId);
        return addRoleVO;
    }

    /**
     * 执行修改保存
     */
    public String editDo(AddRoleVO addRoleVO)throws Exception{
        String message = "fail";
        //首先修改角色表
        if(addRoleVO != null){
            if(addRoleVO.getRoleId() != null){
                String roleId = addRoleVO.getRoleId();
                CrmRole crmRole = roleMapper.selectByPrimaryKey(roleId);
                if(crmRole != null){
                    if(addRoleVO.getRoleName() != null){
                        String roleName = addRoleVO.getRoleName();
                        crmRole.setRoleName(roleName);
                        roleMapper.updateByPrimaryKey(crmRole);
                        //其次做与其相关的数据修改
                        if(addRoleVO.getPermissionIds() != null){
                            String permissionIds = addRoleVO.getPermissionIds();
                            //首先修改数据字典
                            CrmDataDict crmDataDict = crmDictMapper.selectByPrimaryKey(roleId);
                            if(crmDataDict != null){
                                crmDataDict.setDictName(roleName);
                                crmDictMapper.updateByPrimaryKey(crmDataDict);
                            }
                            //修改crm_role_permission
                            //首先全部删除其中的role_permission关系
                            CrmRolePermission crmRolePermission = new CrmRolePermission();
                            crmRolePermission.setRoleId(roleId);
                            List<CrmRolePermission> rolePermissions = crmRolePermissionMapper.select(crmRolePermission);
                            for(CrmRolePermission rolePermission : rolePermissions){
                                crmRolePermissionMapper.deleteByPrimaryKey(rolePermission);
                            }
                            //然后添加
                            String[] split = permissionIds.split(",");
                            for(String permissionId : split){
                                //根据子id查询出父id
                                CrmPermission crmPermission = crmPermissionMapper.selectByPrimaryKey(permissionId);
                                if(crmPermission != null){
                                    String permissionParentId = crmPermission.getPermissionParentId();
                                    CrmRolePermission crmRolePermissionPartent = new CrmRolePermission();
                                    crmRolePermissionPartent.setPermissionId(permissionParentId);
                                    crmRolePermissionPartent.setRoleId(roleId);
                                    CrmRolePermission crmRolePermissionPartentReal = crmRolePermissionMapper.selectOne(crmRolePermissionPartent);
                                    //如果父菜单不存在则创建
                                    if(crmRolePermissionPartentReal == null){
                                        crmRolePermissionPartentReal = new CrmRolePermission();
                                        crmRolePermissionPartentReal.setPermissionId(permissionParentId);
                                        crmRolePermissionPartentReal.setRoleId(roleId);
                                        crmRolePermissionPartentReal.setRolePermissionId(Tools.get32UUID());
                                        crmRolePermissionMapper.insert(crmRolePermissionPartentReal);
                                    }
                                    //继续执行子菜单添加
                                    CrmRolePermission crmRolePermissionChild = new CrmRolePermission();
                                    crmRolePermissionChild.setRoleId(roleId);
                                    crmRolePermissionChild.setRolePermissionId(Tools.get32UUID());
                                    crmRolePermissionChild.setPermissionId(permissionId);
                                    crmRolePermissionMapper.insert(crmRolePermissionChild);
                                }
                            }
                            message = "success";
                        }
                    }
                }
            }
        }
        return message;
    }

}
