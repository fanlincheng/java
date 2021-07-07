package com.example.mall.Controller;

import com.example.mall.Form.CartAddForm;
import com.example.mall.Form.CartPutForm;
import com.example.mall.MallConstant.MallConstant;
import com.example.mall.Service.CartService;
import com.example.mall.pojo.User;
import com.example.mall.vo.CartVo;
import com.example.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class CartController {
    /**
     * 表单验证
     * @param cartAddForm
     * @return
     */
    @Autowired
    private CartService cartService;
    @GetMapping("/cart/get")
    public ResponseVo<CartVo> getCartList(HttpSession httpSession){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        ResponseVo<CartVo> list = cartService.list(user.getId());
        return list;
    }
    @PostMapping("/cart/addcart")
    public ResponseVo<CartVo> add(HttpSession httpSession, @Valid @RequestBody CartAddForm cartAddForm){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        ResponseVo<CartVo> cartVoResponseVo = cartService.addCart(user.getId(), cartAddForm);
        return cartVoResponseVo;
    }

    @PutMapping("/cart/update/{productId}")
    public ResponseVo<CartVo> updateCart(HttpSession httpSession,
                                         @PathVariable Integer productId,
                                         @RequestBody CartPutForm cartPutForm){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        ResponseVo<CartVo> cartVoResponseVo = cartService.updateCart(user.getId(), productId, cartPutForm);
        return cartVoResponseVo;
    }
    @DeleteMapping("/cart/delete/{productId}")
    public ResponseVo<CartVo> deletetCart(HttpSession httpSession,
                                         @PathVariable Integer productId){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        ResponseVo<CartVo> cartVoResponseVo = cartService.deleteCartById(user.getId(),productId);
        return cartVoResponseVo;
    }
    @PutMapping("/cart/selectall")
    public ResponseVo<CartVo> selectAll(HttpSession httpSession){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
       return cartService.selectAll(user.getId());
    }
    @PutMapping("/cart/unselectall")
    public ResponseVo<CartVo> unselectAll(HttpSession httpSession){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        return cartService.unSelectAll(user.getId());
    }
    @GetMapping("/cart/sum")
    public ResponseVo<Integer> sumAll(HttpSession httpSession){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        return cartService.sumCart(user.getId());
    }
}
