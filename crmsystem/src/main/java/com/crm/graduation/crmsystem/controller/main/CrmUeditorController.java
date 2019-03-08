package com.crm.graduation.crmsystem.controller.main;

import com.baidu.ueditor.ActionEnter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
@RequestMapping("/main")
public class CrmUeditorController {

    private static final Logger logger = LoggerFactory.getLogger(CrmUeditorController.class);

    //ueditr后端配置
    @ResponseBody
    @RequestMapping(value = "/ueditor")
    public void ueditor(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        try {
//            if("config".equals(action)){
//                request.setCharacterEncoding("utf-8");
//                response.setHeader("Content-Type" , "text/html");
//                ClassPathResource resource = new ClassPathResource("config.json");
//                File file = resource.getFile();
//                BufferedReader br = new BufferedReader(new FileReader(file));
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = br.readLine()) != null){
//                    stringBuilder.append(line);
//                }
//                System.out.println(stringBuilder.toString());
//                return stringBuilder.toString();
//            }else{
//                String realPath = request.getSession().getServletContext().getRealPath("/");
//                String exec = new ActionEnter(request, realPath).exec();
//                return exec;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return "error";
//        }
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");

        String realPath = request.getSession().getServletContext().getRealPath("upload");
        PrintWriter out = response.getWriter();
        String exec = new ActionEnter(request, realPath).exec();

        logger.info("ueditor后台配置"+exec);

        out.flush();
        out.write(exec);
    }
}
