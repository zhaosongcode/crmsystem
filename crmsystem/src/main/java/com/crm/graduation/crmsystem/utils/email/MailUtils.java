package com.crm.graduation.crmsystem.utils.email;

import com.crm.graduation.crmsystem.model.Consts.MailConst;
import org.springframework.context.annotation.Configuration;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * 发送邮件
 */
@Configuration
public class MailUtils {

    public void sendEmail(String to, String subject, String content) throws MessagingException, UnsupportedEncodingException {

        //设置基本属性
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", MailConst.HOST);
        props.put("mail.smtp.auth", "true");
        props.setProperty("mail.user", MailConst.USER);
        props.setProperty("mail.password", MailConst.PASSWORD);
        //props.setProperty("mail.smtp.port", "465");

        //验证账号密码(授权码)
        Session session = Session.getInstance(props, new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(MailConst.USER, MailConst.PASSWORD); //发件人邮件用户名、授权码
            }
        });
        //详细查看
        //session.setDebug(true);
        MimeMessage message = new MimeMessage(session);
        //设置主题
        message.setSubject(subject);
        //设置邮件的内容
        //message.setContent("感谢您的反馈，我们将尽快解决，请注意关注邮箱动态", "text/plain");
        //message.setText("感谢您的反馈，我们将尽快解决，请注意关注邮箱动态","UTF-8");
        StringBuffer sb = new StringBuffer();
        sb.append(content);
        MimeBodyPart mbp1 = new MimeBodyPart(); // 正文
        mbp1.setContent(content.toString(), "text/html;charset=utf-8");
        Multipart mp = new MimeMultipart(); // 整个邮件：正文+附件
        mp.addBodyPart(mbp1);
        // mp.addBodyPart(mbp2);

        // 附件部分
        /*MimeBodyPart mbp2 = new MimeBodyPart();
        String filename = "d:/1.doc";
        DataSource source = new FileDataSource(filename);
        mbp2.setDataHandler(new DataHandler(source));
        mbp2.setFileName("abc.doc");
        mp.addBodyPart(mbp2);*/

        message.setContent(mp);
        message.setSentDate(new Date());
        message.saveChanges();
       // Transport trans = session.getTransport("smtp");

        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to));
        //文本内容用下面
        //message.setText("Hello");
        //如果要在邮件地址后面增加名字的话，可以通过传递两个参数：代表邮件地址和名字的字符串来建立一个具有邮件地址和名字的邮件地址类：
        Address address = new InternetAddress(MailConst.FROM, "CRM服务中心");
        //Address address = new InternetAddress(from);
        //设置发件人
        message.setFrom(address);
        //message.setReplyTo(address);
        //发送
        Transport.send(message);
        System.out.println("发送成功");
    }

    public String toChinese(String text) {
        try {
            text = MimeUtility.encodeText(new String(text.getBytes(), "GB2312"), "GB2312", "B");
            } catch (Exception e) {
            e.printStackTrace();
            }
        return text;
        }
}
