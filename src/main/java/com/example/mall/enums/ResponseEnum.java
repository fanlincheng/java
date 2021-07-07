package com.example.mall.enums;

import lombok.Getter;

@Getter
public enum  ResponseEnum {
     ERROR(-1,"服务端错误"),
    SUCCESS(0,"成功"),
          PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIST(2,"用户已存在"),
    USEREMAIL_EXIST(4,"邮箱已存在"),
    NEED_LOGIN(10,"用户未登录，请先登录"),
    PARA_ERROR(3,"参数错误"),
    USERNAMEORPASSWORD_NOTEXIST(11,"用户名或者密码错误"),
    PRODUCT_NOTEXIST(13,"商品不存在"),
    PRODUCT_OFFSALE_OR_DELETE(12,"商品下架或者删除"),
    PRODUCT_SALEOUT(13,"商品库存不足"),
    REDIS_CONNECT_FAILED(14,"redis数据库连接失败"),
    CART_PRODUCT_NOT_EXIST(15,"购物车内无此商品"),
    SHIPPING_NOT_EXIST(16,"收货地址不存在"),
    CART_SELECTED_IS_EMPTY(17,"请选中商品后下单"),
    ;
             Integer code;
         String desc;

     ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
