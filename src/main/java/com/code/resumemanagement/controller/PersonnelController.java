package com.code.resumemanagement.controller;


import com.code.resumemanagement.domain.OperationLog;
import com.code.resumemanagement.domain.Personnel;
import com.code.resumemanagement.domain.User;
import com.code.resumemanagement.service.IFTPService;
import com.code.resumemanagement.service.IOperationLogService;
import com.code.resumemanagement.service.IPersonnelService;
import com.code.resumemanagement.utils.PageInfo;
import com.code.resumemanagement.utils.StringUtil;
import com.code.resumemanagement.utils.SysConstant;
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

/**
 * Created by wiliam on 2017/11/10.
 */
@Controller
@RequestMapping("/personnel")
public class PersonnelController {
    private static final Logger LOG = LoggerFactory.getLogger(PersonnelController.class);
    //2017110

    @Autowired
    private IPersonnelService personnelService;
    @Autowired
    private IFTPService ftpService;
    private PageInfo pageTableForm;
    @Autowired
    private IOperationLogService operationLogService;

    @RequestMapping("/manager/select")
    public String selectPersonnelList(Model model, Personnel personnel) {

        pageTableForm = personnelService.selectPersonByParam(personnel);
        model.addAttribute("pageTableForm", pageTableForm);
        return "hbPersonnelInfo";
    }

    @RequestMapping("/manager/deleteFile")
    public String deleteFile(HttpSession session, HttpServletRequest request, String id) {
        Personnel personnel = personnelService.selectPersonById(id);

        User user = (User) session.getAttribute(SysConstant.USER_INFO);
        if (personnel == null) {
            String message = "操作失败！";
            request.getSession().setAttribute("mes", message);
            return "hbPersonnelInfo";
        }
        personnelService.deletePersonnelById(id);
        OperationLog operationLog = new OperationLog();
        operationLog.setCreateName(user.getNickName());
        operationLog.setUserName(user.getUserName());
        operationLog.setPersonnelId(personnel.getId());
        operationLog.setPersonnelName(personnel.getUserName());
        operationLog.setWorkType(personnel.getWorkType());
        operationLog.setOperationType(SysConstant.DELETE);
        operationLogService.insertOperationLog(operationLog);
        ftpService.removeFile(personnel.getFileUrl());
        String message = "操作成功！";
        request.getSession().setAttribute("mes", message);
        return "redirect:/personnel/manager/page";
    }

    @RequestMapping("/manager/downloadFile")
    public String downloadFile(HttpSession session, HttpServletRequest request, String id,
                               HttpServletResponse response) {
        Personnel personnel = personnelService.selectPersonById(id);
        String filePath = ftpService.httpDownloadFile(session, response, personnel.getFileUrl());
        User user = (User) session.getAttribute(SysConstant.USER_INFO);
        if (StringUtil.isEmpty(filePath)) {
            filePath = "文件下载失败！";
            request.getSession().setAttribute("mes", filePath);
        } else {
            filePath = "文件下载成功！";
            request.getSession().setAttribute("mes", filePath);
            OperationLog operationLog = new OperationLog();
            operationLog.setCreateName(user.getNickName());
            operationLog.setUserName(user.getUserName());
            operationLog.setPersonnelId(personnel.getId());
            operationLog.setWorkType(personnel.getWorkType());
            operationLog.setPersonnelName(personnel.getUserName());
            operationLog.setOperationType(SysConstant.DOWNLOAD);
            operationLogService.insertOperationLog(operationLog);
        }
        return "redirect:/personnel/manager/page";
    }

    @RequestMapping("/manager/page")
    public String page(HttpServletRequest request,
                       HttpServletResponse response) throws UnsupportedEncodingException {

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

        String userName = request.getParameter("userName");

      if (!StringUtil.isEmpty(userName)) {
            try {
                userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //2.调用Service查询该页的数据

        PageInfo pageInfo = personnelService.personnelListByPage(userName, uploadUserId, thisPage, pageSize);

        //3.将数据存入request域带到页面展示
        // request.setAttribute("personnelList", list);
        request.setAttribute("pageInfo", pageInfo);
        request.getSession().setAttribute("mes", "");
        return "hbPersonnelInfo";
    }

    @RequestMapping("/manager/update")
    public String update(HttpServletRequest request, Personnel personnel) {
      /*  try {
            personnel.setUserName(new String(personnel.getUserName().getBytes("ISO-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        Integer result = personnelService.updatePersonnel(personnel);

        return "redirect:/home/manager/query";
    }
}
