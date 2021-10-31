package com.cy.store.controller;

import com.cy.store.entity.District;
import com.cy.store.service.IDistrictService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("districts")
public class DistrictController extends BaseController {
    @Autowired
    private IDistrictService iDistrictService;

    //显示地区
    @RequestMapping({"/", ""})//这两种方法是一样的，调用districts地址就会调用该方法
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = iDistrictService.getByParent(parent);
        return new JsonResult<>(OK, data);
    }

}
