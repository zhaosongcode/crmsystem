package com.crm.graduation.crmsystem.service.other;

import com.crm.graduation.crmsystem.dao.mapper.other.CrmTopUpMapper;
import com.crm.graduation.crmsystem.entity.order.CrmOrder;
import com.crm.graduation.crmsystem.entity.other.CrmTopUp;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class CrmTopUpService {

    @Resource
    private CrmTopUpMapper crmTopUpMapper;

    /**
     * 充值逻辑
     * @param parms
     * @return
     */
    public String toup(Map<String, String> parms, String userId)throws Exception {
        String bankNum = parms.get("bankNum");
        String bankType = parms.get("bankType");
        String moneyNum = parms.get("moneyNum");
        CrmTopUp crmTopUp = new CrmTopUp();
        crmTopUp.setUserId(userId);
        crmTopUp.setBankId(bankType);
        crmTopUp.setBankNum(bankNum);
        crmTopUp.setTime(new Date());
        crmTopUp.setTopUpId(Tools.get32UUID());
        crmTopUp.setTopUpNum(moneyNum);
        crmTopUpMapper.insert(crmTopUp);
        return "success";
    }
}
