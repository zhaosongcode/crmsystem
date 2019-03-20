package com.crm.graduation.crmsystem.controller.other;

import com.crm.graduation.crmsystem.model.vo.other.ChargingVO;
import com.crm.graduation.crmsystem.service.other.CrmOtherChargingsystemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

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

    /**
     * 充值逻辑
     */
    @RequestMapping("/moneydo")
    @ResponseBody
    public String moneydo(@RequestParam Map<String, String> parms){
        String mess = "fail";
        if(parms != null){
            String bankNum = parms.get("bankNum");
            String bankType = parms.get("bankType");
            String moneyNum = parms.get("moneyNum");
        }
        return mess;
    }
}
