package com.code.resumemanagement.controller;

import com.code.resumemanagement.domain.*;
import com.code.resumemanagement.service.IOperationLogService;
import com.code.resumemanagement.service.IPersonnelService;
import com.code.resumemanagement.service.IUserService;
import com.code.resumemanagement.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("login")
    public String login(String username, String password, HttpSession session, Model model) {
        if (StringUtil.isEmpty(username)) {
            return "/sysadmin/login/login";        //如果第一次访问直接转向登录页面
        }

        //通过shiro来验证用户是否是系统的账户，如果是就可以得到User对象，如果不是转向到登录页面展现错误信息
        Subject subject = SecurityUtils.getSubject();
        password = EncryptUtil.md5hash(password, username);
        //用token封装了登录名和密码
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);    //把登录名和密码传入到shiro，自动调用自定义realm.doGetAuthenticationInfo
            User curUser = (User) subject.getPrincipal();    //获取到当前用户信息
            session.setAttribute(SysConstant.USER_INFO, curUser);
            //  return "redirect:/home/home";
            return "/sysadmin/home/home";
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            String errMsg = "用户名或者密码错误，请重新输入!";
            model.addAttribute(SysConstant.ERR_INFO, errMsg);
            // System.out.println("error");
        }
        return "/sysadmin/login/login";
    }

    //退出
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(SysConstant.USER_INFO);

        return "/sysadmin/login/login";
    }

    @RequestMapping("loginToRegist")
    public String loginToRegist() {

        return "/sysadmin/login/registUser";
    }


    @RequestMapping(value = "/loginRegist", method = {RequestMethod.POST})
    public String loginRegist(HttpServletRequest request, Model model) {
        String password = request.getParameter("password");

        String userName = request.getParameter("username");
        String nickName = request.getParameter("nickName");
        System.out.println(password + ":" + userName + ":" + nickName);
        User checkUser = null;
        if (StringUtil.isNotEmpty(userName)) {
            userName = userName.trim().replaceAll("。", ".");
            checkUser = userService.selectUserByUserName(userName);
            if (checkUser != null) {
                String errMsg = "注册邮箱已存在！";
                model.addAttribute(SysConstant.ERR_INFO, errMsg);
                return "/sysadmin/login/registUser";
            }

        } else {
            String errMsg = "登录邮箱为空！";
            model.addAttribute(SysConstant.ERR_INFO, errMsg);
            return "/sysadmin/login/registUser";
        }
        password = EncryptUtil.md5hash(password, userName);
        User user = new User();
        user.setId(StringUtil.getUUID());
        user.setPassWord(password);
        user.setNickName(nickName);
        user.setUserName(userName);
        user.setCreateDate(DateUtil.getCurrentDateyyyyMMddhhmmss());
        userService.insertUser(user);
        UserRole userRole = new UserRole();
        userRole.setId(StringUtil.getUUID());
        userRole.setRoleId(SysConstant.REGIST_USER_LEVEL);
        userRole.setUserId(user.getId());
        userService.insertUserRole(userRole);
        String errMsg = "注册成功，请登录！";
        model.addAttribute(SysConstant.ERR_INFO, errMsg);
        return "/sysadmin/login/login";
    }

}
