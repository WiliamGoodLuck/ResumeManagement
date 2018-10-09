package com.code.resumemanagement.domain;

import com.code.resumemanagement.utils.DateUtil;
import com.code.resumemanagement.utils.StringUtil;

public class OperationLog {
    private String id;//主键
    private int operationType;//1上传 2下载 3删除
    private String userName;//操作人邮箱
    private String personnelName; //被推荐人姓名
    private String personnelId; //被推荐人id
    private String workType;//工作分类
    private String createName;//操作人
    private String createDate;//操作日期

    public OperationLog() {
        this.id = System.currentTimeMillis() + "";
        this.createDate = DateUtil.getCurrentDateyyyyMMddhhmmss();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(String personnelId) {
        this.personnelId = personnelId;
    }
}
