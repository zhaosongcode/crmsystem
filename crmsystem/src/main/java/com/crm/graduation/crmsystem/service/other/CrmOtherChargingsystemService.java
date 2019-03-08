package com.crm.graduation.crmsystem.service.other;

import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserMapper;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.vo.other.ChargingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CrmOtherChargingsystemService {

    @Resource
    private CrmUserMapper crmUserMapper;

    /**
     * 查询用户的金币数额
     */
    public ChargingVO getGold(String userId)throws Exception{
        CrmUser crmUser = crmUserMapper.selectByPrimaryKey(userId);
        ChargingVO chargingVO = null;
        if(crmUser != null){
            chargingVO = new ChargingVO();
            BeanUtils.copyProperties(crmUser,chargingVO);
        }
        return chargingVO;
    }
}
