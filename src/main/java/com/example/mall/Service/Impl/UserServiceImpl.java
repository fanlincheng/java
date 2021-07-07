package com.example.mall.Service.Impl;

import com.example.mall.Service.IUserService;
import com.example.mall.dao.UserMapper;
import com.example.mall.pojo.User;
import com.example.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

import static com.example.mall.enums.ResponseEnum.*;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseVo<User> register(User user) {
        /**
         * username和email重复
         */
//        int max = userMapper.getMax();
//        if(max<=0){
//            max=0;
//        }
//        user.setId(max+=1);
//        error();
        int count = userMapper.countByUsername(user.getUsername());
        if(count>0){
//            throw new RuntimeException(user.getUsername()+"already exists");
            return ResponseVo.error(USERNAME_EXIST);
        }
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail>0){
            //throw new RuntimeException(user.getEmail()+"already exists");
            return ResponseVo.error(USEREMAIL_EXIST);
        }
        //Md5摘要（Spring自带）
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        int rowNum = userMapper.insertSelective(user);
        if(rowNum<=0){
//            throw new RuntimeException("failed to insert");
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();
    }

    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUserName(username);
        if(StringUtils.isEmpty(user)){
           return ResponseVo.error(USERNAMEORPASSWORD_NOTEXIST);
        }
        if(!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            return ResponseVo.error(USERNAMEORPASSWORD_NOTEXIST);
        }
        user.setPassword(null);
        return ResponseVo.success(user);
    }
    //模拟错误
//    private void error(){
//        throw new RuntimeException("模拟错误");
//    }






}
