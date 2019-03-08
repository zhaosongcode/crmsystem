package com.crm.graduation.crmsystem.utils.email;

public class FeedbackContentImpl implements MailContent {
    @Override
    public String getContent() {
        String content = "<!DOCTYPE>"+"<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：本邮件由系统自动发出，请勿回复。</span>"
                + "<div style='width:950px;font-family:arial;'>感谢您的反馈意见，我们将尽快处理，请注意关注邮箱动态!<br/>感谢您的使用。<br/><span style='font-weight:bold;'>CRM客户关系管理系统</span></div>"
                +"</div>";
        return content;
    }
}
