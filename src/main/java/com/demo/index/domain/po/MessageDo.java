package com.demo.index.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;
import com.demo.index.util.What_time;

import org.hibernate.annotations.GenericGenerator;



/**
 * 用户实体类
 * 
 * @author zlf
 * @since 2019-9-3
 */

@Entity
@Table(name = "Message")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class MessageDo{
    
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String mId;

    @Column(length = 32)
    private String receiveUserId;

    @Column(length = 32)
    private String sendUserId;

    @Column(length = 200)
    private String mTitle;

    @Column(columnDefinition="TEXT")
    private String mContent;

    @Column(length = 32)
    private String createTime;

    @Column(length = 32)
    private String mStatus;


    public void createMessage(JSONObject jsonObject){
        What_time now_time = new What_time();

        this.sendUserId = jsonObject.get("sendUserId").toString();
        this.receiveUserId = jsonObject.get("receiveUserId").toString();
        this.mTitle = jsonObject.get("mTitle").toString();
        this.mContent = jsonObject.get("mContent").toString();
        this.mStatus = jsonObject.get("mStatus").toString();
        this.createTime = now_time.getTime_string();
    }


    public String getmId() {
        return mId;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

}
