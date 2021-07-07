package com.example.mall.Controller;

import com.example.mall.Form.ShippingAddForm;
import com.example.mall.MallConstant.MallConstant;
import com.example.mall.Service.ShippingService;
import com.example.mall.pojo.User;
import com.example.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@RestController
@Slf4j
public class ShippingController {
    @Autowired
    private ShippingService shippingService;
    @GetMapping("/shipping/{id}")
    public ResponseVo<PageInfo> getAllShipping(HttpSession httpSession, @RequestParam Integer pageNumber,
                                               @RequestParam Integer pageSize){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        return shippingService.getAllShipping(user.getId(),pageNumber,pageSize);
    }
    @PostMapping("/shipping/")
    public ResponseVo<Map<String,Integer>> addShipping(HttpSession httpSession,
                                                      @Valid @RequestBody ShippingAddForm shippingAddForm){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        return shippingService.addShipping(user.getId(),shippingAddForm);
    }
    @DeleteMapping("/shipping/{shippingId}")
    public ResponseVo deleteShipping(HttpSession httpSession,
                                     @PathVariable Integer shippingId ){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        return shippingService.deleteShipping(user.getId(),shippingId);
    }
    @PutMapping("/shipping/{shippingId}")
    public ResponseVo updateShipping(HttpSession httpSession,
                                     @PathVariable Integer shippingId,
                                    @Valid @RequestBody ShippingAddForm shippingAddForm){
        User user=(User)httpSession.getAttribute(MallConstant.CURRENTUSER);
        return shippingService.updateShipping(user.getId(),shippingId,shippingAddForm);
    }
}
