package com.code.resumemanagement.service;

import com.code.resumemanagement.domain.Personnel;
import com.code.resumemanagement.utils.PageInfo;


import java.util.HashMap;
import java.util.List;

public interface IPersonnelService {
    /**
     * 插入一条信息.
     *
     * @param personnel     人员信息
     * @param localFilePath 本地上传简历路径
     * @return 影响条数
     */
    Integer insertPersonnel(Personnel personnel, String localFilePath);

    /**
     * 更新人员信息.
     *
     * @param personnel 人员信息
     * @return 影响条数
     */
    Integer updatePersonnel(Personnel personnel);

    /**
     * 根据主键查询人员.
     *
     * @param id 主键
     * @return 人员信息
     */
    Personnel selectPersonById(String id);

    /**
     * 根据条件查询人员。
     *
     * @return 人员信息
     */
    List<Personnel> selectPersonByParam(int thisPage, int pageSize);

    /**
     * 模糊查询姓名。
     * @param userName 姓名
     * @return 总数
     */
    Integer selectCountPersonByParam(String userName);

    PageInfo selectPerson(PageInfo pageTableForm);

    /**
     * 查询所有人员。
     * @return
     */
    List<Personnel> selectPersonnelAll();

    PageInfo selectPersonByParam(Personnel personnel);

    void test();

    /**
     * 根据id删除人员
     * @param id 主键id
     * @return
     */
    Integer deletePersonnelById(String id);

    /**
     * 分页
     * @param personnelName
     * @param userId
     * @param thispage
     * @param rowperpage
     * @return
     */
    PageInfo personnelListByPage(String personnelName, String userId, int thispage, int rowperpage);
}
