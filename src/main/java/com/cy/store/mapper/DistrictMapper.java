package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

//地区持久层接口
public interface DistrictMapper {

    /**
     * 根据父代号查询地区
     *
     * @param parent 夫代号
     * @return 返回夫区域下所有的列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据省/市/区的行政代号获取省/市/区的名称
     *
     * @param code 省/市/区的行政代号
     * @return 匹配的省/市/区的名称，如果没有匹配的数据则返回null
     */
    String findNameByCode(String code);
}
