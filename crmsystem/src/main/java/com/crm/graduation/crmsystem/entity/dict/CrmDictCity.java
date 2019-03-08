package com.crm.graduation.crmsystem.entity.dict;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 城市
 */
@Getter
@Setter
@Table(name = "crm_dict_city")
public class CrmDictCity {

  /**
   * 城市编码
   */
  @Id
  @Column(name = "city_code")
  private String cityCode;

  /**
   * 城市名称
   */
  @Column(name = "city_name")
  private String cityName;

  /**
   * 省份编码
   */
  @Column(name = "province_code")
  private String provinceCode;

  /**
   * 是否有效
   */
  @Column(name = "is_valid")
  private String isValid;

  /**
   * 城市类型(01:一线,02:新一线,03:二线,04:三线,05:其他)
   */
  @Column(name = "city_type")
  private String cityType;

  /**
   * 县
   */
  @Transient
  private List<CrmDictCountry> countries;

}
