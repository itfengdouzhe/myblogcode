package com.javatiaocao.myblog.service.impl;

import com.javatiaocao.myblog.constant.CodeType;
import com.javatiaocao.myblog.mapper.UserMapper;
import com.javatiaocao.myblog.model.Role;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author IT枫斗者
 * @ClassName UserDetailServiceImpl.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        //查询我们的用户信息
        User user = userMapper.phoneIsExist(phone);
        //判断用户是否为空
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException(CodeType.USERNAME_NOT_EXIST.name());
        }
        //当用户不为空的时候，查询用户对应角色（ROLE_USER/ROLE_ADMIN/ROLE_SUPERADMIN）
        List<Role> roles = userMapper.queryRolesByPhone(phone);
        user.setRoles(roles);
        //处理记录用户登录系统的时间
        TimeUtil timeUtil = new TimeUtil();
        String formatDateForSix = timeUtil.getFormatDateForSix();
        //更新用户登录时间
        userMapper.updatRecentlyLanded(phone,formatDateForSix);
        //添加权限
        ArrayList<SimpleGrantedAuthority> auths = new ArrayList<>();
        for (Role role : user.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),auths);
    }
}
