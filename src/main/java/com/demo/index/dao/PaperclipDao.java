package com.demo.index.dao;

import com.demo.index.domain.po.PaperclipDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PaperclipDao extends JpaRepository<PaperclipDo,String>{
    PaperclipDo findByBelongBbs(String belongBbs);
}