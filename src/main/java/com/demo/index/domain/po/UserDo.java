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
@Table(name = "User")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class UserDo {
 
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String uId;


    @Column(length = 32, nullable = false)
    private String username;

    @Column(length = 32, nullable = false)
    private String password;

    @Column(length = 32, nullable = false)
    private String type;

    @Column(length = 32, nullable = false)
    private String email;

    @Column(length = 200)
    private String psw_question;

    @Column(length = 200)
    private String psw_answer;

    @Column(length = 32)
    private String enroll_time;

    @Column(length = 32)
    private String first_login_time;

    @Column(length = 32)
    private String first_login_ip;

    @Column(length = 32)
    private String last_login_time;

    @Column(length = 32)
    private String last_login_ip;

    @Column(length = 32)
    private int forum_number;

    @Column(length = 32)
    private int agree_forum_number;

    public void enroll_user(JSONObject jsonParam) {
        What_time now_time = new What_time();

        this.username = jsonParam.get("username").toString();
        this.password = jsonParam.get("password").toString();
        this.email = jsonParam.get("email").toString();
        this.psw_question = jsonParam.get("question").toString();
        this.psw_answer = jsonParam.get("answer").toString();
        this.first_login_ip = jsonParam.get("ip").toString();
        this.last_login_ip = jsonParam.get("ip").toString();

        this.type = "100";
        this.enroll_time = now_time.getTime_string();
        this.first_login_time = now_time.getTime_string();
        this.last_login_time = now_time.getTime_string();
        this.agree_forum_number = 0;
        this.forum_number = 0;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Integer getAgree_forum_number() {
        return agree_forum_number;
    }

    public void setAgree_forum_number(Integer agree_forum_number) {
        this.agree_forum_number = agree_forum_number;
    }

    public Integer getForum_number() {
        return forum_number;
    }

    public void setForum_number(Integer forum_number) {
        this.forum_number = forum_number;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getFirst_login_ip() {
        return first_login_ip;
    }

    public void setFirst_login_ip(String first_login_ip) {
        this.first_login_ip = first_login_ip;
    }

    public String getFirst_login_time() {
        return first_login_time;
    }

    public void setFirst_login_time(String first_login_time) {
        this.first_login_time = first_login_time;
    }

    public String getEnroll_time() {
        return enroll_time;
    }

    public void setEnroll_time(String enroll_time) {
        this.enroll_time = enroll_time;
    }

    public String getPsw_answer() {
        return psw_answer;
    }

    public void setPsw_answer(String psw_answer) {
        this.psw_answer = psw_answer;
    }

    public String getPsw_question() {
        return psw_question;
    }

    public void setPsw_question(String psw_question) {
        this.psw_question = psw_question;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

 }