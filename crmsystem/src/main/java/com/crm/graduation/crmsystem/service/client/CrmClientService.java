package com.crm.graduation.crmsystem.service.client;

import com.crm.graduation.crmsystem.dao.DaoSupport;
import com.crm.graduation.crmsystem.dao.mapper.client.CrmClientAddressMapper;
import com.crm.graduation.crmsystem.dao.mapper.client.CrmClientMapper;
import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictCityMapper;
import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictCountryMapper;
import com.crm.graduation.crmsystem.dao.mapper.dict.CrmDictProvinceMapper;
import com.crm.graduation.crmsystem.dao.mapper.system.CrmCurrentRecordMapper;
import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserMapper;
import com.crm.graduation.crmsystem.entity.client.CrmClient;
import com.crm.graduation.crmsystem.entity.client.CrmClientAddress;
import com.crm.graduation.crmsystem.entity.dict.CrmDictCity;
import com.crm.graduation.crmsystem.entity.dict.CrmDictCountry;
import com.crm.graduation.crmsystem.entity.dict.CrmDictProvince;
import com.crm.graduation.crmsystem.entity.system.CrmCurrentRecord;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.client.ClientListSimpleVo;
import com.crm.graduation.crmsystem.model.vo.client.ClientsVo;
import com.crm.graduation.crmsystem.model.vo.client.NewClientVo;
import com.crm.graduation.crmsystem.model.vo.order.OrClVO;
import com.crm.graduation.crmsystem.utils.Tools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户
 */
@Service
public class CrmClientService {

    @Resource
    private CrmClientMapper crmClientMapper;

    @Resource
    private CrmUserMapper crmUserMapper;

    @Resource
    private CrmClientAddressMapper crmClientAddressMapper;

    @Resource
    private CrmDictCityMapper crmDictCityMapper;

    @Resource
    private CrmDictProvinceMapper crmDictProvinceMapper;

    @Resource
    private CrmDictCountryMapper crmDictCountryMapper;

    @Resource
    private CrmCurrentRecordMapper crmCurrentRecordMapper;

    @Resource
    private DaoSupport dao;

    /**
     * 查询客户的年龄分布
     */
    public List<Double> getClientAge()throws Exception{
        List<Double> list = new ArrayList<>();
        //全部客户数量
        CrmClient crmClient = new CrmClient();
        crmClient.setIsDelete("0");
        double i = crmClientMapper.selectCount(crmClient);
        //查询年龄在20岁以下的
        Example example = new Example(CrmClient.class);
        example.createCriteria().andLessThan("clientAge",20).andEqualTo("isDelete","0");
        double i1 = crmClientMapper.selectCountByExample(example);
        double v = Tools.keepThreePoint(i1 / i);
        double age20 = Tools.keepTwoPoint(v * 100);
        list.add(age20);
        //查询年龄在20和40之间的
        Example example1 = new Example(CrmClient.class);
        example1.createCriteria().andBetween("clientAge",20,40).andEqualTo("isDelete","0");
        double i2 = crmClientMapper.selectCountByExample(example1);
        double v1 = Tools.keepThreePoint(i2 / i);
        double age2040 = Tools.keepTwoPoint(v1*100);
        list.add(age2040);
        //查询年龄在40岁以上
        Example example2 = new Example(CrmClient.class);
        example2.createCriteria().andGreaterThan("clientAge",40).andEqualTo("isDelete","0");
        double i3 = crmClientMapper.selectCountByExample(example2);
        double v2 = Tools.keepThreePoint(i3 / i);
        double age40 = Tools.keepTwoPoint(v2*100);
        list.add(age40);
        return list;
    }

    /**
     * 查询客户地址分布信息
     */
    public List<Double> getClientAddress() throws Exception{
        List<Double> city = new ArrayList<>();
        //所有数量
        CrmClient crmClient = new CrmClient();
        crmClient.setIsDelete("0");
        double allCount = crmClientMapper.selectCount(crmClient);
        double firstCity = 0;//一线
        double newFirstCity = 0;//新一线
        double twoCity = 0;//二线
        double threeCity = 0;//三线
        double otherCity = 0;//其他线
        //一线
        List<CrmClient> clients = crmClientMapper.select(crmClient);
        for(CrmClient client : clients){
            String clientId = client.getClientId();
            CrmClientAddress crmClientAddress = new CrmClientAddress();
            crmClientAddress.setClientId(clientId);
            crmClientAddress.setIsValid("1");
            CrmClientAddress address = crmClientAddressMapper.selectOne(crmClientAddress);
            if(address != null){
                String city1 = address.getCity();
                if(city1 != null){
                    CrmDictCity crmDictCity = new CrmDictCity();
                    crmDictCity.setCityName(city1);
                    CrmDictCity crmDictCity1 = crmDictCityMapper.selectOne(crmDictCity);
                    if(crmDictCity1 != null){
                        String cityType = crmDictCity1.getCityType();
                        if("01".equals(cityType)){
                            firstCity++;
                        }else if("02".equals(cityType)){
                            newFirstCity++;
                        }else if("03".equals(cityType)){
                            twoCity++;
                        }else if("04".equals(cityType)){
                            threeCity++;
                        }else{
                            otherCity++;
                        }
                    }
                }
            }
        }
        double v = Tools.keepThreePoint(firstCity / allCount);
        double ct1 = Tools.keepTwoPoint(v*100);
        city.add(ct1);
        //新一线
        double v1 = Tools.keepThreePoint(newFirstCity / allCount);
        double ct11 = Tools.keepTwoPoint(v1*100);
        city.add(ct11);
        //二线
        double v2 = Tools.keepThreePoint(twoCity / allCount);
        double ct2 = Tools.keepTwoPoint(v2*100);
        city.add(ct2);
        //三线
        double v3 = Tools.keepThreePoint(threeCity / allCount);
        double ct3 = Tools.keepTwoPoint(v3*100);
        city.add(ct3);
        //其他
        double v4 = Tools.keepThreePoint(otherCity / allCount);
        double ct4 = Tools.keepTwoPoint(v4*100);
        city.add(ct4);
        return city;
    }

