package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.utils.VerifyCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author JKXAING on 2020/8/9
 */

@RestController
public class LoginController {

    @GetMapping("login")
    public RespBean login(){
        return RespBean.error("请先登陆！");
    }

    @GetMapping("/verifyCode")
    public void getVerifyCode(HttpServletResponse resp, HttpSession session) throws IOException {
        VerifyCode code = new VerifyCode();
        BufferedImage image = code.getImage();
        session.setAttribute("verify_code", code.getText());
        VerifyCode.output(image, resp.getOutputStream());
    }
}
