package com.crm.graduation.crmsystem.service.other;

import com.crm.graduation.crmsystem.dao.mapper.other.CrmMessageMapper;
import com.crm.graduation.crmsystem.entity.other.CrmMessage;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CrmMessageService {

    @Resource
    private CrmMessageMapper crmMessageMapper;

    /**
     * 根据userId查询记录
     */
    public List<CrmMessage> getList(String userId)throws Exception{
        Example example = new Example(CrmMessage.class);
        example.createCriteria().andEqualTo("toUserid",userId).andEqualTo("messageStatus","02");
        List<CrmMessage> messages = crmMessageMapper.selectByExample(example);
        return messages;
    }

    /**
     * 更改消息状态
     */
    public void updataMessageStatus(CrmMessage crmMessage)throws Exception{
        crmMessageMapper.updateByPrimaryKey(crmMessage);
    }

    /**
     * 查询指定通信间的未读记录
     */
    public List<CrmMessage> getUnMessages(String userId, String fromUserId)throws Exception{
        Example example = new Example(CrmMessage.class);
        example.setOrderByClause("create_time asc");
        example.createCriteria().andEqualTo("toUserid",userId).andEqualTo("fromUserid",fromUserId).andEqualTo("messageStatus","02");
        List<CrmMessage> crmMessages = crmMessageMapper.selectByExample(example);
        return crmMessages;
    }

    /**
     * 查询指定通信的记录
     */
    public List<CrmMessage> getRecord(String userId, String fromUserId, String messageCount)throws Exception{
        int messageCounts = Integer.parseInt(messageCount);
        Map<String,Object> map = new HashMap<>();
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        userIds.add(fromUserId);
        map.put("userIds",userIds);
        //map.put("messageStatus","01");
        //首先查询总条数
        int count = crmMessageMapper.getCounts(map);
        int counts = 0;
        if(count>=messageCounts){
            counts = count - messageCounts;
        }
        map.put("counts",counts);
        map.put("capa",messageCounts);
        List<CrmMessage> crmMessages = crmMessageMapper.getRecords(map);

        messageCounts += 10;
        List<CrmMessage> remCrms = new ArrayList<>();
        String mesCouns = String.valueOf(messageCounts);
        for(CrmMessage crmMessage : crmMessages){
            crmMessage.setMessageCount(mesCouns);
            if(crmMessage.getToUserid().equals(userId) && "02".equals(crmMessage.getMessageStatus())){
                remCrms.add(crmMessage);
            }
        }
        for(CrmMessage crmMessage : remCrms){
            crmMessages.remove(crmMessage);
        }
        return crmMessages;
    }
    /**
     * 保存未读消息
     */
    public void saveUnReadMessages(String message)throws Exception{
        String[] split = message.split(",");
        String messageContent = split[0];
        String toUserId = split[1];
        String fromUserId = split[2];
        CrmMessage crmMessage = new CrmMessage();
        crmMessage.setMessageStatus("02");
        crmMessage.setToUserid(toUserId);
        crmMessage.setCreateTime(new Date());
        crmMessage.setFromUserid(fromUserId);
        crmMessage.setMessageContent(messageContent);
        crmMessage.setMessageId(Tools.get32UUID());
        crmMessageMapper.insert(crmMessage);
    }

    /**
     * 存储已读消息
     */
    public void saveMess(String messages, String toUserId)throws Exception{
        String[] split = messages.split(",");
        String message = split[0];
        String fromUserId = split[1];
        CrmMessage crmMessage = new CrmMessage();
        crmMessage.setMessageId(Tools.get32UUID());
        crmMessage.setMessageContent(message);
        crmMessage.setFromUserid(fromUserId);
        crmMessage.setCreateTime(new Date());
        crmMessage.setToUserid(toUserId);
        crmMessage.setMessageStatus("01");
        crmMessageMapper.insert(crmMessage);
    }
}
