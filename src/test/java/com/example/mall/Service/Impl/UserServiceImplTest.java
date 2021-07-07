package com.example.mall.Service.Impl;

import com.example.mall.MallApplicationTests;
import com.example.mall.enums.ResponseEnum;
import com.example.mall.enums.RoleEnum;
import com.example.mall.pojo.User;
import com.example.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 继承主类效果等同于
 * @Runwith(SpringRunner.class)
 * @SpringBootTest
 */
@Transactional
//加上@Transactional 不会真正写入数据库只是测试功能
public class UserServiceImplTest extends MallApplicationTests {
   @Autowired
   private UserServiceImpl userServiceImpl;
   public static final String username="hahah";
   public static final String password="123456";
    @Test
    public void register() {
        User user=new User(username,password,"fanlc@163.com", RoleEnum.ADMIN.getCode());
        ResponseVo register = userServiceImpl.register(user);
        System.out.println(register);
    }
    @Test
    public void login(){
        register();
        ResponseVo<User> login = userServiceImpl.login(username, password);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),login.getStatus());
    }
}