package com.crm.graduation.crmsystem.entity.system.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@Table(name = "crm_user")
@Getter
@Setter
public class CrmUser implements Serializable {

    /**
     * 用户id
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    @Column(name = "user_password")
    private String userPassword;

    /**
     * 手机号
     */
    @Column(name = "user_phone")
    private String phone;

    /**
     * 用户邮箱
     */
    @Column(name = "user_email")
    private String email;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最新修改时间
     */
    @Column(name = "updata_time")
    private Date updataTime;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 部门
     */
    @Column(name = "user_department")
    private String userDepartment;

    /**
     * 工种
     */
    @Column(name = "user_typeOfWork")
    private String userTypeOfWork;

    /**
     * 工号
     */
    @Column(name = "user_code")
    private String userCode;

    /**
     * 真实姓名
     */
    @Column(name = "user_realName")
    private String userRealName;

    /**
     * 金币
     */
    @Column(name = "user_gold")
    private String userGold;

    /**
     * 用户描述
     */
    @Column(name = "user_describe")
    private String userDescribe;

}
