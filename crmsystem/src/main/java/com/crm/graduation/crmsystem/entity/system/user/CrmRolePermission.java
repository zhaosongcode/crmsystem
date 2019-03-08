package com.crm.graduation.crmsystem.entity.system.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色权限中间表
 */
@Getter
@Setter
@Table(name = "crm_role_permission")
public class CrmRolePermission {

    /**
     * 角色权限中间id
     */
    @Id
    @Column(name = "role_permission_id")
    private String rolePermissionId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 权限id
     */
    @Column(name = "permission_id")
    private String permissionId;
}
