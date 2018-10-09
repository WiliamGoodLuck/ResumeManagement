package com.code.resumemanagement.controller;

import com.code.resumemanagement.service.IOperationLogService;
import com.code.resumemanagement.utils.PageInfo;
import com.code.resumemanagement.utils.StringUtil;
import com.code.resumemanagement.utils.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/operation")
public class OperationLogController {
    @Autowired
    private IOperationLogService operationLogService;

    /*  @RequestMapping("/system/toQueryOperation")
      public String batchAddPersonnel(HttpServletRequest request ) {
          request.getSession().setAttribute("mes", "");

          return "redirect:/operation/system/page";
      }*/
    @RequestMapping("/system/page")
    public String page(HttpServletRequest request,
                       HttpServletResponse response) throws UnsupportedEncodingException {
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

        String personnelName = request.getParameter("personnelName");
        String temp = request.getParameter("operationType");
        int operationType = 4;
        if (StringUtil.isNotEmpty(temp)) {

            operationType = Integer.parseInt(temp);
        }
          if (!StringUtil.isEmpty(personnelName)) {
            try {
                personnelName = new String(personnelName.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //2.调用Service查询该页的数据

        PageInfo pageInfo = operationLogService.selectOperationListByPage(personnelName, operationType, thisPage, pageSize);


        //3.将数据存入request域带到页面展示
        // request.setAttribute("personnelList", list);
        request.setAttribute("pageInfo", pageInfo);
        request.getSession().setAttribute("mes", "");
        return "/sysadmin/operationLog/operationInfo";
    }
}
