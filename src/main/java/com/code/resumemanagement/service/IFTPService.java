package com.code.resumemanagement.service;

import org.apache.commons.net.ftp.FTPClient;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface IFTPService {
    /**
     * 获得ftp客户端.
     * 使用配置文件参数。
     *
     * @return ftp客户端
     */
    FTPClient getFTPClient();

    /**
     * 获得ftp客户端.
     *
     * @param ftpHost     地址
     * @param ftpUsername 用户名
     * @param ftpPassword 密码
     * @param ftpPort     端口
     * @return ftp客户端
     */
    FTPClient getFTPClient(String ftpHost, String ftpUsername, String ftpPassword, int ftpPort);

    /**
     * 上传文件.
     *
     * @param filePath  文件存储目录
     * @param fileName  文件名称
     * @param localPath 本地路径及名称
     * @return 是否上传成功
     */
    String uploadFile(String filePath, String fileName, String localPath);

    /**
     * 上传文件.
     *
     * @param basePath  ftp基础目录
     * @param filePath  文件存储目录
     * @param fileName  文件名称
     * @param localPath 本地路径
     * @return
     */
    String uploadFile(String basePath, String filePath, String fileName, String localPath);

    /**
     * 根据人员主键获取路径，下载对应的简历文件.
     *
     * @param id 人员的主键
     * @return 是否下载成功
     */
    String downloadFileById(HttpSession session,
                            HttpServletResponse response, String id);

    /**
     * 根据ftp路径及名称下载简历文件.
     *
     * @param ftpPath ftp路径及名称
     * @return 是否下载成功
     */
    String httpDownloadFile(HttpSession session,
                            HttpServletResponse response, String ftpPath);

    boolean removeFile(String srcName);
}
