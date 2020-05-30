package com.firework.dao;

import com.firework.bean.Driver;
import com.firework.bean.LoginForm;

import java.util.List;

/**
 * 数据访问层-操控司机信息
 */
public interface DriverMapper {

    //验证登录信息是否正确
    Driver login(LoginForm loginForm);

    //根据线路与司机名获取指定或全部司机信息列表
    List<Driver> selectList(Driver driver);

    //根据车牌查询指定司机信息
    Driver findBySno(Driver driver);

    //添加司机信息
    int insert(Driver driver);

    //根据id修改指定司机信息
    int update(Driver driver);

    //根据id修改指定司机密码
    int updatePassword(Driver driver);

    //根据id删除指定司机信息
    int deleteById(Integer[] ids);

}
