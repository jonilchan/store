package com.cy.store.controller;

import com.cy.store.service.ICartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController {

    @Autowired
    private ICartService iCartService;

    //添加到购物车
    @RequestMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        iCartService.addToCart(uid, pid, amount, username);
        return new JsonResult<>(OK);
    }

    //显示购物车
    @RequestMapping({"/", ""})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        List<CartVO> data = iCartService.getVOByUid(getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }

    //购物车商品加一
    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = iCartService.addNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK, data);
    }

    //购物车商品减一
    @RequestMapping("{cid}/num/reduce")
    public JsonResult<Integer> subNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = iCartService.reduceNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK, data);
    }

    //返回CartVO列表
    @RequestMapping("list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        List<CartVO> data = iCartService.getVOByCids(getUidFromSession(session), cids);
        return new JsonResult<>(OK, data);
    }
}
