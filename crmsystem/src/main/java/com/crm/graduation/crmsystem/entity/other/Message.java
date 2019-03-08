package com.crm.graduation.crmsystem.entity.other;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Message {

    // 发送者
    private String from;
    // 发送者名称
    private String fromName;
    // 接收者
    private String to;
    // 发送的文本
    private String text;
    // 发送日期
    private Date date;
}
