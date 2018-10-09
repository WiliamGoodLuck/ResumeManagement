package com.code.resumemanagement.controller;

import com.code.resumemanagement.domain.OperationLog;
import com.code.resumemanagement.domain.Personnel;
import com.code.resumemanagement.domain.TodayNumber;
import com.code.resumemanagement.domain.User;
import com.code.resumemanagement.service.IOperationLogService;
import com.code.resumemanagement.service.IPersonnelService;
import com.code.resumemanagement.service.IUserService;
import com.code.resumemanagement.utils.PageInfo;
import com.code.resumemanagement.utils.SysConstant;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/home")
public class PageController {
    private static final Logger LOG = LoggerFactory.getLogger(PageController.class);
    @Autowired
    private IPersonnelService personnelService;
    @Autowired
    private IOperationLogService operationLogService;
    @Autowired
    private IUserService userService;

    @RequiresPermissions(value = {"admin:count"})
    @RequestMapping("/system/index")
    public String index(Model model) {
        TodayNumber todayNumber = new TodayNumber();
        List<OperationLog> operationLogList = operationLogService.selectTodayData();
        int upload = 0;
        int download = 0;
        int delect = 0;
        for (OperationLog item : operationLogList) {
            switch (item.getOperationType()) {
                case 1:
                    upload += 1;
                    break;
                case 2:
                    download += 1;
                    break;
                case 3:
                    delect += 1;
                    break;
                default:
                    break;
            }

        }
        todayNumber.setUpload(upload);
        todayNumber.setDownload(download);
        todayNumber.setDelete(delect);
        model.addAttribute("todayNumber", todayNumber);
        return "index";
    }

    @RequestMapping("/batchAdd")
    public String batchAddPersonnel(HttpServletRequest request) {
        request.getSession().setAttribute("mes", "");
        return "hbUpload";
    }

    @RequestMapping("/home")
    public String home(HttpServletRequest request) {
        return "/sysadmin/home/home";
    }

    // @RequiresPermissions(value = {"manager:file"})
    @RequestMapping("/manager/query")
    public String queryUserInfo(HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(SysConstant.USER_INFO);
        if (user == null) {
            String errMsg = "用户未登录，请登录!";
            request.setAttribute(SysConstant.ERR_INFO, errMsg);
            return "/sysadmin/login/login";
        }
        String uploadUserId = "";
        if ("user".equals(user.getRoleKey())) {
            uploadUserId = user.getId();
        }

        PageInfo pageInfo = personnelService.personnelListByPage("", uploadUserId, 1, SysConstant.PAGE_SIZE);
        request.setAttribute("pageInfo", pageInfo);
        request.getSession().setAttribute("mes", "");
        return "hbPersonnelInfo";
    }

    @RequiresPermissions(value = {"admin:editUser"})
    @RequestMapping("/system/queryUser")
    public String queryUser(HttpServletRequest request) {

        PageInfo pageInfo = userService.selectUserListByPage("", 1, SysConstant.PAGE_SIZE);
        request.setAttribute("pageInfo", pageInfo);
        request.getSession().setAttribute("mes", "");
        return "userInfo";
    }

    @RequestMapping("/manager/toUpdate")
    public String update(HttpServletRequest request, String id) {
        Personnel personnel = personnelService.selectPersonById(id);
        request.setAttribute("personnel", personnel);
        request.getSession().setAttribute("mes", "");
        return "updatePersonnel";
    }

}
