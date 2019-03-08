package com.crm.graduation.crmsystem.model.vo.system.permission;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuVO {

    private String permissionId;
    private String permissionParentId;
    private String permissionName;
    private String permissionIcon;
    private List<MenuVO> childPermission;
}
