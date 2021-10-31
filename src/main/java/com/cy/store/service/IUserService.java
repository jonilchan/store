package com.cy.store.service;

import com.cy.store.entity.User;

//用户模块业务层接口
public interface IUserService {

    /**
     * 用户注册
     *
     * @param user 用户信息
     */
    void reg(User user);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 用户详情
     */
    User login(String username, String password);

    /**
     * 更改密码
     *
     * @param uid         用户的id
     * @param username    用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid, String username, String oldPassword, String newPassword);

    /**
     * 通过uid获取用户详情
     *
     * @param uid 用户的id
     * @return 用户详情
     */
    User getByUid(Integer uid);

    /**
     * 更改用户信息
     *
     * @param uid      用户的id
     * @param username 用户名
     * @param user     用户更改的信息
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 更改头像
     *
     * @param uid      用户的id
     * @param avatar   头像路径
     * @param username 用户名
     */
    void changeAvatar(Integer uid, String avatar, String username);
}
