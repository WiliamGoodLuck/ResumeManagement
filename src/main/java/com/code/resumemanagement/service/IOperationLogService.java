package com.code.resumemanagement.service;

import com.code.resumemanagement.domain.OperationLog;
import com.code.resumemanagement.utils.PageInfo;

import java.util.List;

public interface IOperationLogService {
    /**
     * 插入一条操作记录.
     *
     * @param operationLog 操作记录
     * @return 执行结果
     */
    Integer insertOperationLog(OperationLog operationLog);

    Integer batchInsertOperationLog(List<OperationLog> operationLog);

    List<OperationLog> selectTodayData();

    PageInfo selectOperationListByPage(String nickName, int operationType, int thisPage, int pageSize);
}
