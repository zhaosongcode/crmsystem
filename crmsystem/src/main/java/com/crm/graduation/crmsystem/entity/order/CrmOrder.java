package com.crm.graduation.crmsystem.entity.order;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Table(name = "crm_order")
public class CrmOrder {

  /**
   * 订单id
   */
  @Id
  @Column(name = "order_id")
  private String orderId;

  /**
   * 用户id
   */
  @Column(name = "user_id")
  private String userId;

  /**
   * 订单状态
   */
  @Column(name = "order_status")
  private String orderStatus;

  /**
   * 客户id
   */
  @Column(name = "client_id")
  private String clientId;

  /**
   * 创建时间
   */
  @Column(name = "create_time")
  private Date createTime;

  /**
   * 订单编号
   */
  @Column(name = "order_code")
  private String orderCode;

  /**
   * 商品id
   */
  @Column(name = "commodity_id")
  private String commodityId;

  /**
   * 是否删除
   */
  @Column(name = "is_delete")
  private Integer isDelete;

  /**
   * 支付订单的id
   */
  @Column(name = "pay_id")
  private String payId;

  /**
   * 订单类型
   */
  @Column(name = "order_type")
  private String orderType;
}
