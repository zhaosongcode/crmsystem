package com.crm.graduation.crmsystem.service.client;

import com.crm.graduation.crmsystem.dao.mapper.system.CrmCurrentRecordMapper;
import com.crm.graduation.crmsystem.entity.system.CrmCurrentRecord;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作记录
 */
@Service
public class CrmCurrentRecordService {

    @Resource
    private CrmCurrentRecordMapper crmCurrentRecordMapper;

    /**
     * 获取用户的记录信息
     */
    public List<Integer> getRecordByUserId(String userId){
        List<Integer> list = new ArrayList<>();
        Integer m1i = 0;
        Integer m1d = 0;
        Integer m2i = 0;
        Integer m2d = 0;
        Integer m3i = 0;
        Integer m3d = 0;
        Integer m4i = 0;
        Integer m4d = 0;
        Integer m5i = 0;
        Integer m5d = 0;
        Integer m6i = 0;
        Integer m6d = 0;
        Integer m7i = 0;
        Integer m7d = 0;
        Integer m8i = 0;
        Integer m8d = 0;
        Integer m9i = 0;
        Integer m9d = 0;
        Integer m10i = 0;
        Integer m10d = 0;
        Integer m11i = 0;
        Integer m11d = 0;
        Integer m12i = 0;
        Integer m12d = 0;
        CrmCurrentRecord crmCurrentRecord = new CrmCurrentRecord();
        crmCurrentRecord.setUserId(userId);
        List<CrmCurrentRecord> allRecords = crmCurrentRecordMapper.select(crmCurrentRecord);
        for(CrmCurrentRecord crmCurrentRecord1 : allRecords){
            String s = Tools.tranTime(crmCurrentRecord1.getRecordTime());
            String months = s.substring(5, 7);
            int month = Integer.parseInt(months);
            Integer ty = crmCurrentRecord1.getRecordType();
            //0/1 删除/新增
            switch (month){
                case 1 :
                    if(ty==1){
                        m1i+=1;
                    }else{
                        m1d+=1;
                    }
                    break;
                case 2 :
                    if(ty==1){
                        m2i+=1;
                    }else{
                        m2d+=1;
                    }
                    break;
                case 3 :
                    if(ty==1){
                        m3i+=1;
                    }else{
                        m3d+=1;
                    }
                    break;
                case 4 :
                    if(ty==1){
                        m4i+=1;
                    }else{
                        m4d+=1;
                    }
                    break;
                case 5 :
                    if(ty==1){
                        m5i+=1;
                    }else{
                        m5d+=1;
                    }
                    break;
                case 6 :
                    if(ty==1){
                        m6i+=1;
                    }else{
                        m6d+=1;
                    }
                    break;
                case 7 :
                    if(ty==1){
                        m7i+=1;
                    }else{
                        m7d+=1;
                    }
                    break;
                case 8 :
                    if(ty==1){
                        m8i+=1;
                    }else{
                        m8d+=1;
                    }
                    break;
                case 9 :
                    if(ty==1){
                        m9i+=1;
                    }else{
                        m9d+=1;
                    }
                    break;
                case 10 :
                    if(ty==1){
                        m10i+=1;
                    }else{
                        m10d+=1;
                    }
                    break;
                case 11 :
                    if(ty==1){
                        m11i+=1;
                    }else{
                        m11d+=1;
                    }
                    break;
                case 12 :
                    if(ty==1){
                        m12i+=1;
                    }else{
                        m12d+=1;
                    }
                    break;
            }
        }
        list.add(m1i);
        list.add(m1d);
        list.add(m2i);
        list.add(m2d);
        list.add(m3i);
        list.add(m3d);
        list.add(m4i);
        list.add(m4d);
        list.add(m5i);
        list.add(m5d);
        list.add(m6i);
        list.add(m6d);
        list.add(m7i);
        list.add(m7d);
        list.add(m8i);
        list.add(m8d);
        list.add(m9i);
        list.add(m9d);
        list.add(m10i);
        list.add(m10d);
        list.add(m11i);
        list.add(m11d);
        list.add(m12i);
        list.add(m12d);
        return list;
    }

}
