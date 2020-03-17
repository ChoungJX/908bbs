package com.demo.index.dao;

import java.util.List;

import com.demo.index.domain.po.UserDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface UserDao extends JpaRepository<UserDo,String>{
    UserDo findByUsername(String username);

    UserDo findByUId(String uId);

    @Query(nativeQuery = true,value = "select * from user limit :start,:end")
    List<UserDo> getAllUser(@Param("start")int start,@Param("end")int end);
    
}