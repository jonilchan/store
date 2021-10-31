package com.cy.store.controller;

import com.cy.store.controller.ex.FileStateException;
import com.cy.store.controller.ex.FileUploadIOException;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.FileCheck;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController extends BaseController {

    @Autowired
    private IUserService iUserService;

    //用户注册
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        iUserService.reg(user);
        return new JsonResult<>(OK);
    }

    //用户登录
    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = iUserService.login(username, password);

        //向session绑定数据
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());

        return new JsonResult<>(OK, data);
    }

    //更改密码
    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        iUserService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }

    //勇敢uid查询用户
    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = iUserService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    //更改用户信息
    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        iUserService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }

    //更改头像
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {

        String parent = session.getServletContext().getRealPath("upload");
        //检查文件，该工具类会直接抛异常
        FileCheck.fileCheck(file);
        File dir = new File(parent);

        if (!dir.exists()) {
            dir.mkdir();
        }
        //使用UUID来更改用户头像名，防止头像名碰撞。虽然用盐值也可以达到同样的效果，但是会有一次数据库查询工作
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String filename = UUID.randomUUID() + originalFilename.substring(index);
        File dest = new File(dir, filename);

        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            throw new FileStateException("文件状态异常，文件可能被移动或删除");
        } catch (IOException e) {
            throw new FileUploadIOException("上传文件时错误，请稍后再试");
        }

        String avatar = "/upload/" + filename;
        iUserService.changeAvatar(getUidFromSession(session), avatar, getUsernameFromSession(session));

        return new JsonResult<>(OK, avatar);
    }
}
