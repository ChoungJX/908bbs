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
@Table(name = "Bbs")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class BbsDo{
    
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String bbsId;

    @Column(length = 32)
    private String submitUserId;

    @Column(length = 32)
    private String areaId;

    @Column(length = 32)
    private String spareId;

    @Column(length = 32)
    private String bbsType;

    @Column(length = 200)
    private String bbsTitle;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition="LONGTEXT")
    private String bbsContent;

    @Column(length = 32)
    private String submitTime;

    @Column(length = 32)
    private String submitIp;

    @Column(length = 32)
    private int readNumber;

    @Column(length = 32)
    private int receiveNumber;

    @Column(length = 32)
    private String lastReceiveTime;

    @Column(length = 32)
    private String ifHightLight;

    @Column(length = 32)
    private String hightlightUser;

    @Column(length = 32)
    private String hightlightColor;

    @Column(length = 200)
    private String hightlightReason;

    @Column(length = 32)
    private String ifTop = "";

    @Column(length = 32)
    private String topUser;

    @Column(length = 200)
    private String topReason;

    @Column(length = 32)
    private String ifClose;

    @Column(length = 32)
    private String closeUser;

    @Column(length = 200)
    private String closeReason;

    @Column(length = 32)
    private String ifGreat = "";

    @Column(length = 32)
    private String greatUser;

    @Column(length = 200)
    private String greatReason;

    @Column(length = 32)
    private String ifDel;

    @Column(length = 32)
    private String delUser;

    @Column(length = 200)
    private String delReason;

    @Column(length = 32)
    private String paperclip = "";

    @Column(length = 32)
    private String lastUser;

    @Column(length = 32)
    private String lastTime;


    public void createBbs(JSONObject jsonObject){
        What_time now_time = new What_time();

        this.submitUserId = jsonObject.get("submitUserId").toString();
        this.areaId = jsonObject.get("areaId").toString();
        this.spareId = jsonObject.get("spareId").toString();
        this.bbsTitle = jsonObject.get("bbsTitle").toString();
        this.bbsType = "1";
        this.bbsContent = jsonObject.get("bbsContent").toString();
        this.submitTime = now_time.getTime_string();
        this.submitIp = jsonObject.get("ip").toString();
        this.readNumber = 0;
        this.receiveNumber = 0;
        this.lastReceiveTime = now_time.getTime_string();
        this.lastUser = jsonObject.get("submitUserId").toString();
        this.lastTime = now_time.getTime_string();
    }

    public String getBbsId() {
        return bbsId;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public String getPaperclip() {
        return paperclip;
    }

    public void setPaperclip(String paperclip) {
        this.paperclip = paperclip;
    }

    public String getDelReason() {
        return delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    public String getDelUser() {
        return delUser;
    }

    public void setDelUser(String delUser) {
        this.delUser = delUser;
    }

    public String getIfDel() {
        return ifDel;
    }

    public void setIfDel(String ifDel) {
        this.ifDel = ifDel;
    }

    public String getGreatReason() {
        return greatReason;
    }

    public void setGreatReason(String greatReason) {
        this.greatReason = greatReason;
    }

    public String getGreatUser() {
        return greatUser;
    }

    public void setGreatUser(String greatUser) {
        this.greatUser = greatUser;
    }

    public String getIfGreat() {
        return ifGreat;
    }

    public void setIfGreat(String ifGreat) {
        this.ifGreat = ifGreat;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public String getCloseUser() {
        return closeUser;
    }

    public void setCloseUser(String closeUser) {
        this.closeUser = closeUser;
    }

    public String getTopReason() {
        return topReason;
    }

    public void setTopReason(String topReason) {
        this.topReason = topReason;
    }

    public String getTopUser() {
        return topUser;
    }

    public void setTopUser(String topUser) {
        this.topUser = topUser;
    }

    public String getIfTop() {
        return ifTop;
    }

    public void setIfTop(String ifTop) {
        this.ifTop = ifTop;
    }

    public String getHightlightReason() {
        return hightlightReason;
    }

    public void setHightlightReason(String hightlightReason) {
        this.hightlightReason = hightlightReason;
    }

    public String getHightlightColor() {
        return hightlightColor;
    }

    public void setHightlightColor(String hightlightColor) {
        this.hightlightColor = hightlightColor;
    }

    public String getHightlightUser() {
        return hightlightUser;
    }

    public void setHightlightUser(String hightlightUser) {
        this.hightlightUser = hightlightUser;
    }

    public String getIfHightLight() {
        return ifHightLight;
    }

    public void setIfHightLight(String ifHightLight) {
        this.ifHightLight = ifHightLight;
    }

    public String getLastReceiveTime() {
        return lastReceiveTime;
    }

    public void setLastReceiveTime(String lastReceiveTime) {
        this.lastReceiveTime = lastReceiveTime;
    }

    public int getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(int receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    public int getReadNumber() {
        return readNumber;
    }

    public void setReadNumber(int readNumber) {
        this.readNumber = readNumber;
    }

    public String getSubmitIp() {
        return submitIp;
    }

    public void setSubmitIp(String submitIp) {
        this.submitIp = submitIp;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getBbsContent() {
        return bbsContent;
    }

    public void setBbsContent(String bbsContent) {
        this.bbsContent = bbsContent;
    }

    public String getBbsTitle() {
        return bbsTitle;
    }

    public void setBbsTitle(String bbsTitle) {
        this.bbsTitle = bbsTitle;
    }

    public String getBbsType() {
        return bbsType;
    }

    public void setBbsType(String bbsType) {
        this.bbsType = bbsType;
    }

    public String getSpareId() {
        return spareId;
    }

    public void setSpareId(String spareId) {
        this.spareId = spareId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(String submitUserId) {
        this.submitUserId = submitUserId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }
}