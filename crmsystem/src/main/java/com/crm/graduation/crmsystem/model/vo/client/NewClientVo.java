package com.crm.graduation.crmsystem.model.vo.client;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 新增客户vo
 */
@Getter
@Setter
public class NewClientVo {

    private String userId;
    private String addressId;
    private String clientId;
    private String clientName;
    private String clientGender;
    private String clientMobelPhone;
    private String clientPhone;
    private String clientEnePhone;
    private String clientBirthday;
    private String clientEmail;
    private String clientCredit;
    private String province;
    private String city;
    private String country;
    private String detailed;
    private String clientType;
    private String iconName;
    private Integer clientAge;
}
