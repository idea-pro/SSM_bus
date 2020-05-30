package com.firework.service;

import com.firework.bean.Admin;
import com.firework.bean.LoginForm;

import java.util.List;

/**
 * 业务层-操控管理员信息
 */
public interface AdminService {

    //验证登录信息是否正确
    Admin login(LoginForm loginForm);

    //根据用户名查询指定管理员信息
    Admin findByName(String name);

    //添加管理员信息
    int insert(Admin admin);

    //根据姓名模糊查询指定/所有管理员信息列表
    List<Admin> selectList(Admin admin);

    //根据id更新指定管理员信息
    int update(Admin admin);

    //根据id修改指定管理员密码
    int updatePassowrd(Admin admin);

    //根据id删除管理员信息
    int deleteById(Integer[] ids);

}