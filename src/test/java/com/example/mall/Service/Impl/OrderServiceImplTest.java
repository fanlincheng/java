package com.example.mall.Service.Impl;

import com.example.mall.MallApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;
@Slf4j
public class OrderServiceImplTest extends MallApplicationTests {
    private Integer uid=1;
    private  Integer shippingId=1;
    @Autowired
    private OrderServiceImpl orderServiceImpl;
    @Test
    public void list(){
        orderServiceImpl.create(uid,shippingId);
    }


}