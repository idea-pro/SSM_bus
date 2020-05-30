package com.firework.controller;

import com.firework.bean.Admin;
import com.firework.bean.Driver;
import com.firework.bean.LoginForm;
import com.firework.service.AdminService;
import com.firework.service.DriverService;
import com.firework.util.CreateVerifiCodeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器-管理系统登录与跳转登录主页面
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    //注入业务对象
    @Autowired
    private AdminService adminService;
    @Autowired
    private DriverService driverService;

    //存储返回给页面的对象数据
    private Map<String, Object> result = new HashMap<>();

    /**
     * 跳转到用户登录页面
     */
    @GetMapping("/goLogin")
    public String goLogin() {
        return "system/login";
    }

    /**
     * 获取并显示验证码图片
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        // 验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // 验证码
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());
        // 将验证码图片输出到登录页面
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 存储验证码Session
        request.getSession().setAttribute("verifiCode", verifiCode);
    }

    /**
     * 验证用户登录信息
     */
    @PostMapping("login")
    @ResponseBody
    public Map<String, Object> login(LoginForm loginForm, HttpServletRequest request) {

        // 校验验证码信息
        String vcode = (String) request.getSession().getAttribute("verifiCode");
        if ("".equals(vcode)) {
            result.put("success", false);
            result.put("msg", "长时间未操作,会话已失效,请刷新页面后重试!");
            return result;
        } else if (!loginForm.getVerifiCode().equalsIgnoreCase(vcode)) {
            result.put("success", false);
            result.put("msg", "验证码错误!");
            return result;
        }
        request.getSession().removeAttribute("verifiCode");

        //效验用户登录信息
        switch (loginForm.getUserType()) {
            //管理员身份
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);//验证账户和密码是否存在
                    if (admin != null) {
                        HttpSession session = request.getSession(); //将用户信息存储到Session
                        session.setAttribute("userInfo", admin);
                        session.setAttribute("userType", loginForm.getUserType());
                        result.put("success", true);
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.put("success", false);
                    result.put("msg", "登录失败! 服务器端发生异常!");
                    return result;
                }
                break;
            //司机身份
            case 2:
                try {
                    Driver driver = driverService.login(loginForm);
                    if (driver != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("userInfo", driver);
                        session.setAttribute("userType", loginForm.getUserType());
                        result.put("success", true);
                        return result;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    result.put("success", false);
                    result.put("msg", "登录失败! 服务器端发生异常!");
                    return result;
                }
                break;
        }
        //登录失败
        result.put("success", false);
        result.put("msg", "用户名或密码错误!");
        return result;
    }

    /**
     * 跳转到系统主页面
     */
    @GetMapping("/goSystemMainView")
    public String goSystemMainView() {
        return "system/main";
    }


    /**
     * 注销用户信息（用于修改密码后自动跳转页面）
     */
    @GetMapping("/loginOut")
    public void loginOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("userInfo");
        request.getSession().removeAttribute("userType");
        //注销后重定向到登录页面
        try {
            response.sendRedirect("../index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
