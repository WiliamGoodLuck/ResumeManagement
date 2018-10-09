package com.code.resumemanagement.dao;

import com.code.resumemanagement.domain.Personnel;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface PersonnelDao {
    Integer insertPersonnel(Personnel person);

    Integer batchInsertPersonnel(List<Personnel> personList);

    Integer updatePersonnel(Personnel personnel);

    List<Personnel> selectPersonByParam(HashMap<String, Object> params);

    List<Personnel> selectPersonAll();

    Personnel selectPersonById(String id);

    Integer deletePersonnelById(String id);

    Integer selectCountPersonByParam(HashMap<String, Object> params);
}
