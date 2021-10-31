package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

//购物车持久层接口
public interface CartMapper {

    /**
     * 插入购物车数据
     *
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 更新购物车某件商品的数量
     *
     * @param cid          购物车数据id
     * @param num          商品更新的数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(@Param("cid") Integer cid,
                           @Param("num") Integer num,
                           @Param("modifiedUser") String modifiedUser,
                           @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户的id和商品的id来查询购物车中某件商品的数据
     *
     * @param uid 用户的uid
     * @param pid 产品的id
     * @return 购物车某件商品的数据
     */
    Cart findByUidAndPid(@Param("uid") Integer uid, @Param("pid") Integer pid);

    /**
     * 根据uid查找CartVo对象
     *
     * @param uid 用户id
     * @return 该用户所有CartVO对象
     */
    List<CartVO> findVOByUid(Integer uid);

    /**
     * 根据购物车id查找购物车
     *
     * @param cid 购物车id
     * @return 购物车详情
     */
    Cart findByCid(Integer cid);

    /**
     * 根据若干购物车数据id查询详情的列表
     *
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVO> findVOByCids(Integer[] cids);
}
