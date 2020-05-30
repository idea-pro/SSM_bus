package com.firework.controller;

import com.firework.bean.Route;
import com.firework.service.RouteService;
import com.firework.service.ZoneService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器-管理线路信息页面
 */
@Controller
@RequestMapping("/route")
public class RouteController {

    //注入业务对象
    @Autowired
    private RouteService routeService;
    @Autowired
    private ZoneService zoneService;

    //存储预返回页面的数据对象
    private Map<String, Object> result = new HashMap<>();


    /**
     * 跳转到线路信息管理页面
     */
    @GetMapping("/goRouteListView")
    public ModelAndView goRouteListPage(ModelAndView modelAndView) {
        //向页面发送一个存储着Zone的List对象
        modelAndView.addObject("zoneList", zoneService.selectAll());

        modelAndView.setViewName("route/routeList");
        return modelAndView;
    }


    /**
     * 分页查询线路信息列表:根据线路与片区名查询指定/全部线路信息列表
     * page 当前页码
     * rows 列表行数
     */
    @PostMapping("/getRouteList")
    @ResponseBody
    public Map<String, Object> getRouteList(Integer page, Integer rows, String routename, String zonename) {

        //存储查询的routename,zonename信息
        Route route = new Route(routename, zonename);
        //设置每页的记录数
        PageHelper.startPage(page, rows);
        //根据线路与片区名获取指定或全部线路信息列表
        List<Route> list = routeService.selectList(route);
        //封装列表信息
        PageInfo<Route> pageInfo = new PageInfo<>(list);
        //获取总记录数
        long total = pageInfo.getTotal();
        //获取当前页数据列表
        List<Route> routeList = pageInfo.getList();
        //存储数据对象
        result.put("total", total);
        result.put("rows", routeList);

        return result;
    }


    /**
     * 添加线路信息
     */
    @PostMapping("/addRoute")
    @ResponseBody
    public Map<String, Object> addRoute(Route route) {
        //判断线路名是否已存在
        Route name = routeService.findByName(route.getName());
        if (name == null) {
            if (routeService.insert(route) > 0) {
                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("msg", "添加失败!服务器端发生异常!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "该线路名称已存在! 请修改后重试!");
        }

        return result;
    }

    /**
     * @description: 根据id修改指定的线路信息
     */
    @PostMapping("/editRoute")
    @ResponseBody
    public Map<String, Object> editRoute(Route route) {
        //若线路名称已存在则修改失败
        Route c = routeService.findByName(route.getName());
        if (c != null) {
            if (!(route.getId().equals(c.getId()))) {
                result.put("success", false);
                result.put("msg", "该线路名称已存在! 请修改后重试!");
                return result;
            }
        }
        //添加操作
        if (routeService.update(route) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "添加失败! 服务器端发生异常!");
        }
        return result;
    }


    /**
     * 删除指定id的线路信息
     */
    @PostMapping("/deleteRoute")
    @ResponseBody
    public Map<String, Object> deleteZone(@RequestParam(value = "ids[]", required = true) Integer[] ids) {

        if (routeService.deleteById(ids) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }

}
