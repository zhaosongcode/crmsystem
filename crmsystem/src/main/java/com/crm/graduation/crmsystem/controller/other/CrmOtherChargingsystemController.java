package com.crm.graduation.crmsystem.controller.other;

import com.crm.graduation.crmsystem.model.vo.other.ChargingVO;
import com.crm.graduation.crmsystem.service.other.CrmOtherChargingsystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/main/other")
public class CrmOtherChargingsystemController {

    public static final String VIEW = "other/charging";

    @Resource
    private CrmOtherChargingsystemService crmOtherChargingsystemService;

    /**
     * 跳转充值页面
     */
    @RequestMapping("/chargingsystem")
    public String toCharging(String userId, ModelMap modelMap){
        try {
            ChargingVO gold = crmOtherChargingsystemService.getGold(userId);
            modelMap.addAttribute("gold",gold);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VIEW;
    }
}
