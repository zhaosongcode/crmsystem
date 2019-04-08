package com.crm.graduation.crmsystem.controller.route.tip;

import com.crm.graduation.crmsystem.controller.system.BaseController;
import com.crm.graduation.crmsystem.entity.route.tip.CrmTip;
import com.crm.graduation.crmsystem.service.route.tip.CrmRouteTipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/main/route/tip/")
public class CrmRouteTipController extends BaseController {

    private static final String VIEW = "route/tip/";

    @Resource
    private CrmRouteTipService crmRouteTipService;

    @RequestMapping("list")
    public String list(ModelMap modelMap){
        //查询所有提醒信息
        String userId = getCurrenUserId();
        try {
            List<CrmTip> tips =  crmRouteTipService.getList(userId);
            modelMap.addAttribute("tips",tips);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return VIEW + "list";
    }

    @RequestMapping("add")
    public String add(){
        return VIEW + "add";
    }

    /**
     * 添加逻辑
     * @param crmTip
     * @return
     */
    @RequestMapping("addDo")
    @ResponseBody
    public String addDo(CrmTip crmTip){
        String mess = "fail";
        String userId = getCurrenUserId();
        try {
            mess = crmRouteTipService.addDo(crmTip,userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mess;
    }

    /**
     * 删除
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public String delete(String tipId){
        String mess = "fail";
        try {
            String[] split = tipId.split(",");
            if(split.length>1){
                //批量删除
                mess = crmRouteTipService.deletes(split);
                mess += "-";
                mess += tipId;
            }else{
                //单个删除
                mess = crmRouteTipService.delete(split[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mess;
    }
}
