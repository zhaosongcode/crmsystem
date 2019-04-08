package com.crm.graduation.crmsystem.service.route.tip;

import com.crm.graduation.crmsystem.dao.mapper.route.tip.CrmTipMapper;
import com.crm.graduation.crmsystem.entity.route.tip.CrmTip;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CrmRouteTipService {

    @Resource
    private CrmTipMapper crmTipMapper;

    /**
     * 查询首页列表
     * @param userId
     * @return
     * @throws Exception
     */
    public List<CrmTip> getList(String userId)throws Exception {
        Example example = new Example(CrmTip.class);
        example.setOrderByClause("create_time desc");
        example.createCriteria().andEqualTo("isDelete","0").andEqualTo("userId",userId);
        List<CrmTip> select = crmTipMapper.selectByExample(example);
        for(CrmTip crmTip1 : select){
            crmTip1.setCTime(Tools.tranTime(crmTip1.getCreateTime()));
            crmTip1.setTTime(Tools.tranTime(crmTip1.getTipTime()));
        }
        return select;
    }

    /**
     * 新增提醒事项
     * @param crmTip
     * @param userId
     * @throws Exception
     */
    public String addDo(CrmTip crmTip, String userId)throws Exception {
        String mess = "fail";
        crmTip.setUserId(userId);
        crmTip.setIsDelete("0");
        crmTip.setCreateTime(new Date());
        crmTip.setTipId(Tools.get32UUID());
        crmTip.setTipTime(Tools.StringTimeToDate(crmTip.getTTime()));
        int insert = crmTipMapper.insert(crmTip);
        if(insert>0){
            if("0".equals(crmTip.getIsClose())){
                //计算时间差
                long differentTime = Tools.getDifferentTime(Tools.tranTime(crmTip.getCreateTime()), crmTip.getTTime());
                if(differentTime>=0){
                    mess = "success,"+crmTip.getTipContent()+","+differentTime+","+crmTip.getTipId();
                }
            }else{
                mess = "success";
            }
        }
        return mess;
    }

    /**
     * 单个删除
     * @param
     * @return
     * @throws Exception
     */
    public String delete(String tipId)throws Exception {
        String mess = "fail";
        CrmTip crmTip = crmTipMapper.selectByPrimaryKey(tipId);
        if(crmTip != null){
            String isClose = crmTip.getIsClose();
            crmTip.setIsClose("1");
            crmTip.setIsDelete("1");
            int i = crmTipMapper.updateByPrimaryKey(crmTip);
            if(i>0){
                mess = "success";
                if("0".equals(isClose)){
                    mess += "-";
                    mess += tipId;
                }
            }
        }
        return mess;
    }

    /**
     * 批量删除
     * @param split
     * @return
     */
    public String deletes(String[] split) {
        String mess = "mess-";
        for(String tipId : split){
            CrmTip crmTip = crmTipMapper.selectByPrimaryKey(tipId);
            if(crmTip != null){
                String isClose = crmTip.getIsClose();
                crmTip.setIsClose("1");
                crmTip.setIsDelete("1");
                int i = crmTipMapper.updateByPrimaryKey(crmTip);
                if("0".equals(isClose)){
                    mess += crmTip.getTipId();
                    mess += ",";
                }
            }
        }
        return "success";
    }
}
