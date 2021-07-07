package com.example.mall.Controller;

import com.example.mall.Form.UserLogin;
import com.example.mall.Form.UserRegist;
import com.example.mall.MallConstant.MallConstant;
import com.example.mall.Service.IUserService;
import com.example.mall.pojo.User;
import com.example.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
//加上@Valid注解的原因日志打印错误
public class UserController {
    @Autowired
    private IUserService iUserService;
    @PostMapping("/user/register")
    public ResponseVo<User> registUser(@Valid @RequestBody UserRegist userForm){

//        if(bindingResult.hasErrors()){
//            log.error("注册提交的信息有误 ,{}{}",bindingResult.getFieldError().getField(),
//                    bindingResult.getFieldError().getDefaultMessage());
//            return ResponseVo.error(PARA_ERROR,bindingResult);
//        }
        User user=new User();
        BeanUtils.copyProperties(userForm,user);
        return iUserService.register(user);


//        log.info("username={}",userForm.getUsername());
//       //return ResponseVo.success();
//        return ResponseVo.error(NEED_LOGIN);
    }
    @PostMapping("/user/login")
    public ResponseVo<User> login(@RequestBody UserLogin userLogin, HttpSession session){
//        if(bindingResult.hasErrors()){
//            log.error("登录提交的信息有误 ,{}{}",bindingResult.getFieldError().getField(),
//                    bindingResult.getFieldError().getDefaultMessage());
//            return ResponseVo.error(PARA_ERROR,bindingResult);
//        }
        ResponseVo<User> login = iUserService.login(userLogin.getUsername(), userLogin.getPassword());
        session.setAttribute(MallConstant.CURRENTUSER,login.getData());
        log.info("/login sessionId={}",session.getId());
        return login;
    }
    //session保存在内存里，重启就会没
    //改进版：token+redis
    //cookie跨域（localhost/127.0.0.1也算跨域）
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session) {
        log.info("/user  sessionId={}",session.getId());
        User user = (User) session.getAttribute(MallConstant.CURRENTUSER);
//        if (StringUtils.isEmpty(user)) {
//            return ResponseVo.error(NEED_LOGIN);
//        }
        return ResponseVo.success(user);
    }

    /**
     * 前端--->Java
     * cookie(sessionId(Tomcat中叫JssionId))
     *失效情况（
     * 1.sessionId改变、移除
     * 2.项目重启或者服务器重启（java的session存储在内存中）
     * 3.时间过期
     * ）
     * @param session
     * @return
     */
    /**
     *   判断登录状态（拦截器）
     *   有很多请求都需要判断登录状态，简化操作
     *   实现方式：
     *   1.Interceptor---url
     *   2.AOP---包名
     *
     */


    /**
     * {@link TomcatServletWebServerFactory}
     * @param session
     * @return
     */
    @GetMapping("/user/loginout")
    public ResponseVo<User> loginOut(HttpSession session){
        log.info("/loginout sessionId={}",session.getId());
//        User user=(User) session.getAttribute(MallConstant.CURRENTUSER);
//        if(StringUtils.isEmpty(user)){
//            return ResponseVo.error(NEED_LOGIN);
//        }
        session.removeAttribute(MallConstant.CURRENTUSER);
        return ResponseVo.success();
    }

}
