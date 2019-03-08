package com.crm.graduation.crmsystem.model.vo.other;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackVO {

    private String feedbackType;
    private String feedbackSubject;
    private String feedbackDesc;
    private String userId;
}
