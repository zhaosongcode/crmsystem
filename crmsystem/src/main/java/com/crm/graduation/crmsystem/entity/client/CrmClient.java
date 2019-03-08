package com.crm.graduation.crmsystem.entity.client;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 客户
 */
@Table(name = "crm_client")
@Getter
@Setter
public class CrmClient {

    /**
     * 客户id
     */
      @Id
      @Column(name = "client_id")
      private String clientId;

    /**
     * 客户姓名
     */
    @Column(name = "client_name")
    private String clientName;

    /**
     * 客户固定电话
     */
    @Column(name = "client_phone")
    private String clientPhone;

    /**
     *客户移动电话
     */
    @Column(name = "client_mobelPhone")
    private String clientMobelPhone;

    /**
     * 客户生日
     */
    @Column(name = "client_birthday")
    private String clientBirthday;

    /**
     * 客户邮箱
     */
    @Column(name = "client_email")
    private String clientEmail;

    /**
     * 客户性别
     */
    @Column(name = "client_gender")
    private String clientGender;

    /**
     * 客户年龄
     */
    @Column(name = "client_age")
    private Integer clientAge;

    /**
     * 客户信誉
     */
    @Column(name = "client_credit")
    private String clientCredit;

    /**
     * 客户紧急联系电话
     */
    @Column(name = "client_enePhone")
    private String clientEnePhone;

    /**
     * 客户创建时间
     */
    @Column(name = "client_create_time")
    private Date clientCreateTime;

    /**
     * 客户类型
     */
    @Column(name = "client_type")
    private String clientType;

    /**
     * 客户编码
     */
    @Column(name = "client_code")
    private String clientCode;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    private String isDelete;

    /**
     * 图标
     */
    @Column(name = "client_icon")
    private String clientIcon;
}
