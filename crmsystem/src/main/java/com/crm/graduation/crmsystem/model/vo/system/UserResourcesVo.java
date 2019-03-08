package com.crm.graduation.crmsystem.model.vo.system;

import com.crm.graduation.crmsystem.entity.system.permission.CrmPermission;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 用户权限菜单信息
 */
@Getter
@Setter
public class UserResourcesVo implements Serializable{

    private String userId;
    private List<String> roleIds;
    private List<CrmPermission> permissions;
}
