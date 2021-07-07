package com.example.mall.dao;

import com.example.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);
    int deleteByUidAndPrimaryKey(Integer uid,Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
    List<Shipping> selectAllShipping(Integer uid);
    Shipping selectByUserIdAndShipId(@Param("uid") Integer uid, @Param("shippingId") Integer shippingId);
}