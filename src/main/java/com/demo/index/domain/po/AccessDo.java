package com.demo.index.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Access")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class AccessDo {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String accessId;

    @Column(length = 32)
    private String accessName;

    @Column(length = 64)
    private String accessUrl;

    @Column(length = 32)
    private String accessWeight;

    public String getAccessName() {
        return accessName;
    }

    public String getAccessWeight() {
        return accessWeight;
    }

    public void setAccessWeight(String accessWeight) {
        this.accessWeight = accessWeight;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    
}