package com.crm.graduation.crmsystem.dao.mapper.other;

import com.crm.graduation.crmsystem.model.utils.MyMapper;
import com.crm.graduation.crmsystem.entity.other.CrmMessage;

import java.util.List;
import java.util.Map;

public interface CrmMessageMapper extends MyMapper<CrmMessage> {

    List<CrmMessage> getRecords(Map<String,Object> map);
    int getCounts(Map<String,Object> map);
}