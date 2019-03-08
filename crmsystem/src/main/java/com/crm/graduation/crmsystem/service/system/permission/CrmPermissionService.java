package com.crm.graduation.crmsystem.service.system.permission;

import com.crm.graduation.crmsystem.dao.mapper.user.CrmPermissionMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmRolePermissionMapper;
import com.crm.graduation.crmsystem.entity.system.permission.CrmPermission;
import com.crm.graduation.crmsystem.entity.system.role.CrmRole;
import com.crm.graduation.crmsystem.entity.system.user.CrmRolePermission;
import com.crm.graduation.crmsystem.model.vo.system.permission.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 权限service
 */
@Service
public class CrmPermissionService {

    @Resource
    private CrmRolePermissionMapper rolePermissionMapper;

    @Resource
    private CrmPermissionMapper permissionMapper;

    /**
     * 根据角色id查询相应的权限集合
     */
    public Set<CrmPermission> getListPermissionByRoleId(List<CrmRole> roles){
        Set<CrmPermission> set = new HashSet<>();
        for(CrmRole role : roles){
            CrmRolePermission rolePermission = new CrmRolePermission();
            rolePermission.setRoleId(role.getRoleId());
            List<CrmRolePermission> rolePermissions = rolePermissionMapper.select(rolePermission);
            for(CrmRolePermission crmRolePermission : rolePermissions){
                CrmPermission permission = permissionMapper.selectByPrimaryKey(crmRolePermission.getPermissionId());
                set.add(permission);
            }
        }
        return set;
    }

    /**
     * 查询菜单
     */
    public List<MenuVO> getList() throws Exception{
        Example example = new Example(CrmPermission.class);
        example.createCriteria().andIsNull("permissionParentId");
        List<CrmPermission> permissions = permissionMapper.selectByExample(example);
        List<MenuVO> menus = new ArrayList<>();
        for(CrmPermission crmPermission : permissions){
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(crmPermission,menuVO);
            menuVO.setPermissionParentId("0");
            menus.add(menuVO);
        }
        for(MenuVO menuVO : menus){
            String partentId = menuVO.getPermissionId();
            //查询父id为的子集
            Example example1 = new Example(CrmPermission.class);
            example1.createCriteria().andEqualTo("permissionParentId",partentId);
            List<CrmPermission> childPermissions = permissionMapper.selectByExample(example1);
            List<MenuVO> childMenus = new ArrayList<>();
            for(CrmPermission crmPermission : childPermissions){
                MenuVO menuVO1 = new MenuVO();
                BeanUtils.copyProperties(crmPermission,menuVO1);
                childMenus.add(menuVO1);
            }
            menuVO.setChildPermission(childMenus);
        }
        return menus;
    }
}
