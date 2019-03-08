package com.crm.graduation.crmsystem.entity.dict;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 县级
 */
@Getter
@Setter
@Table(name = "crm_dict_country")
public class CrmDictCountry {

  /**
   * 县编码
   */
  @Id
  @Column(name = "country_code")
  private String countryCode;

  /**
   * 县名称
   */
  @Column(name = "country_name")
  private String countryName;

  /**
   * 城市编码
   */
  @Column(name = "city_code")
  private String cityCode;

  /**
   * 是否有效
   */
  @Column(name = "is_valid")
  private String isValid;
}
