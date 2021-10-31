package com.cy.store.service;

import com.cy.store.entity.Order;

//订单业务层接口
public interface IOrderService {

    /**
     * 创建订单
     *
     * @param aid      收货地址id
     * @param cids     该用户所有购物车id
     * @param uid      用户的id
     * @param username 用户名
     * @return 创建的订单
     */
    Order create(Integer aid, Integer[] cids, Integer uid, String username);
}
