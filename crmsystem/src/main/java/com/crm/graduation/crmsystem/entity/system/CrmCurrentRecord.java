package com.crm.graduation.crmsystem.entity.system;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 操作记录表
 */
@Getter
@Setter
@Table(name = "crm_current_record")
public class CrmCurrentRecord {

  /**
   * 记录id
   */
  @Id
  @Column(name = "record_id")
  private String recordId;

  /**
   * 记录类型
   */
  @Column(name = "record_type")
  private Integer recordType;

  /**
   * 记录时间
   */
  @Column(name = "record_time")
  private Date recordTime;

  /**
   * 用户id
   */
  @Column(name = "user_id")
  private String userId;

  /**
   * 客户id
   */
  @Column(name = "client_id")
  private String clientId;

  /**
   * 记录描述
   */
  @Column(name = "record_desc")
  private String recordDesc;

}
