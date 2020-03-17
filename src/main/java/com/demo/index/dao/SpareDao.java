package com.demo.index.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.demo.index.domain.po.SpareDo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface SpareDao extends JpaRepository<SpareDo,String>{

    SpareDo findByspareId(String spareId);
    
    List<SpareDo> findBySubordinateAreaId(String subordinateAreaId);

    List<SpareDo> findBySpareUserId(String spareUserId);

    @Query(nativeQuery = true,value = "SELECT sp.spare_name,ar.area_name FROM spare sp INNER JOIN area ar WHERE sp.subordinate_area_id=ar.area_id AND sp.spare_id=:spareId") //原生SQL方法
    JSONObject getSpare_areaName(@Param("spareId")String spareId);

    @Query(nativeQuery = true,value = "SELECT sp.spare_id,ar.area_id FROM spare sp INNER JOIN area ar WHERE sp.subordinate_area_id=ar.area_id AND sp.spare_id=:spareId") //原生SQL方法
    JSONObject getSpare_areaId(@Param("spareId")String spareId);

    @Query(nativeQuery = true,value = "SELECT distinct spare.subordinate_area_id FROM spare where spare_user_id=:userId")
    List<JSONObject> getAreaSet(@Param("userId")String userId);

    @Query(nativeQuery = true,value ="SELECT *  FROM spare where spare_user_id=:userId and subordinate_area_id=:areaId")
    List<SpareDo> getSpareList(@Param("userId")String userId,@Param("areaId")String areaId);

    @Query(nativeQuery = true,value ="SELECT sum(read_number) as allnumber,spare_id FROM bbs group by spare_id order by allnumber desc limit 0,5")
    List<JSONObject> getHot2();
}