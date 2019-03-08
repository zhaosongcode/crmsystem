package com.crm.graduation.crmsystem.model.vo.other;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ContactVO {

    private String apartmentId;//部门id
    private String apartmentName;//部门名称
    private Integer peoCount;//部门人员数量
    List<ContactSimpleVO> users;//部门所有人
}
