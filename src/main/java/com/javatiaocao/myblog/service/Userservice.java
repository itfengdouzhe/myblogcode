package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.DataMap;

/**
 * @author IT枫斗者
 * @ClassName Userservice.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public interface Userservice {
    //通过手机号码查询用户
    User findUserByPhone(String phone);
    //判断用户名是否存在
    boolean userNameIsExist(String username);
    //注册用户
    DataMap insertUser(User user);

    DataMap getUserPersonalInfo(String username);

    String findUsernameByid(int id);


    int getUserIdByuserName(String userName);

    String findAvatarImgUrlByAnswereId(int answererId);
}
