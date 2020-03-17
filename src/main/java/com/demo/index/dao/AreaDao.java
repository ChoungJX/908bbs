package com.demo.index.dao;

import java.util.List;

import com.demo.index.domain.po.AreaDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AreaDao extends JpaRepository<AreaDo,String>{
    AreaDo findByareaId(String areaId);

    List<AreaDo> findByAreaUserId(String areaUserId);

}