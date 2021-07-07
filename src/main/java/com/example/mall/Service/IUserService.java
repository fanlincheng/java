package com.example.mall.Service;

import com.example.mall.pojo.User;
import com.example.mall.vo.ResponseVo;

public interface IUserService {
    /**
     * 注册
     * @param user
     * @return
     */
    ResponseVo<User> register(User user);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    ResponseVo<User> login(String username,String password);

}
