package com.code.resumemanagement.dao;

import com.code.resumemanagement.domain.OperationLog;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface OperationLogDao {
    Integer insertOperationLog(OperationLog operationLog);

    Integer batchInsertOperationLog(List<OperationLog> operationLogList);

    List<OperationLog> selectTodayData(String date);

    int selectCountOperationByParam(HashMap<String, Object> params);

    List<OperationLog> selectoperationLogByParam(HashMap<String, Object> params);

    OperationLog selectById(String id);

}
