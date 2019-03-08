package com.crm.graduation.crmsystem.service.other;

import com.crm.graduation.crmsystem.dao.mapper.other.CrmOtherFeedbackMapper;
import com.crm.graduation.crmsystem.entity.other.CrmFeedback;
import com.crm.graduation.crmsystem.model.vo.other.FeedbackVO;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CrmOtherFeedbackService {

    @Resource
    private CrmOtherFeedbackMapper crmOtherFeedbackMapper;

    /**
     * 保存到数据库
     */
    public void save(FeedbackVO feedbackVO)throws Exception{
        CrmFeedback crmFeedback = new CrmFeedback();
        BeanUtils.copyProperties(feedbackVO,crmFeedback);
        crmFeedback.setCreateDate(new Date());
        crmFeedback.setFeedbackId(Tools.get32UUID());
        crmFeedback.setIsDelete(0);
        crmFeedback.setSolveState("01");
        crmOtherFeedbackMapper.insert(crmFeedback);
    }
}
