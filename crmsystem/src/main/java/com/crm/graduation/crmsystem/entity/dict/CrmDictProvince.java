package com.crm.graduation.crmsystem.entity.dict;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 省份
 */
@Getter
@Setter
@Table(name = "crm_dict_province")
public class CrmDictProvince {

  /**
   * 省份编码
   */
  @Column(name = "province_code")
  private String provinceCode;

  /**
   * 省份名称
   */
  @Column(name = "province_name")
  private String provinceName;

  /**
   * 是否有效
   */
  @Column(name = "is_valid")
  private String isValid;

  /**
   * 城市
   */
  @Transient
  private List<CrmDictCity> cities;
}
