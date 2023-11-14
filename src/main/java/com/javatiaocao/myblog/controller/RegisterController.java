package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.Userservice;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

/**
 * @author IT枫斗者
 * @ClassName RegisterController.java
 * @From www.javatiaozao.com
 * @Description 注册类控制层
 */
@Slf4j
public class RegisterController {

    @Autowired
    Userservice userservice;

    /**
     * @Author IT枫斗者
     * @From  www.javatiaozao.com
     * @Description 注册接口
     * @Param [user, request]
     * @return java.lang.String
     **/
    @PostMapping(value = "/register")
    public String register(User user, HttpServletRequest request) {
        try {
            //处理控制层的数据
            //判断手机号码是否已经注册
            if (userservice.userNameIsExist(user.getUsername())) {
                return JsonResult.fail(CodeType.USERNAME_EXIST).toJSON();
            }
            //对密码加密
            MD5Util md5Util = new MD5Util();
            user.setPassword(md5Util.encode(user.getPassword()));
            //注册
            DataMap data = userservice.insertUser(user);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("RegisterController register Exception", user, e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping(value = "/getUserPersonalInfo")
    public String getUserPersonalInfo(@AuthenticationPrincipal Principal principal, HttpServletRequest request) {
        try {
//            String username = principal.getName(); //当前遇到问题Principal为null。
            Principal userPrincipal = request.getUserPrincipal();
            if(!Objects.isNull(userPrincipal)){
                String username = userPrincipal.getName();
               DataMap dataMap =  userservice.getUserPersonalInfo(username);
                return JsonResult.build(dataMap).toJSON();
            }
        } catch (Exception e) {
            log.error("RegisterController register Exception", e);
        }
        //失败了
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


}
