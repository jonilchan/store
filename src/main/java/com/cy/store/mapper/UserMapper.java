package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

//处理用户数据操作的持久层接口
public interface UserMapper {
    /**
     * 插入用户数据
     *
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     *
     * @param username 用户名
     * @return 匹配的用户数据，如果没有匹配的数据，则返回null
     */
    User findByUsername(@Param("username") String username);

    /**
     * 根据uid更改用户密码
     *
     * @param uid          用户的id
     * @param modifiedUser 修改密码的执行人
     * @param modifiedTime 修改密码的时间
     * @return 返回受影响的行数
     * @return 返回受影响的行数
     */
    Integer updatePasswordByUid(
            @Param("uid") Integer uid,
            @Param("password") String password,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime
    );

    /**
     * 根据用户的id查询用户的数据
     *
     * @param uid 用户的id
     * @return 如果找到则返回对象，否则返回null值
     */
    User findByUid(Integer uid);

    /**
     * 根据用户的id更新用户的信息
     *
     * @param user 封装新数据的user对象
     * @return
     */
    Integer updateInfoByUid(User user);


    /**
     * //@Param("SQL映射文件中#{}占位符的变量名") 解决占位符和变量名不一致的问题
     * 根据用户的id更新用户的头像
     *
     * @param uid          用户的id
     * @param avatar       用户的头像
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 返回受影响的行数
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);

}