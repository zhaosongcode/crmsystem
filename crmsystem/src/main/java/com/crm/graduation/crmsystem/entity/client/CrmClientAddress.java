package com.crm.graduation.crmsystem.entity.client;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "crm_client_address")
public class CrmClientAddress {

  /**
   * 地址id
   */
  @Id
  @Column(name = "address_id")
  private String addressId;

  /**
   * 客户id
   */
  @Column(name = "client_id")
  private String clientId;

  /**
   * 省份
   */
  @Column(name = "province")
  private String province;

  /**
   * 城市
   */
  @Column(name = "city")
  private String city;

  /**
   * 县级
   */
  @Column(name = "country")
  private String country;

  /**
   * 详细地址
   */
  @Column(name = "detailed")
  private String detailed;

  /**
   * 是否有效
   */
  @Column(name = "is_valid")
  private String isValid;

}
