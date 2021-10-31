package com.cy.store.util;

import org.springframework.util.DigestUtils;

//密码进行MD5加密
public class MD5 {
    public static String toMD5(String password, String salt) {
        password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        return password;
    }
}
