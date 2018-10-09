package com.code.resumemanagement.utils;

import com.code.resumemanagement.domain.OperationLog;
import com.code.resumemanagement.domain.Personnel;
import com.code.resumemanagement.domain.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/10.
 */
public class PageInfo implements Serializable {
    private List<Personnel> list;
    private List<User> userList;
    private List<OperationLog> operationLogList;
    private int countRow; //总条数
    private int countPage; //总页数
    private int firstPage;//首页
    private int prePage;//上一页
    private int thisPage;//
    private int pageSize ;//
    private int nextPage;//下一页
    private int lastPage;//尾页
    private String userName;//用户名
    private String nickName;//用户名
    private String uploadUserId;//上传用户id;
    private String personnelName;//被推荐人姓名
    private int operationType;//操作类型
    public List<Personnel> getList() {
        return list;
    }

    public void setList(List<Personnel> list) {
        this.list = list;
    }

    public int getCountRow() {
        return countRow;
    }

    public void setCountRow(int countRow) {
        this.countRow = countRow;
    }

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getThisPage() {
        return thisPage;
    }

    public void setThisPage(int thisPage) {
        this.thisPage = thisPage;
    }


    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<OperationLog> getOperationLogList() {
        return operationLogList;
    }

    public void setOperationLogList(List<OperationLog> operationLogList) {
        this.operationLogList = operationLogList;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public String getUploadUserId() {
        return uploadUserId;
    }

    public void setUploadUserId(String uploadUserId) {
        this.uploadUserId = uploadUserId;
    }
}
