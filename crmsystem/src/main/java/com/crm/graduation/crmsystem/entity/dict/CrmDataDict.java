package com.crm.graduation.crmsystem.entity.dict;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name = "crm_data_dict")
public class CrmDataDict {

  /**
   * 字典id
   */
  @Column(name = "dict_id")
  @Id
  private String dictId;

  /**
   * 字典名称
   */
  @Column(name = "dict_name")
  private String dictName;

  /**
   * 字典编码
   */
  @Column(name = "dict_code")
  private String dictCode;

  /**
   * 字典排序
   */
  @Column(name = "dict_sort")
  private Integer dictSort;

  /**
   * 父id
   */
  @Column(name = "parent_id")
  private String parentId;

  /**
   * 描述
   */
  @Column(name = "description")
  private String description;
}
