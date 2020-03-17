package com.demo.index.domain.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;
import com.demo.index.util.What_time;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Receive")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class ReceiveDo {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String receiveId;

    @Column(length = 32)
    private String bbsId;

    @Column(length = 32)
    private String reSubmitUserID;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGTEXT")
    private String receiveContent;

    @Column(length = 32)
    private String receiveTime;

    @Column(length = 32)
    private String receiveIp;

    @Column(length = 32)
    private int likeNumber;


    public void createReceive(JSONObject jsonObject){
        What_time now = new What_time();

        this.bbsId = jsonObject.get("bbsId").toString();
        this.receiveContent = jsonObject.get("receiveContent").toString();
        this.reSubmitUserID = jsonObject.get("submitUserID").toString();
        this.receiveTime = now.getTime_string();
        this.receiveIp = jsonObject.get("ip").toString();
        this.likeNumber = 0;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getReceiveIp() {
        return receiveIp;
    }

    public void setReceiveIp(String receiveIp) {
        this.receiveIp = receiveIp;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiveContent() {
        return receiveContent;
    }

    public void setReceiveContent(String receiveContent) {
        this.receiveContent = receiveContent;
    }

    public String getSubmitUserID() {
        return reSubmitUserID;
    }

    public void setSubmitUserID(String submitUserID) {
        this.reSubmitUserID = submitUserID;
    }

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

}