package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    //通过父地区查找子地区
    public List<District> getByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);

        //设置属性为空，减少网络运输信息量
        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    //通过code获取地区名
    public String getNameByCode(String code) {
        String name = districtMapper.findNameByCode(code);
        return name;
    }
}
