package com.demo.index.dao;

import java.util.List;

import com.demo.index.domain.po.AccessDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface AccessDao extends JpaRepository<AccessDo,String>{
    
    @Query(nativeQuery = true,value = "SELECT * FROM access where access_weight <=:aweight") //原生SQL方法
    List<AccessDo> getAccess(@Param("aweight")String weight);
}