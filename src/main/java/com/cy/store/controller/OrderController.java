package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.service.IOrderService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService iOrderService;

    //创建订单
    @RequestMapping("create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        Order data = iOrderService.create(aid, cids, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK, data);
    }
}
