package com.example.mall.enums;

import lombok.Getter;

@Getter
public enum  ProductStatusEnum {
    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3),
    ;
    int code;
    ProductStatusEnum(Integer code){
        this.code=code;
    }
}
