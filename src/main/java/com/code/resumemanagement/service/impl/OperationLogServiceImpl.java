package com.code.resumemanagement.service.impl;


import com.code.resumemanagement.dao.OperationLogDao;
import com.code.resumemanagement.domain.OperationLog;
import com.code.resumemanagement.service.IOperationLogService;
import com.code.resumemanagement.utils.DateUtil;
import com.code.resumemanagement.utils.PageInfo;
import com.code.resumemanagement.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OperationLogServiceImpl implements IOperationLogService {
    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public Integer batchInsertOperationLog(List<OperationLog> operationLog) {
        return operationLogDao.batchInsertOperationLog(operationLog);
    }

    @Override
    public Integer insertOperationLog(OperationLog operationLog) {
        return operationLogDao.insertOperationLog(operationLog);
    }

    @Override
    public List<OperationLog> selectTodayData() {
        String data = DateUtil.getCurrentDateyyyyMMdd();
        return operationLogDao.selectTodayData(data);
    }

    @Override
    public PageInfo selectOperationListByPage(String personnelName, int operationType, int thisPage, int pageSize) {
        PageInfo pageInfo = new PageInfo();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("start", (thisPage - 1) * pageSize);
        params.put("pageSize", pageSize);
        if (!StringUtil.isEmpty(personnelName)) {
            params.put("personnelName", personnelName);
            pageInfo.setPersonnelName(personnelName);
        } else {
            pageInfo.setPersonnelName("");
        }
        if (4 != operationType) {
            params.put("operationType", operationType);
            pageInfo.setOperationType(operationType);
        }

        //总记录数
        int countRow = operationLogDao.selectCountOperationByParam(params);
        pageInfo.setCountRow(countRow);
        //总页数
        int countPage = countRow / pageSize + (countRow % pageSize == 0 ? 0 : 1);
        pageInfo.setCountPage(countPage);
        //首页
        int firstpage = 1;
        pageInfo.setFirstPage(firstpage);
        //上一页
        int prePage = thisPage == 1 ? 1 : thisPage - 1;
        pageInfo.setPrePage(prePage);
        //当前页
        pageInfo.setThisPage(thisPage);
        //每页记录数
        pageInfo.setPageSize(pageSize);
        //下一页
        int nextPage = thisPage == countPage ? countPage : thisPage + 1;
        pageInfo.setNextPage(nextPage);
        //尾页
        int lastPage = countPage;
        pageInfo.setLastPage(lastPage);
        //当前页的数据
        List<OperationLog> list = operationLogDao.selectoperationLogByParam(params);
        pageInfo.setOperationLogList(list);

        return pageInfo;

    }
}
