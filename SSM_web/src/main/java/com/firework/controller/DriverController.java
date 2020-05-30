package com.firework.controller;

import com.firework.bean.Driver;
import com.firework.service.DriverService;
import com.firework.service.RouteService;
import com.firework.util.UploadFile;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器-管理司机信息页面
 */
@Controller
@RequestMapping("/driver")
public class DriverController {

    // 注入业务对象
    @Autowired
    private RouteService routeService;
    @Autowired
    private DriverService driverService;

    //存储预返回页面的数据对象
    private Map<String, Object> result = new HashMap<>();

    /**
     * 跳转到司机信息管理页面
     */
    @GetMapping("/goDriverListView")
    public ModelAndView goDriverListView(ModelAndView modelAndView) {
        //向页面发送一个存储着Route的List对象
        modelAndView.addObject("routeList", routeService.selectAll());
        modelAndView.setViewName("driver/driverList");
        return modelAndView;
    }


    /**
     * 分页查询司机信息列表:根据司机与线路名查询指定/全部司机信息列表
     */
    @PostMapping("/getDriverList")
    @ResponseBody
    public Map<String, Object> getDriverList(Integer page, Integer rows, String drivername, String routename) {

        //存储查询的drivername,routename信息
        Driver driver = new Driver(drivername, routename);
        //设置每页的记录数
        PageHelper.startPage(page, rows);
        //根据线路与司机名获取指定或全部司机信息列表
        List<Driver> list = driverService.selectList(driver);
        //封装信息列表
        PageInfo<Driver> pageInfo = new PageInfo<>(list);
        //获取总记录数
        long total = pageInfo.getTotal();
        //获取当前页数据列表
        List<Driver> driverList = pageInfo.getList();
        //存储数据对象
        result.put("total", total);
        result.put("rows", driverList);

        return result;
    }

    /**
     * 添加司机信息
     */
    @PostMapping("/addDriver")
    @ResponseBody
    public Map<String, Object> addDriver(Driver driver) {
        //判断车牌号是否已存在
        if (driverService.fingBySno(driver) != null) {
            result.put("success", false);
            result.put("msg", "该车牌号已经存在! 请修改后重试!");
            return result;
        }
        //添加司机信息
        if (driverService.insert(driver) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "添加失败! 服务器端发生异常!");
        }

        return result;
    }

    /**
     * 根据id修改指定的司机信息
     */
    @PostMapping("/editDriver")
    @ResponseBody
    public Map<String, Object> editDriver(Driver driver) {
        if (driverService.update(driver) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "添加失败!服务器端发生异常!");
        }
        return result;
    }


    /**
     * 根据id删除指定的司机信息
     */
    @PostMapping("/deleteDriver")
    @ResponseBody
    public Map<String, Object> deleteDriver(@RequestParam(value = "ids[]", required = true) Integer[] ids) {

        if (driverService.deleteById(ids) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }


    /**
     * @description: 上传头像-原理:将头像上传到项目发布目录中,通过读取数据库中的头像路径来获取头像
     */
    @PostMapping("/uploadPhoto")
    @ResponseBody
    public Map<String, Object> uploadPhoto(MultipartFile photo, HttpServletRequest request) {
        //存储头像的本地目录
        final String dirPath = request.getServletContext().getRealPath("/upload/driver_portrait");
        //存储头像的项目发布目录
        final String portraitPath = request.getServletContext().getContextPath() + "/upload/driver_portrait";
        //返回头像的上传结果
        return UploadFile.getUploadResult(photo, dirPath, portraitPath);
    }
}