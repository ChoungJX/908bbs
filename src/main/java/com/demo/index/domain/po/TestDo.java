package com.demo.index.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 用户实体类
 * 
 * @author zlf
 * @since 2019-9-3
 */

@Entity
@Table(name = "test")
public class TestDo {
 
    @Id
    @Column(length = 40)
    private String USER_ID;

    @Column(length = 32,nullable = false)
    private String username;

    @Column(length = 32,nullable = false)
    private String password;


    public String getUSER_ID() {
        return USER_ID;
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

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

 }