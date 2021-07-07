package com.example.mall.Service.Impl;

import com.example.mall.Form.CartAddForm;
import com.example.mall.Form.CartPutForm;
import com.example.mall.MallApplicationTests;
import com.example.mall.Service.CartService;
import com.example.mall.vo.CartVo;
import com.example.mall.vo.ResponseVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class CartServiceImplTest extends MallApplicationTests {
@Autowired
private CartService cartService;
private Gson gson=new GsonBuilder().setPrettyPrinting().create();
    @Before
    public void addCart() {
        CartAddForm cartAddForm=new CartAddForm();
        cartAddForm.setProductId(27);
        cartAddForm.setSelected(true);
        cartService.addCart(1,cartAddForm);
    }
    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info("list={}",gson.toJson(list));
       // Assert.assertEquals(ResponseVo.success().getStatus(),list.getStatus());
    }
    @Test
    public void update(){
        CartPutForm cartPutForm=new CartPutForm();
        cartPutForm.setQuantity(1);
        cartPutForm.setSelected(false);
        ResponseVo<CartVo> cartVoResponseVo = cartService.updateCart(1, 27, cartPutForm);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }
    @After
    public void deleteCartProduct(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.deleteCartById(1, 27);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }
    @Test
    public void selectAll(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.selectAll(1);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }
    @Test
    public void unSelectAll(){
        ResponseVo<CartVo> cartVoResponseVo = cartService.unSelectAll(1);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }
    @Test
    public void sumCart(){
        ResponseVo<Integer> cartVoResponseVo = cartService.sumCart(1);
        log.info("list={}",gson.toJson(cartVoResponseVo));
    }
}