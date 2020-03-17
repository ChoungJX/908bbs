package com.demo.index.dao;



import com.demo.index.domain.po.RecommonDo;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface RecommonDao extends JpaRepository<RecommonDo,String>{
    
    RecommonDo findByRecommonId(String recommonId);
}