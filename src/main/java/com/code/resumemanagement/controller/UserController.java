package com.code.resumemanagement.controller;

import com.code.resumemanagement.domain.User;
import com.code.resumemanagement.domain.UserSys;
import com.code.resumemanagement.service.IUserService;
import com.code.resumemanagement.utils.EncryptUtil;
import com.code.resumemanagement.utils.PageInfo;
import com.code.resumemanagement.utils.StringUtil;
import com.code.resumemanagement.utils.SysConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("/toUpdate")
    public String toUpdate(HttpServletRequest request) {

        request.getSession().setAttribute("mes", "");
        return "/sysadmin/admin/updateUser";
    }

    @RequestMapping("/toUpdateSys")
    public String toUpdateSys(HttpServletRequest request) {

        request.getSession().setAttribute("mes", "");
        return "/sysadmin/admin/updateUserSys";
    }

    @RequestMapping("/update")
    public String update(HttpSession session, User user) {

        Integer result = userService.updateUserInfo(user);
        if (result == 1) {
            User old = (User) session.getAttribute(SysConstant.USER_INFO);
            user.setRoleName(old.getRoleName());
            session.setAttribute(SysConstant.USER_INFO, user);

        }
        return "redirect:/home/home";
    }


    @RequestMapping("/updateSys")
    public String updateSys(HttpSession session, UserSys user) {
        User userInfo = new User();
        userInfo.setId(user.getId());
        String passWord = user.getPassword();
        passWord = EncryptUtil.md5hash(passWord, user.getUserName());
        userInfo.setPassWord(passWord);
        userService.updateUserPassWord(userInfo);

       /* Integer result=    userService.updateUserSys(user);
        if(result==1){
            User old= (User)session.getAttribute(SysConstant.USER_INFO);
            user.setRoleName(old.getRoleName());
            session.setAttribute(SysConstant.USER_INFO, user);

        }*/
        return "redirect:/home/home";
    }

    @RequiresPermissions(value = {"admin:editUser"})
    @RequestMapping("/system/page")
    public String page(HttpServletRequest request,
                       HttpServletResponse response) {
        //1.获取要查询的页码和每页记录数
        String thisPageStr = request.getParameter("thisPage");
        int thisPage;
        int pageSize;
        if (StringUtil.isEmpty(thisPageStr)) {
            thisPage = 1;
        } else {
            thisPage = Integer.parseInt(thisPageStr);
        }
        String pageSizeStr = request.getParameter("pageSize");

        if (StringUtil.isEmpty(pageSizeStr)) {
            pageSize = SysConstant.PAGE_SIZE;
        } else {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        String nickName = request.getParameter("nickName");
          if (!StringUtil.isEmpty(nickName)) {
            try {
                nickName = new String(nickName.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //2.调用Service查询该页的数据

        PageInfo pageInfo = userService.selectUserListByPage(nickName, thisPage, pageSize);


        //3.将数据存入request域带到页面展示
        // request.setAttribute("personnelList", list);
        request.setAttribute("pageInfo", pageInfo);
        request.getSession().setAttribute("mes", "");
        return "userInfo";
    }

    @RequestMapping("/system/delete")
    public String delete(HttpSession session, HttpServletRequest request, String id,
                         HttpServletResponse response) {
        userService.deleteUserById(id);
        userService.deleteUserRoleByUserId(id);
        return "redirect:/user/system/page";
    }


    @RequestMapping("/system/toUpdateRole")
    public String update(HttpServletRequest request, String id) {
        User user = userService.selectUserById(id);
        request.setAttribute("SELECT_USER", user);
        request.getSession().setAttribute("mes", "");
    /*    Personnel personnel = personnelService.selectPersonById(id);
        request.setAttribute("personnel", personnel);
        request.getSession().setAttribute("mes", "");*/
        return "updateUserRole";
    }

    @RequestMapping("/system/updateRole")
    public String update(HttpServletRequest request, User user) {
      /*  try {
            personnel.setUserName(new String(personnel.getUserName().getBytes("ISO-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        if ("系统管理员".equals(user.getRoleName())) {
            user.setRoleId("1");
        } else if ("用户".equals(user.getRoleName())) {
            user.setRoleId("10");
        } else if ("经理".equals(user.getRoleName())) {
            user.setRoleId("2");
        } else {
            LOG.error("更改角色失败" + user.getRoleName());
            return "redirect:/user/system/page";
        }
        Integer result = userService.updateUserRole(user);


        return "redirect:/user/system/page";
    }
}
