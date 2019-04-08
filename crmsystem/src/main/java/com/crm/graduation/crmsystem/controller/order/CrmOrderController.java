package com.crm.graduation.crmsystem.controller.order;

import com.crm.graduation.crmsystem.controller.system.BaseController;
import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.exceptions.OrderStatusErrorException;
import com.crm.graduation.crmsystem.model.Consts.OrderConst;
import com.crm.graduation.crmsystem.model.vo.order.OrClVO;
import com.crm.graduation.crmsystem.model.vo.order.OrderListVO;
import com.crm.graduation.crmsystem.model.vo.order.OrderVO;
import com.crm.graduation.crmsystem.service.client.CrmClientService;
import com.crm.graduation.crmsystem.service.dict.CrmDictService;
import com.crm.graduation.crmsystem.service.order.CrmOrderService;
import com.crm.graduation.crmsystem.utils.ResultDto;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/main/order")
public class CrmOrderController extends BaseController {

    @Resource
    private CrmDictService crmDictService;

    @Resource
    private CrmOrderService crmOrderService;

    @Resource
    private CrmClientService crmClientService;

    /**
     * 主页
     */
    @RequestMapping("/list")
    public String toList(OrderListVO orderListVO, ModelMap modelMap){
        modelMap.addAttribute("order",orderListVO);
        try {
            //查询订单类型字典集合
            List<CrmDataDict> type = getDict(OrderConst.ORDER_TYPE_CODE);
            modelMap.addAttribute("type",type);
            //查询订单状态字典集合
            List<CrmDataDict> status = getDict(OrderConst.ORDER_STATUS_CODE);
            modelMap.addAttribute("status",status);
            //查询订单的集合
            List<OrderVO> list = crmOrderService.getList(orderListVO);
            modelMap.addAttribute("orders",list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "order/list";
    }

    /**
     * 查询订单类型字典集合
     */
    public List<CrmDataDict> getDict(String dictCode) throws Exception {
        CrmDataDict dictByCode = crmDictService.getDictByCode(dictCode);
        List<CrmDataDict> listDict = null;
        if(dictByCode != null){
            String partenId = dictByCode.getDictId();
            //根据父id查询集合
            listDict = crmDictService.getListDict(partenId);
        }
        return listDict;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String orderIds){
        String message = "fail";
        try {
            message = crmOrderService.deleteOrder(orderIds);
        } catch (OrderStatusErrorException oe){
            oe.printStackTrace();
            return "statusError";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 跳转编辑页面
     */
    @RequestMapping("/edit")
    public String toEdit(String orderId, ModelMap modelMap, String userId){
        try {
            //字典部分
            //查询订单类型字典集合
            List<CrmDataDict> type = getDict(OrderConst.ORDER_TYPE_CODE);
            modelMap.addAttribute("type",type);
            //查询订单状态字典集合
            List<CrmDataDict> status = getDict(OrderConst.ORDER_STATUS_CODE);
            modelMap.addAttribute("status",status);
            //1.查询出订单的信息
            OrderVO order = crmOrderService.getOrder(orderId);
            modelMap.addAttribute("order",order);
            //2.查询出用户所有的客户
            List<OrClVO> orCl = crmClientService.getAllClient(userId);
            modelMap.addAttribute("orCl",orCl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "order/edit";
    }

    /**
     * 编辑保存
     */
    @RequestMapping("/editdo")
    @ResponseBody
    public ResultDto editDo(OrderVO orderVO){
        try {
            String message = crmOrderService.editDo(orderVO);
            return ResultDto.success(message);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error("fail");
        }
    }

    /**
     * 跳转添加页面
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap modelMap){
        try {
            String userId = getCurrenUserId();
            //字典部分
            //查询订单类型字典集合
            List<CrmDataDict> type = getDict(OrderConst.ORDER_TYPE_CODE);
            modelMap.addAttribute("type",type);
            //查询订单状态字典集合
            List<CrmDataDict> status = getDict(OrderConst.ORDER_STATUS_CODE);
            modelMap.addAttribute("status",status);
            //2.查询出用户所有的客户
            List<OrClVO> orCl = crmClientService.getAllClient(userId);
            modelMap.addAttribute("orCl",orCl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "order/add";
    }

    /**
     * 添加逻辑
     * @param orderVO
     * @return
     */
    @RequestMapping("/addDo")
    @ResponseBody
    public String addDo(OrderVO orderVO){
        String userId = getCurrenUserId();
        String mess = "fail";
        try {
            mess = crmOrderService.addDo(orderVO,userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mess;
    }
}
