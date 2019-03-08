package com.crm.graduation.crmsystem.entity.system.permission;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 权限
 */
@Getter
@Setter
@Table(name = "crm_permission")
public class CrmPermission implements Serializable {

    /**
     * 权限id
     */
    @Id
    @Column(name = "permission_id")
    private String permissionId;

    /**
     * 父权限id
     */
    @Column(name = "permission_parent_id")
    private String permissionParentId;

    /**
     * 权限名称
     */
    @Column(name = "permission_name")
    private String permissionName;

    /**
     * 权限路径
     */
    @Column(name = "permission_url")
    private String permissionUrl;

    /**
     * 子权限
     */
    @Transient
    private List<CrmPermission> childPermission;

    /**
     * 菜单样式
     */
    @Column(name = "permission_icon")
    private String permissionIcon;

}
