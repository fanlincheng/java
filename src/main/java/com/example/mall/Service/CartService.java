package com.example.mall.Service;

import com.example.mall.Form.CartAddForm;
import com.example.mall.Form.CartPutForm;
import com.example.mall.pojo.Cart;
import com.example.mall.vo.CartVo;
import com.example.mall.vo.ResponseVo;

import java.util.List;

public interface CartService {
    ResponseVo<CartVo> addCart(Integer uid,CartAddForm cartAddForm);
    ResponseVo<CartVo> list(Integer uid);
    ResponseVo<CartVo> updateCart(Integer uid, Integer productId, CartPutForm cartPutForm);
    ResponseVo<CartVo> deleteCartById(Integer uid,Integer productId);
    ResponseVo<CartVo> selectAll(Integer uid);
    ResponseVo<CartVo> unSelectAll(Integer uid);
    ResponseVo<Integer> sumCart(Integer uid);
    List<Cart> getCartById(Integer uid);
}
