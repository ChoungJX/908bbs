package com.demo.index.dao;

import com.demo.index.domain.po.TestDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TestDao extends JpaRepository<TestDo,String>{
    
}