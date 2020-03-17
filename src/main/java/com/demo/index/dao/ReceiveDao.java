package com.demo.index.dao;

import java.util.List;

import com.demo.index.domain.po.ReceiveDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public interface ReceiveDao extends JpaRepository<ReceiveDo,String>{
    
    List<ReceiveDo> findBybbsId(String bbsId);

    ReceiveDo findByreceiveId(String reid);

    @Query(nativeQuery = true,value = "select * from receive where bbs_id =:bbsId order by receive_time asc limit :start,:end") //原生SQL方法
    List<ReceiveDo> getReceive(@Param("bbsId")String bbsId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from receive where bbs_id =:bbsId") //原生SQL方法
    int getReceiveNumber(@Param("bbsId")String bbsId);

    @Query(nativeQuery = true,value = "select * from receive where re_submit_userid =:bbsId order by receive_time desc limit :start,:end") //原生SQL方法
    List<ReceiveDo> getReceive_user(@Param("bbsId")String bbsId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from receive where re_submit_userid =:bbsId") //原生SQL方法
    int getReceiveNumber_user(@Param("bbsId")String bbsId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "DELETE FROM receive where bbs_id =:bbsId")
    void delReceive_bbsId(@Param("bbsId")String bbsId);

}