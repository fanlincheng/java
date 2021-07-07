package com.example.mall.Service;

import com.example.mall.vo.OrderVo;
import com.example.mall.vo.ResponseVo;

public interface OrderService {
    ResponseVo<OrderVo> create(Integer uid,Integer shippingId);
}
