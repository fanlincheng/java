package com.example.mall.vo;

import com.example.mall.enums.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
//返回值不包含空的字段
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    private Integer status;
    private String msg;
    private T data;

    private ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    private ResponseVo(Integer status,T data){
        this.status=status;
        this.data=data;
    }
    public static <T> ResponseVo<T> successByMsg(String msg){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),msg);
    }
    public static <T> ResponseVo<T> success(){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }
    public static <T> ResponseVo<T> success(T data){
        return new ResponseVo<T>(ResponseEnum.SUCCESS.getCode(), data);
    }
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo<>(responseEnum.getCode(),responseEnum.getDesc());
    }
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseVo<>(responseEnum.getCode(),bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
    }
    public static <T> ResponseVo<T> passwordError(){
        return new ResponseVo<>(ResponseEnum.PASSWORD_ERROR.getCode(),ResponseEnum.PASSWORD_ERROR.getDesc());
    }
    public static <T> ResponseVo<T> userNameExits(){
        return new ResponseVo<>(ResponseEnum.USERNAME_EXIST.getCode(),ResponseEnum.USERNAME_EXIST.getDesc());
    }
    public static <T> ResponseVo<T> userEmailExits(){
        return new ResponseVo<>(ResponseEnum.USEREMAIL_EXIST.getCode(),ResponseEnum.USEREMAIL_EXIST.getDesc());
    }
    public static <T> ResponseVo<T> needLogin(){
        return new ResponseVo<>(ResponseEnum.NEED_LOGIN.getCode(),ResponseEnum.NEED_LOGIN.getDesc());
    }
}
