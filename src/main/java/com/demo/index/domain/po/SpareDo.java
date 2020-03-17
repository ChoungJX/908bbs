package com.demo.index.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;


import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "Spare")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class SpareDo{
    
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String spareId;

    @Column(length = 32)
    private String spareName;

    @Column(length = 32)
    private String spareUserId;

    @Column(length = 32)
    private String subordinateAreaId;

    @Column(length = 210)
    private String paperclip;

    public void creatSpare(JSONObject jsonObject) {
        this.spareName = jsonObject.get("spareName").toString();
        this.spareUserId = jsonObject.get("spareUserId").toString();
        this.subordinateAreaId = jsonObject.get("subordinateAreaId").toString();
        this.paperclip = jsonObject.get("info").toString();
    }

    public String getPaperclip() {
        return paperclip;
    }

    public void setPaperclip(String paperclip) {
        this.paperclip = paperclip;
    }

    public String getSpareId() {
        return spareId;
    }

    public String getSubordinateAreaId() {
        return subordinateAreaId;
    }

    public void setSubordinateAreaId(String subordinateAreaId) {
        this.subordinateAreaId = subordinateAreaId;
    }

    public String getSpareUserId() {
        return spareUserId;
    }

    public void setSpareUserId(String spareUserId) {
        this.spareUserId = spareUserId;
    }

    public String getSpareName() {
        return spareName;
    }

    public void setSpareName(String spareName) {
        this.spareName = spareName;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId;
    }

    


}