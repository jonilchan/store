package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ICartService;
import com.cy.store.service.IOrderService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IAddressService iAddressService;

    @Autowired
    private ICartService iCartService;

    @Override
    //创建订单
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        Date time = new Date();

        List<CartVO> cartVOs = iCartService.getVOByCids(uid, cids);
        Long totalPrice = 0L;
        for (CartVO cartVO : cartVOs) {
            totalPrice += cartVO.getRealPrice() * cartVO.getNum();
        }

        Order order = new Order();


        //补全订单的地址信息
        Address address = iAddressService.getByAid(aid, uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        //补全订单的用户id、状态、订单时间、订单总额
        order.setUid(uid);
        order.setStatus(0);
        order.setOrderTime(time);
        order.setTotalPrice(totalPrice);
        //补全订单的基本日志
        order.setCreatedUser(username);
        order.setCreatedTime(time);
        order.setModifiedUser(username);
        order.setModifiedTime(time);
        Integer orderRows = orderMapper.insertOrder(order);

        if (orderRows != 1) {
            throw new UpdateException("订单创建异常");
        }

        //订单项详细数据
        for (CartVO cartVO : cartVOs) {

            OrderItem orderItem = new OrderItem();
            //补全订单项的基本数据
            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setNum(cartVO.getNum());
            //补全订单项的基本数据
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(time);
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(time);
            orderMapper.insertOrderItem(orderItem);
            Integer orderItemRows = orderMapper.insertOrderItem(orderItem);
            if (orderItemRows != 1) {
                throw new InsertException("插入订单商品数据时出现未知错误");
            }
        }
        return order;
    }
}
