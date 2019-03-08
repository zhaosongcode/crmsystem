package com.crm.graduation.crmsystem.entity.system.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户角色中间
 */
@Getter
@Setter
@Table(name = "crm_user_role")
public class CrmUserRole {

    /**
     * 用户角色中间id
     */
    @Id
    @Column(name = "user_role_id")
    private String userRoleId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;
}
