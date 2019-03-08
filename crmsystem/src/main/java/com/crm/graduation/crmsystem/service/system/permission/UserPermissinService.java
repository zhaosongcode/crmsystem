package com.crm.graduation.crmsystem.service.system.permission;

import com.crm.graduation.crmsystem.dao.mapper.user.CrmPermissionMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmRolePermissionMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserRoleMapper;
import com.crm.graduation.crmsystem.entity.system.permission.CrmPermission;
import com.crm.graduation.crmsystem.entity.system.user.CrmRolePermission;
import com.crm.graduation.crmsystem.entity.system.user.CrmUserRole;
import com.crm.graduation.crmsystem.model.vo.system.UserResourcesVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单权限service
 */
@Service
public class UserPermissinService {

    @Resource
    private CrmUserRoleMapper userRoleMapper;

    @Resource
    private CrmRolePermissionMapper crmRolePermissionMapper;

    @Resource
    private CrmPermissionMapper crmPermissionMapper;

    /**
     * 查询用户所对应的权限信息
     */
    public UserResourcesVo getUserPermission(String userId){
        UserResourcesVo userResourcesVo = new UserResourcesVo();
        List<String> roleIds = new ArrayList<>();
        userResourcesVo.setUserId(userId);
        userResourcesVo.setRoleIds(roleIds);
        CrmUserRole userRole = new CrmUserRole();
        userRole.setUserId(userId);
        Set<String> set = new HashSet<>();
        //所有的权限集合
        List<CrmPermission> permissions = new ArrayList<>();
        List<CrmUserRole> select = userRoleMapper.select(userRole);
        for(CrmUserRole crmUserRole : select){
            CrmRolePermission crmRolePermission = new CrmRolePermission();
            roleIds.add(crmUserRole.getRoleId());
            crmRolePermission.setRoleId(crmUserRole.getRoleId());
            List<CrmRolePermission> select1 = crmRolePermissionMapper.select(crmRolePermission);
            for(CrmRolePermission crmRolePermission1 : select1){
                set.add(crmRolePermission1.getPermissionId());
            }
        }
        for(String permissionId : set){
            CrmPermission crmPermission1 = crmPermissionMapper.selectByPrimaryKey(permissionId);
            permissions.add(crmPermission1);
        }
        //遍历出父菜单
        List<CrmPermission> menu = new ArrayList<>();
        List<CrmPermission> cmenu = new ArrayList<>();
        for(CrmPermission permission : permissions){
            String permissionParentId = permission.getPermissionParentId();
            if(permissionParentId==null || "".equals(permissionParentId)){
                menu.add(permission);
            }else{
                //子菜单
                cmenu.add(permission);
            }
        }
        //将子菜单找出
        for(CrmPermission crmPermission : menu){
            String permissionId = crmPermission.getPermissionId();
            List<CrmPermission> childMenu = new ArrayList<>();
            for(CrmPermission permission : cmenu){
                if(permissionId.equals(permission.getPermissionParentId())){
                    childMenu.add(permission);
                }
            }
            crmPermission.setChildPermission(childMenu);
        }
        userResourcesVo.setPermissions(menu);
        return userResourcesVo;
    }
}
