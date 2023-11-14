package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Role;
import com.javatiaocao.myblog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author IT枫斗者
 * @ClassName UserMapper.java
 * @From www.javatiaozao.com
 * @Description TODO
 */
@Mapper
@Repository
public interface UserMapper {

    User findUserByPhone(@Param(value = "phone") String phone);

    User userNameIsExist(String username);

    User phoneIsExist(String phone);

    void insertUser(User user);

    void updatRecentlyLanded(@Param(value = "phone") String phone,@Param(value = "recentlyLanded") String recentlyLanded);

    List<Role> queryRolesByPhone(String phone);

    void updateRoleByUserId(@Param(value = "userId") int userId,@Param(value = "roleId") int roleId);

    User getUserPersonalInfo(String username);

    String findUsernameByid(int id);

    int countUserNum();

    int getUserIdByuserName(@Param(value = "username") String username);

    String findAvatarImgUrlByAnswereId(int answererId);
}