    /**
     * 查询客户列表
     */
    public List<ClientsVo> getClientsByUserId(ClientListSimpleVo clientListSimpleVo) throws Exception{
        List<CrmClient> clients = null;
        List<ClientsVo> clientsVos = null;
        if(clientListSimpleVo!=null){
            String userId = clientListSimpleVo.getUserId();
            CrmUser crmUser = crmUserMapper.selectByPrimaryKey(userId);
            if(crmUser!=null){
                clients = (List<CrmClient>) dao.getList("CrmClientMapper.getClients",clientListSimpleVo);
                clientsVos = new ArrayList<>();
                for(CrmClient crmClient : clients){
                    ClientsVo clientsVo = new ClientsVo();
                    BeanUtils.copyProperties(crmClient,clientsVo);
                    clientsVos.add(clientsVo);
                }
            }
        }
        return clientsVos;
    }

    /**
     * 删除客户
     */
    public void deleteClient(String clientId) throws Exception {
        CrmClient crmClient = crmClientMapper.selectByPrimaryKey(clientId);
        if (crmClient != null) {
            crmClient.setIsDelete("1");
            crmClientMapper.updateByPrimaryKey(crmClient);
            CrmCurrentRecord crmCurrentRecord = new CrmCurrentRecord();
            crmCurrentRecord.setUserId(crmClient.getUserId());
            crmCurrentRecord.setClientId(crmClient.getClientId());
            crmCurrentRecord.setRecordDesc("删除");
            crmCurrentRecord.setRecordId(Tools.get32UUID());
            crmCurrentRecord.setRecordTime(new Date());
            crmCurrentRecord.setRecordType(0);
            crmCurrentRecordMapper.insert(crmCurrentRecord);
        }
    }

    /**
     * 添加用戶
     */
    public void addClient(NewClientVo newClientVo) throws Exception{
        CrmClient crmClient = new CrmClient();
        CrmClientAddress crmClientAddress = new CrmClientAddress();
        BeanUtils.copyProperties(newClientVo,crmClient);
        BeanUtils.copyProperties(newClientVo,crmClientAddress);
        crmClient.setIsDelete("0");
        crmClient.setClientCreateTime(new Date());
        crmClient.setClientIcon(newClientVo.getIconName());
        crmClient.setClientId(Tools.get32UUID());
        String clientCredit = crmClient.getClientCredit();
        if("".equals(clientCredit)){
            crmClient.setClientCredit("100");
        }
        //客户编码
        //客户编码原则 KH+客户类型+4位数0000(根据客户数量)
        String clientCode = "KH";
        String clientType = crmClient.getClientType();
        clientCode += clientType;
        //根据客户类型查询数量
        CrmClient crmClient1 = new CrmClient();
        crmClient1.setIsDelete("0");
        crmClient1.setClientType(clientType);
        int count = crmClientMapper.selectCount(crmClient1);
        count += 1;
        String strCount = (new DecimalFormat("0000")).format(count);
        clientCode += strCount;
        crmClient.setClientCode(clientCode);
        crmClientMapper.insert(crmClient);
        //客户地址
        crmClientAddress.setIsValid("1");
        crmClientAddress.setClientId(crmClient.getClientId());
        crmClientAddress.setAddressId(Tools.get32UUID());
        crmClientAddressMapper.insert(crmClientAddress);
        //跟新记录信息
        CrmCurrentRecord crmCurrentRecord = new CrmCurrentRecord();
        crmCurrentRecord.setRecordId(Tools.get32UUID());
        crmCurrentRecord.setRecordType(1);
        crmCurrentRecord.setRecordTime(new Date());
        crmCurrentRecord.setRecordDesc("新增客户");
        crmCurrentRecord.setUserId(newClientVo.getUserId());
        crmCurrentRecord.setClientId(crmClient.getClientId());
        crmCurrentRecordMapper.insert(crmCurrentRecord);
    }

