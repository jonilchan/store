package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
//新增收货地址的实现类
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IDistrictService iDistrictService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    //新增地址
    public void addNewAddress(Integer uid, String username, Address address) {

        //检验收货地址数量不超出限制
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用户收货地址数量超出上限");
        }

        //补全信息
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());
        String provinceName = iDistrictService.getNameByCode(address.getProvinceCode());
        String cityName = iDistrictService.getNameByCode(address.getCityCode());
        String areaName = iDistrictService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        //插入操作
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("新增用户收货地址数据产生未知异常");
        }
    }

    @Override
    //通过uid查找该用户所有地址
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    @Transactional//事务，要么全部完成，要么全部不完成
    //设置默认地址
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("不存在该地址");
        }
        if (!uid.equals(result.getUid())) {
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.updateNonDefaultByUid(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    //删除地址
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("不存在该地址");
        }
        if (!uid.equals(result.getUid())) {
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除数据产生未知异常");
        }
        if (result.getIsDefault() == 0) {
            return;
        }
        if (addressMapper.countByUid(result.getUid()) == 0) {
            return;
        }
        Address address = addressMapper.findLastModified(result.getUid());
        rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生异常");
        }
    }

    @Override
    //通过aid获取地址
    public Address getByAid(Integer aid, Integer uid) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("不存在该地址");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        result.setProvinceCode(null);
        result.setCityCode(null);
        result.setAreaCode(null);
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);

        return result;
    }
}
