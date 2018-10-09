package com.code.resumemanagement.dao;


import com.code.resumemanagement.domain.User;
import com.code.resumemanagement.domain.UserRole;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public interface UserDao {
    Set<String> queryRolesByName(String userName);

    User queryUserByName(String userName);

    Integer updateUserSys(User user);

    Integer updateUserInfo(User user);

    Integer deleteUserRoleByUserId(String UserId);

    Integer deleteUserById(String id);

    Set<String> selectPermissionsByUserName(String userName);

    int selectCountUserByParam(HashMap<String, Object> params);

    List<User> selectUserByParam(HashMap<String, Object> params);

    User selectUserById(String id);

    Integer updateUserRole(User user);

    Integer updateUserPassWord(User userInfo);

    Integer insertUser(User user);

    Integer insertUserRole(UserRole userRole);

    User selectUserByUserName(String userName);
}