    /**
     * 查询一个客户
     */
    public NewClientVo getClient(String clientId) throws Exception{
        NewClientVo newClientVo = null;
        CrmClient crmClientExam = new CrmClient();
        crmClientExam.setClientId(clientId);
        crmClientExam.setIsDelete("0");
        CrmClient crmClient = crmClientMapper.selectOne(crmClientExam);
        if(crmClient!=null){
            newClientVo = new NewClientVo();
            BeanUtils.copyProperties(crmClient,newClientVo);
            newClientVo.setIconName(crmClient.getClientIcon());
            CrmClientAddress crmClientAddressExa = new CrmClientAddress();
            crmClientAddressExa.setClientId(clientId);
            crmClientAddressExa.setIsValid("1");
            CrmClientAddress crmClientAddress = crmClientAddressMapper.selectOne(crmClientAddressExa);
            if(crmClientAddress!=null){
                BeanUtils.copyProperties(crmClientAddress,newClientVo);
                newClientVo.setAddressId(crmClientAddress.getAddressId());
            }
            //根据地域名称查找对应编号
            //省份
            CrmDictProvince crmDictProvince = new CrmDictProvince();
            crmDictProvince.setProvinceName(newClientVo.getProvince());
            CrmDictProvince province = crmDictProvinceMapper.selectOne(crmDictProvince);
            newClientVo.setProvince(province.getProvinceCode());
            //城市
            CrmDictCity crmDictCity = new CrmDictCity();
            crmDictCity.setCityName(newClientVo.getCity());
            CrmDictCity city = crmDictCityMapper.selectOne(crmDictCity);
            newClientVo.setCity(city.getCityCode());
            //县
            CrmDictCountry crmDictCountry = new CrmDictCountry();
            crmDictCountry.setCountryName(newClientVo.getCountry());
            CrmDictCountry country = crmDictCountryMapper.selectOne(crmDictCountry);
            newClientVo.setCountry(country.getCountryCode());
        }
        return newClientVo;
    }

    /**
     * 修改客户
     */
    public String editClientDo(NewClientVo newClientVo, HttpServletRequest request) throws Exception{
        String message = "fail";
        if(newClientVo != null){
            //首先判断图片是否变化
            String clientId = newClientVo.getClientId();
            CrmClient crmClient = crmClientMapper.selectByPrimaryKey(clientId);
            if(crmClient!=null){
                String clientIcon = crmClient.getClientIcon();
                if(!clientIcon.equals(newClientVo.getIconName())){
                    //变化了执行复制保存图片并更新
                    String iconName = newClientVo.getIconName();
                    String srcPath = request.getSession().getServletContext().getRealPath("upload");
                    copyFile1(srcPath+"/"+iconName, Consts.UPLOAD_SAVE_ADDRESS+iconName);
                }
                BeanUtils.copyProperties(newClientVo,crmClient);
                crmClient.setClientIcon(newClientVo.getIconName());
                //地址更新
                String addressId = newClientVo.getAddressId();
                CrmClientAddress crmClientAddress = crmClientAddressMapper.selectByPrimaryKey(addressId);
                if(crmClientAddress!=null){
                    crmClientAddress.setCity(newClientVo.getCity());
                    crmClientAddress.setCountry(newClientVo.getCountry());
                    crmClientAddress.setProvince(newClientVo.getProvince());
                    crmClientAddress.setDetailed(newClientVo.getDetailed());
                    crmClientMapper.updateByPrimaryKey(crmClient);
                    crmClientAddressMapper.updateByPrimaryKey(crmClientAddress);
                    message = "success";
                }
            }
        }
        return message;
    }

    /**
     * 复制图片
     */
    public static void copyFile1(String srcPath, String destPath) throws IOException {
        // 打开输入流
        FileInputStream fis = new FileInputStream(srcPath);
        // 打开输出流
        FileOutputStream fos = new FileOutputStream(destPath);

        // 读取和写入信息
        int len = 0;
        while ((len = fis.read()) != -1) {
            fos.write(len);
        }

        // 关闭流  先开后关  后开先关
        fos.close(); // 后开先关
        fis.close(); // 先开后关

    }

    /**
     * 查询用户所有客户的集合
     */
    public List<OrClVO> getAllClient(String userId)throws Exception{
        List<OrClVO> orClVOS = new ArrayList<>();
        CrmClient crmClient = new CrmClient();
        crmClient.setUserId(userId);
        List<CrmClient> crmClients = crmClientMapper.select(crmClient);
        for(CrmClient client : crmClients){
            OrClVO orClVO = new OrClVO();
            BeanUtils.copyProperties(client,orClVO);
            orClVOS.add(orClVO);
        }
        return orClVOS;
    }
}
