package com.code.resumemanagement.service.impl;

import com.code.resumemanagement.dao.PersonnelDao;
import com.code.resumemanagement.domain.Personnel;
import com.code.resumemanagement.service.IFTPService;
import com.code.resumemanagement.service.IPersonnelService;
import com.code.resumemanagement.utils.DateUtil;
import com.code.resumemanagement.utils.PageInfo;
import com.code.resumemanagement.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;


@Service
public class PersonnelServiceImpl implements IPersonnelService {
    private static final Logger LOG = LoggerFactory.getLogger(PersonnelServiceImpl.class);
    @Autowired
    private IFTPService ftpService;

    @Autowired
    private PersonnelDao personDao;

    @Override
    public Integer insertPersonnel(Personnel person, String localFilePath) {
        LOG.info("localFilePath:" + localFilePath);
        String id = StringUtil.getUUID();
        person.setId(id);
        person.setCreateDate(DateUtil.getCurrentDateyyyyMMddhhmmss());
        //路径规则在pub/创建年月/主键.文件类型
        String path = "/" + person.getCreateDate().substring(0, 7);
        String resultPath = ftpService.uploadFile(path, person.getId() + localFilePath.substring(localFilePath.lastIndexOf(".")), localFilePath);
        person.setFileUrl(resultPath);
        if (!"".equals(resultPath)) {
            return personDao.insertPersonnel(person);
        } else {
            return 0;
        }

    }

    @Override
    public Integer updatePersonnel(Personnel personnel) {
        return personDao.updatePersonnel(personnel);
    }

    @Override
    public Personnel selectPersonById(String id) {
        return personDao.selectPersonById(id);
    }

    @Override
    public List<Personnel> selectPersonByParam(int thisPage, int pageSize) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("thisPage", thisPage + "");
        params.put("pageSize", pageSize + "");
        return personDao.selectPersonByParam(params);
    }

    @Override
    public Integer selectCountPersonByParam(String userName) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userName", userName);

        return personDao.selectCountPersonByParam(params);
    }

    @Override
    public PageInfo selectPerson(PageInfo pageTableForm) {
        // Integer sum=personDao.selectCountPersonnel();
        pageTableForm.setList(personDao.selectPersonAll());
        return pageTableForm;
    }

    @Override
    public List<Personnel> selectPersonnelAll() {
        return personDao.selectPersonAll();
    }

    @Override
    public PageInfo selectPersonByParam(Personnel personnel) {
        PageInfo pageTableForm = new PageInfo();
        HashMap<String, Object> param = new HashMap<String, Object>();
        String userName = personnel.getUserName();
        if (userName != null) {
            try {
                userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        param.put("userName", userName);
        //param.put("age", personnel.getAge());
        param.put("status", personnel.getStatus());
        List<Personnel> list = personDao.selectPersonByParam(param);
        pageTableForm.setList(list);
        return pageTableForm;
    }

    public void test() {
        LOG.info("info日志");
        LOG.debug("debug日志");
        LOG.error("error日志");
    }

    @Override
    public Integer deletePersonnelById(String id) {
        return personDao.deletePersonnelById(id);
    }

    @Override
    public PageInfo personnelListByPage(String personnelName, String userId, int thisPage, int pageSize) {
        PageInfo pageInfo = new PageInfo();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("start", (thisPage - 1) * pageSize);
        params.put("pageSize", pageSize);
        if (!StringUtil.isEmpty(personnelName)) {
            params.put("userName", personnelName);
            pageInfo.setUserName(personnelName);
        } else {
            pageInfo.setUserName("");
        }
        if (StringUtil.isNotEmpty(userId)) {
            params.put("uploadUserId", userId);
            pageInfo.setUploadUserId(userId);
        } else {
            pageInfo.setUploadUserId("");
        }


        //总记录数
        int countRow = personDao.selectCountPersonByParam(params);
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
        List<Personnel> list = personDao.selectPersonByParam(params);
        pageInfo.setList(list);

        return pageInfo;
    }
}
