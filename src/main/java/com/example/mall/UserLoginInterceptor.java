package com.example.mall;

import com.example.mall.Exception.UserLoginException;
import com.example.mall.MallConstant.MallConstant;
import com.example.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * true:表示继续流程 false:表示中断
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        User user = (User)request.getSession().getAttribute(MallConstant.CURRENTUSER);
        if (StringUtils.isEmpty(user)) {
            log.info("user==null");
            //response.getWriter().print("error");
            throw new UserLoginException();
//            return false;
        }
        return true;
    }
}
