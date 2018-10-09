package com.code.resumemanagement.service.impl;

import com.code.resumemanagement.dao.PersonnelDao;
import com.code.resumemanagement.domain.Personnel;
import com.code.resumemanagement.service.IFTPService;
import com.code.resumemanagement.utils.PropertyUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.SocketException;

@Service
public class FTPServiceImpl implements IFTPService {
    private static final Logger LOG = LoggerFactory.getLogger(FTPServiceImpl.class);
    private static String ftpHost;
    private static String ftpUsername;
    private static String ftpPassword;
    private static int ftpPort;
    //获取字符编码
    private static String encoding = "UTF-8";
    @Autowired
    private PersonnelDao personDao;

    /**
     * 获取FTPClient对象
     *
     * @return FTPClient对象
     */
    @Override
    public FTPClient getFTPClient() {
        ftpHost = PropertyUtil.getProperty("host");
        ftpUsername = PropertyUtil.getProperty("username");
        ftpPassword = PropertyUtil.getProperty("password");
        ftpPort = Integer.parseInt(PropertyUtil.getProperty("port"));
        return getFTPClient(ftpHost, ftpUsername, ftpPassword, ftpPort);
    }

    @Override
    public FTPClient getFTPClient(String ftpHost, String ftpUsername, String ftpPassword, int ftpPort) {
        FTPClient ftpClient = new FTPClient();
        try {
            //获取连接
            ftpClient.connect(ftpHost, ftpPort);
            //登录
            if (ftpPassword == null) {
                ftpPassword = "";
            }
            ftpClient.login(ftpUsername, ftpPassword);
            ftpClient.setBufferSize(1024);
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding(encoding);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;

    }

    @Override
    public String uploadFile(String filePath, String fileName, String localPath) {
        String basePath = PropertyUtil.getProperty("basepath").trim();
        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }
        return uploadFile(basePath, filePath, fileName, localPath);
    }

    @Override
    public String uploadFile(String basePath, String filePath, String fileName, String localPath) {

        FTPClient ftpClient = getFTPClient();
        String ftpFile = basePath + filePath + "/" + fileName;
        File localFile = new File(localPath);
        try {
            FileInputStream fis = new FileInputStream(localFile);//输入流
            int reply = ftpClient.getReplyCode();//响应编码
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return "";
            }
            //切换到上传目录
            if (!ftpClient.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    basePath += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(basePath)) {
                        if (!ftpClient.makeDirectory(basePath)) {
                            return "";
                        } else {
                            ftpClient.changeWorkingDirectory(basePath);
                        }
                    }
                }
            }
            //上传
            boolean b = ftpClient.storeFile(new String(ftpFile.getBytes(encoding), "ISO-8859-1"), fis);
            //System.out.println("***********");
            fis.close();
            ftpClient.logout();
            if (b) {
                return ftpFile;
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return "";
    }

    @Override
    public String downloadFileById(HttpSession session,
                                   HttpServletResponse response, String id) {
        Personnel person = personDao.selectPersonById(id);
        return httpDownloadFile(session, response, person.getFileUrl());
    }

    @Override
    public String httpDownloadFile(HttpSession session,
                                   HttpServletResponse response, String ftpPath) {
        FTPClient ftpClient = getFTPClient();
        int reply = ftpClient.getReplyCode();//响应编码
        FileOutputStream fos = null;
        boolean downloadResult = false;
        String filePath = "";
        try {
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return filePath;
            }
            String prefix = ftpPath.substring(ftpPath.lastIndexOf("."));
            filePath = System.currentTimeMillis() + prefix.toLowerCase();
            fos = new FileOutputStream(filePath);
            ftpClient.setBufferSize(1024);
            downloadResult = ftpClient.retrieveFile(new String(ftpPath.getBytes(encoding), "ISO-8859-1"), fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + filePath);
        FileInputStream input = null;
        File tempFile = new File(filePath);
        try {
            input = new FileInputStream(tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStream output = null;
        try {
            output = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] b = new byte[1024];
        int i = 0;
        try {
            while ((i = input.read(b)) > 0) {
                output.write(b, 0, i);
            }
            input.close();
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempFile.exists()) {
            tempFile.delete();
        }
        return filePath;
    }

    @Override
    public boolean removeFile(String srcName) {
        FTPClient ftpClient = getFTPClient();
        boolean flag = false;
        if (ftpClient != null) {
            try {
                String i = ftpClient.getStatus(srcName);
                System.out.println(i);
                flag = ftpClient.deleteFile(srcName);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("关闭FTP连接发生异常！", e);
                }
            }
        }
        return flag;
    }
}


