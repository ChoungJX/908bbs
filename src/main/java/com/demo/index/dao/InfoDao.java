package com.demo.index.dao;


import com.demo.index.domain.po.InfoDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface InfoDao extends JpaRepository<InfoDo,String>{

}