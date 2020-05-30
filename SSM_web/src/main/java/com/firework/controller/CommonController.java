package com.firework.controller;

import com.firework.bean.Admin;
import com.firework.bean.Driver;
import com.firework.service.AdminService;
import com.firework.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器-修改密码功能+跳转信息管理页面
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    //注入业务对象
    @Autowired
    private AdminService adminService;
    @Autowired
    private DriverService driverService;

    //存储预返回给页面的结果对象
    private Map<String, Object> result = new HashMap<>();

    /**
     * 跳转到个人信息管理页面
     */
    @GetMapping("/goSettingView")
    public String getAdminList() {
        return "common/settings";
    }

    /**
     * 修改密码
     */
    @PostMapping("/editPassword")
    @ResponseBody
    public Map<String, Object> editPassword(String oldPassword, String newPassword, HttpServletRequest request) {
        //判断当前登录用户的用户类型
        int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
        //管理员身份
        if (userType == 1) {
            Admin admin = (Admin) request.getSession().getAttribute("userInfo");
            if (!admin.getPassword().equals(oldPassword)) {
                result.put("success", false);
                result.put("msg", "原密码错误!");
                return result;
            }
            try {
                //修改密码
                admin.setPassword(newPassword);//覆盖旧密码值,存储待更新的密码
                if (adminService.updatePassowrd(admin) > 0) {
                    result.put("success", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "修改失败! 服务器端发生异常!");
            }
        }

        //司机身份
        if (userType == 2) {
            Driver driver = (Driver) request.getSession().getAttribute("userInfo");
            if (!driver.getPassword().equals(oldPassword)) {
                result.put("success", false);
                result.put("msg", "原密码错误!");
                return result;
            }
            try {
                driver.setPassword(newPassword);
                if (driverService.updatePassowrd(driver) > 0) {
                    result.put("success", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "修改失败! 服务器端发生异常!");
            }
        }

        return result;
    }

}