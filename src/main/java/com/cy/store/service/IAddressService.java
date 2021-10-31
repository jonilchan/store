package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

//收货地址业务层接口
public interface IAddressService {

    /**
     * 增加地址
     *
     * @param uid      用户的id
     * @param username 用户名
     * @param address  新增的地址信息
     */
    void addNewAddress(Integer uid, String username, Address address);

    /**
     * 通过uid查询地址详情
     *
     * @param uid 用户的id
     * @return 地址详情
     */
    List<Address> getByUid(Integer uid);

    /**
     * 设置默认地址
     *
     * @param aid      地址的id
     * @param uid      用户的id
     * @param username 用户名
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除地址
     *
     * @param aid      地址的id
     * @param uid      用户的id
     * @param username 用户名
     */
    void delete(Integer aid, Integer uid, String username);

    /**
     * 通过aid获取地址
     *
     * @param aid 地址的id
     * @param uid 用户的id
     * @return 地址详情
     */
    Address getByAid(Integer aid, Integer uid);
}
