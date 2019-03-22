package com.crm.graduation.crmsystem.service.system.menu;

import com.crm.graduation.crmsystem.dao.mapper.user.CrmPermissionMapper;
import com.crm.graduation.crmsystem.entity.system.permission.CrmPermission;
import com.crm.graduation.crmsystem.model.vo.system.menu.MenuVo;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CrmSystemMenuService {

    @Resource
    private CrmPermissionMapper crmPermissionMapper;

    /**
     * 查询列表
     * @param
     * @return
     */
    public List<MenuVo> getList(MenuVo menuVo)throws Exception {
        List<MenuVo> menuVOS = crmPermissionMapper.getAll(menuVo);
        for(MenuVo menuVo1 : menuVOS){
            String paPermissionId = menuVo1.getPaMenu();
            if(paPermissionId != null && !"".equals(paPermissionId.trim())){
                CrmPermission crmPermission = crmPermissionMapper.selectByPrimaryKey(paPermissionId);
                menuVo1.setPaMenu(crmPermission.getPermissionName());
            }
        }
        return menuVOS;
    }

    /**
     * 单个删除
     * @param split
     * @return
     */
    public String dele(String[] split) {
        String mess = "fail";
        String permissionId = split[0];
        CrmPermission crmPermission = crmPermissionMapper.selectByPrimaryKey(permissionId);
        if(crmPermission != null){
            crmPermissionMapper.deleteByPrimaryKey(permissionId);
            mess = "success";
        }
        return mess;
    }

    /**
     * 批量删除
     * @param split
     * @return
     */
    public String deles(String[] split) {
        String mess = "fail";
        for(String permissionId : split){
            CrmPermission crmPermission = crmPermissionMapper.selectByPrimaryKey(permissionId);
            if(crmPermission != null){
                crmPermissionMapper.deleteByPrimaryKey(permissionId);
            }
        }
        mess = "success";
        return mess;
    }

    /**
     * 查询父菜单
     * @return
     */
    public List<CrmPermission> getPaMenu() {
        List<CrmPermission> permissions = crmPermissionMapper.getPaMenu();
        return permissions;
    }

    /**
     * 保存
     * @param menuVo
     * @return
     */
    public String save(MenuVo menuVo) {
        CrmPermission crmPermission = new CrmPermission();
        crmPermission.setPermissionId(Tools.get32UUID());
        String paMenu = menuVo.getPaMenu();
        if(paMenu != null && !"".equals(paMenu.trim())){
            CrmPermission crmPermission1 = crmPermissionMapper.selectByPrimaryKey(paMenu);
            if(crmPermission1 != null){
                crmPermission.setPermissionParentId(paMenu);
                crmPermission.setPermissionUrl(menuVo.getMenuUrl());
            }
        }
        crmPermission.setPermissionIcon(menuVo.getMenuIcon());
        crmPermission.setPermissionName(menuVo.getMenuName());
        crmPermissionMapper.insert(crmPermission);
        String mess = "success";
        return mess;
    }

    /**
     * 根据id查询菜单
     * @param menuId
     * @return
     */
    public MenuVo getPermissionById(String menuId) {
        CrmPermission crmPermission = crmPermissionMapper.selectByPrimaryKey(menuId);
        MenuVo menuVo = new MenuVo();
        menuVo.setMenuId(crmPermission.getPermissionId());
        menuVo.setMenuIcon(crmPermission.getPermissionIcon());
        menuVo.setMenuName(crmPermission.getPermissionName());
        menuVo.setMenuUrl(crmPermission.getPermissionUrl());
        menuVo.setPaMenu(crmPermission.getPermissionParentId());
        return menuVo;
    }

    /**
     * 修改动作
     * @param menuVo
     * @return
     */
    public String editDo(MenuVo menuVo) {
        String mess = "fail";
        String permissionId = menuVo.getMenuId();
        CrmPermission crmPermission = crmPermissionMapper.selectByPrimaryKey(permissionId);
        if(crmPermission != null){
            crmPermission.setPermissionName(menuVo.getMenuName());
            crmPermission.setPermissionIcon(menuVo.getMenuIcon());
            String paMenu = menuVo.getPaMenu();
            if(paMenu != null && !"".equals(paMenu.trim())){
                CrmPermission crmPermission1 = crmPermissionMapper.selectByPrimaryKey(paMenu);
                if(crmPermission1 != null){
                    crmPermission.setPermissionParentId(paMenu);
                    crmPermission.setPermissionUrl(menuVo.getMenuUrl());
                }
            }else{
                crmPermission.setPermissionParentId(null);
                crmPermission.setPermissionUrl(null);
            }
            crmPermissionMapper.updateByPrimaryKey(crmPermission);
            mess = "success";
        }
        return mess;
    }
}
