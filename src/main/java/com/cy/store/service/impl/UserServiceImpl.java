package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import com.cy.store.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块业务层的实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    //用户注册
    public void reg(User user) {

        String username = user.getUsername();

        User result = userMapper.findByUsername(username);

        if (result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }

        //设置用户初始数据值
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //追加盐值,生成新密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        user.setPassword(MD5.toMD5(user.getPassword(), salt));


        Integer rows = userMapper.insert(user);

        if (rows != 1) {
            throw new InsertException("在用户注册的过程中产生了异常！");
        }

    }

    @Override
    //用户登录
    public User login(String username, String password) {

        User result = userMapper.findByUsername(username);

        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户名不存在!");
        }

        String newMd5Password = MD5.toMD5(password, result.getSalt());

        if (!newMd5Password.equals(result.getPassword())) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        //减少数据在网络中的运输，也防止用户其它属性暴露
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    //更改密码
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户不存在");
        }
        String oldMd5Password = MD5.toMD5(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }

        String newMd5Password = MD5.toMD5(newPassword, result.getSalt());
        Integer row = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());

        if (row != 1) {
            throw new UpdateException("更新数据产生未知异常");
        }
    }

    @Override
    //通过uid获取用户对象
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("不存在该用户");
        }

        User user = new User();
        user.setUsername(result.getUsername());
        user.setGender(result.getGender());
        user.setEmail(result.getEmail());
        user.setPhone(result.getPhone());

        return user;
    }

    @Override
    //更改用户信息
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("不存在该用户");
        }

        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);

        if (rows != 1) {
            throw new UpdateException("数据更新产生异常");
        }
    }

    @Override
    //更改头像
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("不存在该用户");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新用户头像产生异常");
        }
    }


}
