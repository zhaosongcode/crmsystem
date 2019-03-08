package com.crm.graduation.crmsystem.service.dict;

import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictCityMapper;
import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictCountryMapper;
import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictMapper;
import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictProvinceMapper;
import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.entity.dict.CrmDictCity;
import com.crm.graduation.crmsystem.entity.dict.CrmDictCountry;
import com.crm.graduation.crmsystem.entity.dict.CrmDictProvince;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CrmDictService {

    @Resource
    private CrmDictMapper crmDictMapper;

    @Resource
    private CrmDictProvinceMapper crmDictProvinceMapper;

    @Resource
    private CrmDictCityMapper crmDictCityMapper;

    @Resource
    private CrmDictCountryMapper crmDictCountryMapper;

    /**
     * 根据字典编号查询
     */
    public CrmDataDict getDictByCode(String dictCode) throws Exception{
        CrmDataDict crmDataDict = new CrmDataDict();
        crmDataDict.setDictCode(dictCode);
        CrmDataDict dict = crmDictMapper.selectOne(crmDataDict);
        return dict;
    }

    /**
     * 根据父id查询字典
     */
    public List<CrmDataDict> getListDict(String partenId) throws Exception{
        CrmDataDict crmDataDict = new CrmDataDict();
        crmDataDict.setParentId(partenId);
        List<CrmDataDict> dicts = crmDictMapper.select(crmDataDict);
        return dicts;
    }

    /**
     * 查询地址信息字典
     */
    public List<CrmDictProvince> getAllAddress() throws Exception{
        CrmDictProvince crmDictProvince = new CrmDictProvince();
        crmDictProvince.setIsValid("1");
        List<CrmDictProvince> provinces = crmDictProvinceMapper.select(crmDictProvince);
        for(CrmDictProvince province : provinces){
            CrmDictCity crmDictCity = new CrmDictCity();
            crmDictCity.setIsValid("1");
            crmDictCity.setProvinceCode(province.getProvinceCode());
            List<CrmDictCity> cities = crmDictCityMapper.select(crmDictCity);
            for(CrmDictCity city : cities){
                CrmDictCountry crmDictCountry = new CrmDictCountry();
                crmDictCountry.setIsValid("1");
                crmDictCountry.setCityCode(city.getCityCode());
                List<CrmDictCountry> countries = crmDictCountryMapper.select(crmDictCountry);
                city.setCountries(countries);
            }
            province.setCities(cities);
        }
        return provinces;
    }

}
