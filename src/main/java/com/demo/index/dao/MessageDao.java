package com.demo.index.dao;

import java.util.List;

import com.demo.index.domain.po.MessageDo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageDao extends JpaRepository<MessageDo,String>{
    
    @Query(nativeQuery = true,value = "select * from message where receive_user_id=:uId and m_status='0' order by create_time desc limit :start,:end") //原生SQL方法
    List<MessageDo> selectReceiveNo(@Param("uId")String uId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from message where receive_user_id=:uId and m_status='0'") //原生SQL方法
    int selectReceiveNo_number(@Param("uId")String uId);

    @Query(nativeQuery = true,value = "select * from message where receive_user_id=:uId and m_status='1' order by create_time desc limit :start,:end") //原生SQL方法
    List<MessageDo> selectReceiveYes(@Param("uId")String uId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from message where receive_user_id=:uId and m_status='1'") //原生SQL方法
    int selectReceiveYes_number(@Param("uId")String uId);

    @Query(nativeQuery = true,value = "select aaa.m_title,user.username,aaa.create_time,aaa.m_id from (select * from message where send_user_id=:uId order by create_time desc limit :start,:end) as aaa,user where aaa.receive_user_id = user.u_id  and aaa.m_status!='3'") //原生SQL方法
    List<Object> selectSendYes(@Param("uId")String uId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from (select * from message where send_user_id=:uId) as aaa,user where aaa.receive_user_id = user.u_id  and aaa.m_status!='3'") //原生SQL方法
    int selectSendYes_number(@Param("uId")String uId);


}