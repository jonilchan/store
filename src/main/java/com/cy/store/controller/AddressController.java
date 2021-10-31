package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseController {

    @Autowired
    private IAddressService iAddressService;

    //增加地址
    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        iAddressService.addNewAddress(uid, username, address);
        return new JsonResult<>(OK);
    }

    //显示地址
    @RequestMapping({"/", ""})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> data = iAddressService.getByUid(uid);
        return new JsonResult<>(OK, data);
    }

    //设置默认地址
    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        iAddressService.setDefault(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    //删除地址
    @RequestMapping("{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
        iAddressService.delete(aid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }
}
