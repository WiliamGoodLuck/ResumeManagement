package com.code.resumemanagement.service;

import com.code.resumemanagement.domain.User;
import com.code.resumemanagement.domain.UserRole;
import com.code.resumemanagement.utils.PageInfo;

import java.util.Set;

/**
 * Created by Administrator on 2017/11/26.
 */
public interface IUserService {
    Set<String> queryRolesByName(String userName);

    User queryUserByName(String userName);

    Integer updateUserInfo(User user);

    Integer updateUserSys(User user);

    Set<String> selectPermissionsByUserName(String userName);

    PageInfo selectUserListByPage(String nickName, int thisPage, int pageSize);

    Integer deleteUserRoleByUserId(String UserId);

    Integer deleteUserById(String id);

    User selectUserById(String id);

    Integer updateUserRole(User user);

    Integer updateUserPassWord(User userInfo);

    Integer insertUser(User user);

    Integer insertUserRole(UserRole userRole);

    User selectUserByUserName(String userName);


}
