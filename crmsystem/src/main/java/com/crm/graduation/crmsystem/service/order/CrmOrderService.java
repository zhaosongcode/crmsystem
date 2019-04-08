package com.crm.graduation.crmsystem.service.order;

import com.crm.graduation.crmsystem.dao.mapper.client.CrmClientMapper;
import com.crm.graduation.crmsystem.dao.mapper.order.CrmOrderMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserMapper;
import com.crm.graduation.crmsystem.entity.client.CrmClient;
import com.crm.graduation.crmsystem.entity.order.CrmOrder;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.exceptions.OrderStatusErrorException;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.order.OrderListVO;
import com.crm.graduation.crmsystem.model.vo.order.OrderVO;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CrmOrderService {

    @Resource
    private CrmOrderMapper crmOrderMapper;

    @Resource
    private CrmUserMapper crmUserMapper;

    @Resource
    private CrmClientMapper crmClientMapper;

    /**
     * 查询订单列表
     */
    public List<OrderVO> getList(OrderListVO orderListVO)throws Exception{
        List<OrderVO> orderVOS = null;
        //先查询用户id
        if(orderListVO != null){
            String userId = orderListVO.getUserId();
            CrmUser crmUser = crmUserMapper.selectByPrimaryKey(userId);
            if(crmUser != null){
                //根据客户的姓名查询出对应的id集合
                List<String> clientIds = null;
                if(orderListVO.getClientName() != null && !"".equals(orderListVO.getClientName())){
                    Example client = new Example(CrmClient.class);
                    Example.Criteria criteria = client.createCriteria().andEqualTo("isDelete", 0);
                    criteria.andLike("clientName","%"+orderListVO.getClientName()+"%");
                    List<CrmClient> clients = crmClientMapper.selectByExample(client);
                    clientIds = new ArrayList<>();
                    for(CrmClient crmClient : clients){
                        clientIds.add(crmClient.getClientId());
                    }
                }
                //加条件
                Example order = new Example(CrmOrder.class);
                order.setOrderByClause("create_time desc");
                Example.Criteria criteria = order.createCriteria().andEqualTo("isDelete", 0);
                //判断是否是管理员
                if(!Consts.ADMIN_ID.equals(userId)){
                    criteria.andEqualTo("userId",userId);
                }
                //判断是否需要加条件
                if(clientIds != null && clientIds.size()>0){
                    criteria.andIn("clientId",clientIds);
                }
                if(orderListVO.getOrderType() != null && !"".equals(orderListVO.getOrderType())){
                    criteria.andEqualTo("orderType",orderListVO.getOrderType());
                }
                if(orderListVO.getOrderStatus() != null && !"".equals(orderListVO.getOrderStatus())){
                    criteria.andEqualTo("orderStatus",orderListVO.getOrderStatus());
                }
                List<CrmOrder> crmOrders = crmOrderMapper.selectByExample(order);
                orderVOS = new ArrayList<>();
                for(CrmOrder crmOrder : crmOrders){
                    OrderVO orderVO = new OrderVO();
                    BeanUtils.copyProperties(crmOrder,orderVO);
                    Date createTime = crmOrder.getCreateTime();
                    String time = Tools.tranTime(createTime);
                    orderVO.setCreateTime(time);
                    String clientId = crmOrder.getClientId();
                    CrmClient crmClient = crmClientMapper.selectByPrimaryKey(clientId);
                    String clientName = crmClient.getClientName();
                    orderVO.setClientName(clientName);
                    orderVOS.add(orderVO);
                }
            }
        }
        return orderVOS;
    }

    /**
     * 删除订单
     * 只有01和04状态可以删除
     */
    @Transactional(rollbackFor = Exception.class)
    public String deleteOrder(String orderIds)throws Exception{
        String message = "fail";
        if(orderIds != null && !"".equals(orderIds)){
            String[] split = orderIds.split(",");
                //删除
                for(String orderId : split){
                    CrmOrder crmOrder = crmOrderMapper.selectByPrimaryKey(orderId);
                    if(crmOrder != null){
                        if("01".equals(crmOrder.getOrderStatus()) || "04".equals(crmOrder.getOrderStatus())){
                            crmOrder.setIsDelete(1);
                            crmOrderMapper.updateByPrimaryKey(crmOrder);
                        }else{
                            throw new OrderStatusErrorException();
                        }
                    }
                }
            message = "success";
        }
        return message;
    }

    /**
     * 查询单个订单信息
     */
    public OrderVO getOrder(String orderId)throws Exception{
        CrmOrder crmOrder = crmOrderMapper.selectByPrimaryKey(orderId);
        OrderVO orderVO = null;
        if(crmOrder != null){
            orderVO = new OrderVO();
            BeanUtils.copyProperties(crmOrder,orderVO);
        }
        return orderVO;
    }

    /**
     * 编辑保存
     */
    public String editDo(OrderVO orderVO)throws Exception{
        String message = "fail";
        if(orderVO != null){
            String orderId = orderVO.getOrderId();
            CrmOrder crmOrder = crmOrderMapper.selectByPrimaryKey(orderId);
            if(crmOrder != null){
                crmOrder.setClientId(orderVO.getClientId());
                crmOrder.setOrderStatus(orderVO.getOrderStatus());
                crmOrder.setOrderType(orderVO.getOrderType());
                crmOrderMapper.updateByPrimaryKey(crmOrder);
                message = "success";
            }
        }
        return message;
    }

    /**
     * 新增订单
     * @param orderVO
     * @return
     * @throws Exception
     */
    public String addDo(OrderVO orderVO, String userId)throws Exception {
        String mess = "fail";
        CrmOrder crmOrder = new CrmOrder();
        crmOrder.setOrderId(Tools.get32UUID());
        //订单编号原则：OD + 六位数字（按数据量）
        String orderCode = "OD";
        //查询数量
        int i = crmOrderMapper.selectCount(new CrmOrder());
        i++;
        String s = Tools.tranCode(i);
        orderCode += s;
        crmOrder.setOrderCode(orderCode);//订单编号
        crmOrder.setOrderType(orderVO.getOrderType());
        crmOrder.setOrderStatus(orderVO.getOrderStatus());
        crmOrder.setClientId(orderVO.getClientId());
        crmOrder.setIsDelete(0);
        crmOrder.setCreateTime(new Date());
        crmOrder.setUserId(userId);
        int insert = crmOrderMapper.insert(crmOrder);
        if(insert>0){
            mess = "success";
        }
        return mess;
    }
}
