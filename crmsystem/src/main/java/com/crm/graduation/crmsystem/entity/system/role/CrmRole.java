package com.crm.graduation.crmsystem.entity.system.role;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色
 */
@Table(name = "crm_role")
@Getter
@Setter
public class CrmRole {

    /**
     * 角色id
     */
    @Id
    @Column(name = "role_id")
    private String roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private Integer isDelete;

}
