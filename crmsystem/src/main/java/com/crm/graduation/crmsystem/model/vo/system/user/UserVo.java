package com.crm.graduation.crmsystem.model.vo.system.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserVo {

    private String userId;
    private String userName;
    private String phone;
    private String email;
    private Date updataTime;
    private Date createTime;
    private String userDepartment;
    private String userTypeOfWork;
    private String userCode;
    private String userRealName;
    private String userDescribe;
}
