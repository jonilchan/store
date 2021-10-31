package com.cy.store.service;

import com.cy.store.vo.CartVO;

import java.util.List;

//购物车业务层接口
public interface ICartService {

    /**
     * 添加到购物车
     *
     * @param uid      用户的id
     * @param pid      商品的id
     * @param amount   增加的数量
     * @param username 用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 获取该用户所有购物车
     *
     * @param uid 用户的id
     * @return 购物车列表
     */
    List<CartVO> getVOByUid(Integer uid);

    /**
     * 该购物车商品加一
     *
     * @param cid      购物车的id
     * @param uid      用户的id
     * @param username 用户名
     * @return 受影响的行数
     */
    Integer addNum(Integer cid, Integer uid, String username);

    /**
     * 该购物车商品减一
     *
     * @param cid      购物车的id
     * @param uid      用户的id
     * @param username 用户名
     * @return 受影响的行数
     */
    Integer reduceNum(Integer cid, Integer uid, String username);

    /**
     * @param uid  通过购物车列表获取CartVO
     * @param cids 购物车数组
     * @return 购物车VO列表
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);
}
