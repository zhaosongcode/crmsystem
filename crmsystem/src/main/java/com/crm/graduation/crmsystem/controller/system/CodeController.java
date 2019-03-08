package com.crm.graduation.crmsystem.controller.system;

import com.crm.graduation.crmsystem.model.Consts.Consts;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 生成随机验证码
 */
@Controller
@RequestMapping("/code")
public class CodeController {

    private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public void generate(HttpServletResponse response){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = drawImg(output);

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute(Consts.SESSION_CODE, code);

        try {
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
            out.close();
        } catch (IOException e) {
            logger.error("验证码异常",e.getMessage());
        }
    }

    private String drawImg(ByteArrayOutputStream output){
        StringBuilder code = new StringBuilder();
        for(int i=0; i<4; i++){
            code.append(randomChar());
        }
        int width = 70;
        int height = 25;
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Font font = new Font("Times New Roman", Font.PLAIN,20);
        Graphics2D g = bi.createGraphics();
        g.setFont(font);
        Color color = new Color(66,2,82);
        g.setColor(color);
        g.setBackground(new Color(226,226,240));
        g.clearRect(0, 0, width, height);
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code.toString(), context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code.toString(), (int)x, (int)baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            logger.error("验证码图片异常",e.getMessage());
        }
        return code.toString();
    }

    private char randomChar(){
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }
}
