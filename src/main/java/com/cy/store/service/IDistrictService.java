package com.cy.store.service;

import com.cy.store.entity.District;

import java.util.List;

//地区业务层接口
public interface IDistrictService {

    /**
     * 根据父代号来查询区域信息（省市区）
     *
     * @param parent 父代号
     * @return 父代号下的子区域信息
     */
    List<District> getByParent(String parent);

    /**
     * 根据省/市/区的行政代号获取省/市/区的名称
     *
     * @param code 省/市/区的行政代号
     * @return 匹配的省/市/区的名称，如果没有匹配的数据则返回null
     */
    String getNameByCode(String code);
}
