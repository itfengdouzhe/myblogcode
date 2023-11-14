package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.Userservice;
import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author IT枫斗者
 * @ClassName UserserviceImpl.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Service
public class UserserviceImpl implements Userservice {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByPhone(String phone) {

        return userMapper.findUserByPhone(phone);
    }

    @Override
    public boolean userNameIsExist(String username) {
        User user = userMapper.userNameIsExist(username);
        return user != null;
    }

    @Override
    @Transactional
    public DataMap insertUser(User user) {
        //判断用户名是否异常：1：用户名长度 2.特殊字符
        if (user.getUsername().length() > 35) {
            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
        }
        //判断手机号码是否存在
        User userIsExist = userMapper.phoneIsExist(user.getPhone());

        if (userIsExist != null) {
            return DataMap.fail(CodeType.PHONE_EXIST);
        }
        //设置默认头像
        if ("male".equals(user.getGender())) {
            user.setAvatarImgUrl("www.javatiaocao.com");
        } else {
            user.setAvatarImgUrl("www.javatiaocao.com");
        }
        //设置trueNname
        user.setTrueName("www.javatiaocao.com");
        userMapper.insertUser(user);
        //当注册成功的时候，配置角色,默认是普通用户
        User userByPhone = userMapper.findUserByPhone(user.getPhone());
        updateRoleByUserId(userByPhone.getId(), 1);
        return DataMap.success();
    }

    @Override
    public DataMap getUserPersonalInfo(String username) {
        User user = userMapper.getUserPersonalInfo(username);
        return DataMap.success().setData(user);
    }

    @Override
    public String findUsernameByid(int id) {
        return userMapper.findUsernameByid(id);
    }

    @Override
    public int getUserIdByuserName(String userName) {
        int userId = userMapper.getUserIdByuserName(userName);
        return userId;
    }

    @Override
    public String findAvatarImgUrlByAnswereId(int answererId) {
        return userMapper.findAvatarImgUrlByAnswereId(answererId);
    }

    private void updateRoleByUserId(int userId, int roleId) {
        userMapper.updateRoleByUserId(userId, roleId);
    }
}
