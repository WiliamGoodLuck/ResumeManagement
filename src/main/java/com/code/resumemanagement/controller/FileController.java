package com.code.resumemanagement.controller;


import com.code.resumemanagement.domain.OperationLog;
import com.code.resumemanagement.domain.Personnel;
import com.code.resumemanagement.domain.User;
import com.code.resumemanagement.service.IOperationLogService;
import com.code.resumemanagement.service.IPersonnelService;
import com.code.resumemanagement.utils.FileUtil;
import com.code.resumemanagement.utils.ParseFileUtil;
import com.code.resumemanagement.utils.SysConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.jms.Session;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by wiliam on 2017/11/10.
 */
@Controller
@RequestMapping("/file")
public class FileController {
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private IPersonnelService personnelService;
    @Autowired
    private IOperationLogService operationLogService;

    @RequestMapping("/add")
    public String addPersonnel(Model model, Personnel personnel) {
        return "hbforms";
    }

    @RequestMapping("/batchUpload")
    public String batchFileUpload(MultipartHttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        User user = (User) session.getAttribute(SysConstant.USER_INFO);
        if (user == null) {
            String errMsg = "用户未登录，请登录!";
            session.setAttribute(SysConstant.ERR_INFO, errMsg);
            return "/sysadmin/login/login";
        }
        String message = "";
        int sum = 0;
        int success = 0;
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            LOG.debug("fileName:" + fileName);

            List<MultipartFile> fileList = request.getFiles(fileName);
            if (fileList.size() > 0) {
                //遍历文件列表
                Iterator<MultipartFile> fileIte = fileList.iterator();
                while (fileIte.hasNext()) {
                    sum += 1;
                    //获得每一个文件
                    MultipartFile multipartFile = fileIte.next();
                    //获得原文件名
                    String originalFilename = multipartFile.getOriginalFilename();
                    if (originalFilename.indexOf("-") == -1) {
                        continue;
                    }
                    LOG.debug("原文件名:" + originalFilename);
                    //设置保存路径.
                    String path = "../fileUpload/";
                    //检查该路径对应的目录是否存在. 如果不存在则创建目录
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String filePath = path + System.currentTimeMillis() + originalFilename;
                    LOG.debug("下载到服务器文件:" + filePath);
                    //保存文件
                    File newFile = new File(filePath);
                    if (!newFile.exists()) {
                        multipartFile.transferTo(newFile);
                        String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
                        ;

                        String content = "";
                        if ("docx".equals(fileType)) {
                            content = FileUtil.readdocx(newFile);
                        } else if ("doc".equals(fileType)) {
                            content = FileUtil.readdoc(newFile);
                        } else if ("pdf".equals(fileType)) {
                            content = FileUtil.readpdf(newFile);
                        } else {
                            LOG.error("不支持解析:" + fileType);
                        }
                        Personnel personnel = ParseFileUtil.parsePersonnel(content, originalFilename);
                        LOG.debug("文件解析到的personnel:" + personnel.toString());
                        if (personnel.getStatus() == null) {
                            personnel.setStatus("1");
                        }
                        personnel.setUploadUserId(user.getId());
                        personnelService.insertPersonnel(personnel, filePath);
                        OperationLog operationLog = new OperationLog();
                        operationLog.setOperationType(SysConstant.INSERT);
                        operationLog.setPersonnelName(personnel.getUserName());
                        operationLog.setWorkType(personnel.getWorkType());
                        operationLog.setCreateName(user.getNickName());
                        operationLog.setUserName(user.getUserName());
                        operationLog.setPersonnelId(personnel.getId());
                        operationLogService.insertOperationLog(operationLog);

                        success += 1;
                        if (newFile.exists()) {
                            newFile.delete();
                        }
                        /*
                         * 如果需对文件进行其他操作, MultipartFile也提供了
                         * InputStream getInputStream()方法获取文件的输入流
                         * 方法, 获取输入流后将其保存至指定路径
                         */
                        //FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), dest);
                    }//获取文件contentType
                    String contentType = multipartFile.getContentType();
                    LOG.debug("contentType:" + contentType);
                    /*
                     * 前台页面<input>中name=""属性
                     */
                    /*String name=multipartFile.getName();*/
                    //获取文件大小, 单位为字节
                  /*  long size=multipartFile.getSize();*/

                }
            } else {
                message = "文件上传失败！";
                request.getSession().setAttribute("mes", message);
                return "hbUpload";
            }
        }
        if (success == 0) {
            message = "文件上传失败！";
        } else if (sum == success) {
            message = "文件上传成功！";
        } else {
            message = "一共上传" + sum + "个文件，成功" + success + "个文件";
        }
        request.getSession().setAttribute("mes", message);
        return "hbUpload";
    }

}
