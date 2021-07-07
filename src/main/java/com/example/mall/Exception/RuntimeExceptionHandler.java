package com.example.mall.Exception;

import com.example.mall.vo.ResponseVo;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

import static com.example.mall.enums.ResponseEnum.*;

@ControllerAdvice
public class RuntimeExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    //改变返回状态码@ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseVo handle(RuntimeException e){
        return  ResponseVo.error(ERROR);
    }
    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle(){
        return ResponseVo.error(NEED_LOGIN);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo notValidExceptionHandle(MethodArgumentNotValidException e){
        BindingResult bindingResult=e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError());
        return ResponseVo.error(PARA_ERROR,bindingResult);
    }
    @ExceptionHandler(RedisConnectionFailureException.class)
    @ResponseBody
    public ResponseVo connectFailureExceptionHandle(RedisConnectionFailureException e){
        return ResponseVo.error(REDIS_CONNECT_FAILED);
    }
}
