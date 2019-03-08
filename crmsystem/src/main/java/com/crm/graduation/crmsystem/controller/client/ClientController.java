package com.crm.graduation.crmsystem.controller.client;

import com.crm.graduation.crmsystem.dao.mapper.user.CrmUserMapper;
import com.crm.graduation.crmsystem.entity.dict.CrmDataDict;
import com.crm.graduation.crmsystem.entity.system.user.CrmUser;
import com.crm.graduation.crmsystem.model.Consts.Consts;
import com.crm.graduation.crmsystem.model.vo.client.ClientListSimpleVo;
import com.crm.graduation.crmsystem.model.vo.client.ClientsVo;
import com.crm.graduation.crmsystem.model.vo.client.NewClientVo;
import com.crm.graduation.crmsystem.service.client.CrmClientService;
import com.crm.graduation.crmsystem.service.dict.CrmDictService;
import com.crm.graduation.crmsystem.utils.CommonMethod;
import com.crm.graduation.crmsystem.utils.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

/**
 * 客户管理
 */
@RequestMapping("/main/client")
@Controller
public class ClientController {

    private static Logger log = LoggerFactory.getLogger(ClientController.class);

    @Resource
    private CrmDictService dictService;

    @Resource
    private CrmClientService crmClientService;

    /**
     * 查询客户list
     * @param
     * @param modelMap
     * @return
     */
    @RequestMapping("/list")
    public String getList(ClientListSimpleVo clientListSimpleVo, ModelMap modelMap){
        modelMap.addAttribute("simpleVo",clientListSimpleVo);
        log.info("查询客户list");
        //查字典，查找客户类型
        try {
            CrmDataDict crmDataDict = dictService.getDictByCode(Consts.DICT_CLIENT_TYPE_CODE);
            List<CrmDataDict> clientType = null;
            if(crmDataDict != null){
                String partenId = crmDataDict.getDictId();
                //根据父id查询子字典
                clientType = dictService.getListDict(partenId);
            }
            modelMap.addAttribute("clientType",clientType);
            //查客户list
            List<ClientsVo> clients = crmClientService.getClientsByUserId(clientListSimpleVo);
            modelMap.addAttribute("clients",clients);
            modelMap.addAttribute("userId",clientListSimpleVo.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "client/list";
    }

    /**
     * 删除客户
     */
    @RequestMapping("/deleteClient")
    @ResponseBody
    public String deleteClient(String clientIds){
        log.info("删除用户");
        String result = null;
        if(clientIds!=null&&!"".equals(clientIds.trim())){
            try {
                String[] split = clientIds.split(",");
                for(String clientId : split){
                    crmClientService.deleteClient(clientId);
                }
                result = "success";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 跳转新增页面
     */
    @RequestMapping("/add")
    public String toAdd(ModelMap modelMap){
        try {
            CommonMethod method = new CommonMethod();
            method.getDict(modelMap,dictService);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "client/add";
    }

    /**
     * 上传图片临时存放
     *
     * @param
     * @return
     */
    @RequestMapping("/upLoadImage")
    @ResponseBody
    public ResultDto uploadFile(MultipartFile icon, HttpServletRequest request) throws Exception {
        log.info("上传图片");
        String message = "";
        if (icon == null || icon.isEmpty()) {
            message = "fileIsEmpty"; // 文件或内容为空
            return ResultDto.error(message);
        }
        // 判断图片的格式是否符合要求
        String fileName = icon.getOriginalFilename(); // 上传的文件名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!"jpg".equals(suffix)) {
            message = "formatError"; // 文件格式错误
            return ResultDto.error(message);
        }
        BufferedImage image = ImageIO.read(icon.getInputStream());
        if (image == null) {
            message = "formatError"; // 文件格式错误
            return ResultDto.error(message);
        }

        // 判断图片的大小是否符合要求
        Long iconSize = icon.getSize() / 1024;
        if (iconSize > 512) {
            message = "sizeTooBig"; // 图片超出指定大小
            return ResultDto.error(message);
        }

        // 判断图片的尺寸是否符合要求
        int width = image.getWidth();
        int height = image.getHeight();
        if (width != 60 || height != 60) {
            message = "sizeError"; // 图片尺寸不合适
            return ResultDto.error(message);
        }
        String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/";//存储路径
        String path = request.getSession().getServletContext().getRealPath("upload"); //文件存储位置
        String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
        fileName=new Date().getTime()+"_"+new Random().nextInt(1000)+fileF;//新的文件名
        //先判断文件是否存在
        File file1 =new File(path);
        //如果文件夹不存在则创建
        if(!file1 .exists()  && !file1 .isDirectory()){
            file1 .mkdir();
        }
        File targetFile = new File(file1, fileName);
        try {
            icon.transferTo(targetFile);
            message=fileName;
            return ResultDto.success(message);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error();
        }
    }

    /**
     * 添加客户
     */
    @RequestMapping("/addClient")
    @ResponseBody
    public ResultDto addClient(NewClientVo clientVo,HttpServletRequest request){
        try {
            //保存图片
            String iconName = clientVo.getIconName();
            String srcPath = request.getSession().getServletContext().getRealPath("upload");
            crmClientService.copyFile1(srcPath+"/"+iconName,Consts.UPLOAD_SAVE_ADDRESS+iconName);
            //保存新用户
            crmClientService.addClient(clientVo);
            return ResultDto.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.error();
        }
    }

    /**
     * 编辑客户
     */
    @RequestMapping("/editClient")
    public String editClient(String clientId, ModelMap modelMap){
        try {
            //字典
            CommonMethod method = new CommonMethod();
            method.getDict(modelMap,dictService);
            //客户信息
            NewClientVo client = crmClientService.getClient(clientId);
            modelMap.addAttribute("client",client);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "client/edit";
    }

    /**
     * 修改动作
     * @return
     */
    @RequestMapping("/editClientDo")
    @ResponseBody
    public String editClientDo(NewClientVo newClientVo,HttpServletRequest request){
        String message = null;
        try {
            message = crmClientService.editClientDo(newClientVo, request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }

}
