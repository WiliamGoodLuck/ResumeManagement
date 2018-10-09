package com.code.resumemanagement.service.impl;

import com.code.resumemanagement.dao.UserDao;
import com.code.resumemanagement.domain.Personnel;
import com.code.resumemanagement.domain.User;
import com.code.resumemanagement.domain.UserRole;
import com.code.resumemanagement.service.IUserService;
import com.code.resumemanagement.utils.PageInfo;
import com.code.resumemanagement.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/11/26.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Set<String> queryRolesByName(String userName) {


        return userDao.queryRolesByName(userName);
    }

    @Override
    public User queryUserByName(String userName) {
        return userDao.queryUserByName(userName);
    }

    @Override
    public Integer updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }

    @Override
    public Integer updateUserSys(User user) {
        return userDao.updateUserSys(user);
    }

    @Override
    public Set<String> selectPermissionsByUserName(String userName) {

        return userDao.selectPermissionsByUserName(userName);
    }

    @Override
    public PageInfo selectUserListByPage(String nickName, int thisPage, int pageSize) {
        PageInfo pageInfo = new PageInfo();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("start", (thisPage - 1) * pageSize);
        params.put("pageSize", pageSize);
        if (!StringUtil.isEmpty(nickName)) {
            params.put("nickName", nickName);
            pageInfo.setNickName(nickName);
        } else {
            pageInfo.setNickName("");
        }


        //总记录数
        int countRow = userDao.selectCountUserByParam(params);
        pageInfo.setCountRow(countRow);
        //总页数
        int countPage = countRow / pageSize + (countRow % pageSize == 0 ? 0 : 1);
        pageInfo.setCountPage(countPage);
        //首页
        int firstpage = 1;
        pageInfo.setFirstPage(firstpage);
        //上一页
        int prePage = thisPage == 1 ? 1 : thisPage - 1;
        pageInfo.setPrePage(prePage);
        //当前页
        pageInfo.setThisPage(thisPage);
        //每页记录数
        pageInfo.setPageSize(pageSize);
        //下一页
        int nextPage = thisPage == countPage ? countPage : thisPage + 1;
        pageInfo.setNextPage(nextPage);
        //尾页
        int lastPage = countPage;
        pageInfo.setLastPage(lastPage);
        //当前页的数据
        List<User> list = userDao.selectUserByParam(params);
        pageInfo.setUserList(list);

        return pageInfo;
    }

    @Override
    public Integer deleteUserRoleByUserId(String userId) {
        return userDao.deleteUserRoleByUserId(userId);
    }

    @Override
    public Integer deleteUserById(String id) {
        return userDao.deleteUserById(id);
    }

    @Override
    public User selectUserById(String id) {
        return userDao.selectUserById(id);
    }

    @Override
    public Integer updateUserRole(User user) {
        return userDao.updateUserRole(user);
    }

    @Override
    public Integer updateUserPassWord(User userInfo) {
        return userDao.updateUserPassWord(userInfo);
    }

    @Override
    public Integer insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public Integer insertUserRole(UserRole userRole) {
        return userDao.insertUserRole(userRole);
    }

    @Override
    public User selectUserByUserName(String userName) {
        return userDao.selectUserByUserName(userName);
    }

}
