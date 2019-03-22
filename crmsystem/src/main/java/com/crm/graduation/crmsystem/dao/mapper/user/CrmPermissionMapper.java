package com.crm.graduation.crmsystem.dao.mapper.user;

import com.crm.graduation.crmsystem.entity.system.permission.CrmPermission;
import com.crm.graduation.crmsystem.model.utils.MyMapper;
import com.crm.graduation.crmsystem.model.vo.system.menu.MenuVo;

import java.util.List;

/**
 * 权限mapper
 */
public interface CrmPermissionMapper extends MyMapper<CrmPermission> {
    List<MenuVo> getAll(MenuVo menuVo);

    List<CrmPermission> getPaMenu();
}
