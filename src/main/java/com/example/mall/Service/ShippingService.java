package com.example.mall.Service;

import com.example.mall.Form.ShippingAddForm;
import com.example.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface ShippingService {
    ResponseVo<PageInfo> getAllShipping(Integer uid, Integer pageNumber, Integer pageSize);
    ResponseVo<Map<String,Integer>> addShipping(Integer uid, ShippingAddForm shippingAddForm);
    ResponseVo deleteShipping(Integer uid,Integer shippingId);
    ResponseVo updateShipping(Integer uid,Integer shippingId,ShippingAddForm shippingAddForm);


}
