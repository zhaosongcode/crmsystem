package com.crm.graduation.crmsystem.controller.other;

import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.other.FeedbackVO;
import com.crm.graduation.crmsystem.service.dict.CrmDictService;
import com.crm.graduation.crmsystem.service.other.CrmOtherFeedbackService;
import com.crm.graduation.crmsystem.service.system.user.CrmUserService;
import com.crm.graduation.crmsystem.utils.ResultDto;
import com.crm.graduation.crmsystem.utils.email.FeedbackContentImpl;
import com.crm.graduation.crmsystem.utils.email.MailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/main/other")
public class CrmOtherFeedbackController {

    private static final String VIEW = "other/feedback";

    @Resource
    private CrmDictService crmDictService;

    @Resource
    private CrmUserService crmUserService;

    @Resource
    private CrmOtherFeedbackService crmOtherFeedbackService;

    private static final Logger log = LoggerFactory.getLogger(CrmOtherFeedbackController.class);

    /**
     * 跳转意见反馈页面
     */
    @RequestMapping("/feedback")
    public String toFeedback(ModelMap modelMap){
        //查询反馈类型字典
        List<CrmDataDict> listDict = null;
        try {
            CrmDataDict dataDict = crmDictService.getDictByCode(Consts.DICT_FEEDBACK_TYPE_CODE);
            if(dataDict != null){
                listDict = crmDictService.getListDict(dataDict.getDictId());
            }
            modelMap.addAttribute("feedbackTypes",listDict);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VIEW;
    }

    @RequestMapping("/send")
    @ResponseBody
    public ResultDto send(FeedbackVO feedbackVO){
        try {
            //保存到数据库
            crmOtherFeedbackService.save(feedbackVO);
            //发送成功邮件
            //先查询用户的邮箱
            CrmUser user = crmUserService.getUserByUserId(feedbackVO.getUserId());
            if(user != null){
                String email = user.getEmail();
                FeedbackContentImpl content = new FeedbackContentImpl();
                MailUtils mailUtils = new MailUtils();
                mailUtils.sendEmail(email,"感谢函",content.getContent());
            }
            return ResultDto.success("success");
        }catch (Exception e){
            e.printStackTrace();
            return ResultDto.error();
        }
    }
}
