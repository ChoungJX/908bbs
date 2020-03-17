package com.demo.index.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;


import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "Area")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class AreaDo{
    
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String areaId;


    @Column(length = 32)
    private String areaName;

    @Column(length = 32)
    private String areaUserId;

    @Column(length = 32)
    private String spareId;

    public void createArea(JSONObject jsonObject) {
        this.areaName = jsonObject.get("areaName").toString();
        this.areaUserId = jsonObject.get("areaUserId").toString();
        this.spareId = jsonObject.get("spareId").toString();

    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getareaName() {
        return areaName;
    }

    public void setareaName(String areaName){
        this.areaName= areaName;
    }

    public String getareaUserId(){
        return areaUserId;
    }

    public void setareaUserId(String areaUserId){
        this.areaUserId= areaUserId;
    }

    public String getspareId(){
        return spareId;
    }

    public void setspareId(String spareId){
        this.spareId= spareId;
    }

}