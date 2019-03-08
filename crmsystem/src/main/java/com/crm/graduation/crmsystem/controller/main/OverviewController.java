package com.crm.graduation.crmsystem.controller.main;

import com.crm.graduation.crmsystem.service.client.CrmClientService;
import com.crm.graduation.crmsystem.service.client.CrmCurrentRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 概览
 */
@Controller
@RequestMapping("/overview")
public class OverviewController {

    @Resource
    private CrmClientService crmClientService;

    @Resource
    private CrmCurrentRecordService crmCurrentRecordService;

    /**
     * 跳转概览页面
     */
    @RequestMapping("/index")
    public String toOverview(Model model,String userId){
        //获得客户年龄阶段list
        List<Double> listAge;
        try {
            listAge = crmClientService.getClientAge();
            model.addAttribute("listAge",listAge);
        }catch (Exception e){
            e.printStackTrace();
        }
        //获得客户居住地信息list
        List<Double> listAddress;
        try{
            listAddress = crmClientService.getClientAddress();
            model.addAttribute("listAddress",listAddress);
        }catch (Exception e){
            e.printStackTrace();
        }
        //获得记录信息
        try {
            List<Integer> listClients = crmCurrentRecordService.getRecordByUserId(userId);
            model.addAttribute("listClients",listClients);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "main";
    }
}
