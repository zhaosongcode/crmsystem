package com.crm.graduation.crmsystem.controller.other;

import com.crm.graduation.crmsystem.entity.other.CrmMessage;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.other.ContactVO;
import com.crm.graduation.crmsystem.service.other.CrmMessageService;
import com.crm.graduation.crmsystem.service.other.CrmOnChatService;
import com.crm.graduation.crmsystem.service.system.user.CrmUserService;
import com.crm.graduation.crmsystem.utils.ResultDto;
import com.crm.graduation.crmsystem.utils.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@RequestMapping("/main/other")
public class CrmOnChatController {

    @Resource
    private CrmOnChatService crmOnChatService;

    @Resource
    private CrmMessageService crmMessageService;

    @Resource
    private CrmUserService crmUserService;

    private static final Logger logger = LoggerFactory.getLogger(CrmOnChatController.class);

    //连接集合
    private static final Set<CrmOnChatController> connections = new CopyOnWriteArraySet<CrmOnChatController>();


    @RequestMapping("/onlineconsultant")
    public String toChatIndex(String userId, ModelMap modelMap, HttpServletRequest request){
        //查询除了自己以外的所有联系人
        try {
            List<ContactVO> contacts = crmOnChatService.getContacts(userId);

            modelMap.addAttribute("contacts",contacts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "other/chat/chat";
    }

    /**
     * 查询未读消息
     */
    @RequestMapping("/getunreadmessage")
    @ResponseBody
    public String getUnReadMessage(String userId){
        String result = "false";
        Session session = SecurityUtils.getSubject().getSession();
        session.removeAttribute("unReadMessages");
        try {
            List<CrmMessage> unReadMessages = crmMessageService.getList(userId);
            if(unReadMessages != null && unReadMessages.size()<1){
                result = "unmessage";
            }
            if(unReadMessages != null && unReadMessages.size()>0){
                result = "havemessage";
                for(CrmMessage crmMessage : unReadMessages){
                    String fromUserid = crmMessage.getFromUserid();
                    CrmUser userByUserId = crmUserService.getUserByUserId(fromUserid);
                    String userDepartment = userByUserId.getUserDepartment();
                    crmMessage.setApartmentId(userDepartment);
                }
                session.setAttribute("unReadMessages",unReadMessages);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查找未读记录并修改状态
     */
    @RequestMapping("/getMessage")
    @ResponseBody
    public ResultDto getMessage(String userId,String fromUserId){
        //首先查询出未读记录
        try {
            List<CrmMessage> unReadMessages = crmMessageService.getUnMessages(userId,fromUserId);
            for(CrmMessage crmMessage : unReadMessages){
                crmMessage.setMessageStatus("01");
                crmMessageService.updataMessageStatus(crmMessage);
            }
            return ResultDto.success(unReadMessages);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error();
        }
    }

    /**
     * 存储未读消息
     */
    @RequestMapping("/saveUnRMessages")
    @ResponseBody
    public String saveUnReadMessages(String messages){
        try {
            crmMessageService.saveUnReadMessages(messages);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 刷新未读列表
     */
    @RequestMapping("/refSess")
    @ResponseBody
    public List<CrmMessage> refeSess(String userId){
        List<CrmMessage> unReadMessages = null;
        try {
            unReadMessages = crmMessageService.getList(userId);
            for(CrmMessage crmMessage : unReadMessages){
                String fromUserid = crmMessage.getFromUserid();
                CrmUser userByUserId = crmUserService.getUserByUserId(fromUserid);
                String userDepartment = userByUserId.getUserDepartment();
                crmMessage.setApartmentId(userDepartment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return unReadMessages;
    }

    /**
     * 存储消息
     */
    @RequestMapping("/saveMessages")
    @ResponseBody
    public String saveMessage(String messages, String toUserId){
        try {
            crmMessageService.saveMess(messages,toUserId);
            String[] split = messages.split(",");
            String message = split[0];
            String fromUserId = split[1];
            CrmUser user = crmUserService.getUserByUserId(fromUserId);
            String userName = user.getUserName();
            String messagess = message + "," + userName;
            return messagess;
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 聊天记录回显
     */
    @RequestMapping("/getRecord")
    @ResponseBody
    public List<CrmMessage> getRecord(String toUserId, String fromUserId, String messageCount){
        try {
            List<CrmMessage> record = crmMessageService.getRecord(toUserId, fromUserId, messageCount);
            return record;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
