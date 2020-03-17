package com.demo.index.dao;


import java.util.List;

import com.demo.index.domain.po.BbsDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface BbsDao extends JpaRepository<BbsDo,String>{
    
    BbsDo findBybbsId(String bbsId);

    List<BbsDo> findBysubmitUserId(String submitUserId);

    List<BbsDo> findBySpareId(String spareId);

    @Query(nativeQuery = true,value = "select * from bbs where spare_id=:spareId order by if_top desc,last_receive_time desc limit :start,:end") //原生SQL方法
    List<BbsDo> getBbs(@Param("spareId")String spareId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from bbs where spare_id=:spareId") //原生SQL方法
    int getBbsNumber(@Param("spareId")String spareId);

    @Query(nativeQuery = true,value = "select * from bbs where submit_user_id=:submitUserId order by submit_time desc limit :start,:end")
    List<BbsDo> getBbs_subuser(@Param("submitUserId")String submitUserId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from bbs where submit_user_id=:submitUserId")
    int getBbs_subuser_numver(@Param("submitUserId")String submitUserId);

    @Query(nativeQuery = true,value = "SELECT * FROM bbs order by read_number desc limit 0,5")
    List<BbsDo> getBbs_hot();

    @Query(nativeQuery = true,value = "SELECT * FROM bbs where spare_id=:spareId order by read_number desc limit 0,5")
    List<BbsDo> getBbs_hot_spare(@Param("spareId")String spareId);

    @Query(nativeQuery = true,value = "select * from bbs where spare_id=:spareId and if_great=\"1\" order by if_top desc,last_receive_time desc limit :start,:end") //原生SQL方法
    List<BbsDo> getBbs_great(@Param("spareId")String spareId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from bbs where spare_id=:spareId and if_great=\"1\"") //原生SQL方法
    int getBbsNumber_great(@Param("spareId")String spareId);

    @Query(nativeQuery = true,value = "select * from bbs where bbs_title like :spareId or bbs_content like :spareId order by last_receive_time desc limit :start,:end") //原生SQL方法
    List<BbsDo> getBbs_search(@Param("spareId")String spareId,@Param("start")int start,@Param("end")int end);

    @Query(nativeQuery = true,value = "select count(*) from bbs where bbs_title like :spareId or bbs_content like :spareId") //原生SQL方法
    int getBbs_search_number(@Param("spareId")String spareId);
}