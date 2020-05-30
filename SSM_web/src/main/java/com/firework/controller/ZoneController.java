package com.firework.controller;

import com.firework.bean.Zone;
import com.firework.service.ZoneService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器-管理片区信息页面
 */

@Controller
@RequestMapping("/zone")
public class ZoneController {

    // 注入业务对象
    @Autowired
    private ZoneService zoneService;

    // 存储预返回页面的结果对象
    private Map<String, Object> result = new HashMap<>();

    /**
     * 跳转到片区信息管理页面
     */
    @GetMapping("/goZoneListView")
    public String goZoneListPage() {
        return "zone/zoneList";
    }

    /**
     * 分页查询:根据片区名称获取指定/所有片区信息列表
     */
    @PostMapping("/getZoneList")
    @ResponseBody
    public Map<String, Object> getZoneList(Integer page, Integer rows, String zonename) {

        Zone zone = new Zone();
        zone.setName(zonename);

        //设置每页的记录数
        PageHelper.startPage(page, rows);
        //根据片区名称获取指定或全部片区信息列表
        List<Zone> list = zoneService.selectList(zone);
        //封装信息列表
        PageInfo<Zone> pageInfo = new PageInfo<>(list);
        //获取总记录数
        long total = pageInfo.getTotal();
        //获取当前页数据列表
        List<Zone> zoneList = pageInfo.getList();
        //存储数据对象
        result.put("total", total);
        result.put("rows", zoneList);

        return result;
    }


    /**
     * 添加片区信息
     */
    @PostMapping("/addZone")
    @ResponseBody
    public Map<String, Object> addZone(Zone zone) {
        //判断片区名是否已存在
        Zone name = zoneService.findByName(zone.getName());
        if (name == null) {
            if (zoneService.insert(zone) > 0) {
                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("msg", "添加失败! 服务器端发生异常!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "该片区名称已存在! 请修改后重试!");
        }

        return result;
    }

    /**
     * 根据id修改指定的片区信息
     */
    @PostMapping("/editZone")
    @ResponseBody
    public Map<String, Object> editZone(Zone zone) {

        Zone g = zoneService.findByName(zone.getName());
        if (g != null) {
            if (!(zone.getId().equals(g.getId()))) {
                result.put("success", false);
                result.put("msg", "该片区名称已存在! 请修改后重试!");
                return result;
            }
        }
        //添加操作
        if (zoneService.update(zone) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("msg", "添加失败! 服务器端发生异常!");
        }
        return result;
    }


    /**
     * 根据id删除指定的片区信息
     */
    @PostMapping("/deleteZone")
    @ResponseBody
    public Map<String, Object> deleteZone(@RequestParam(value = "ids[]", required = true) Integer[] ids) {

        if (zoneService.deleteById(ids) > 0) {
            result.put("success", true);
        } else {
            result.put("success", false);
        }
        return result;
    }

}