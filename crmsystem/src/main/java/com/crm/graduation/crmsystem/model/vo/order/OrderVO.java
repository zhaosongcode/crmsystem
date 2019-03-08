package com.crm.graduation.crmsystem.model.vo.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVO {

    private String orderId;//订单id
    private String orderCode;//订单编号
    private String clientName;//客户姓名
    private String clientId;//客户id
    private String orderType;//订单类型
    private String orderStatus;//订单状态
    private String createTime;//创建时间
}
