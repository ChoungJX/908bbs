package com.demo.index.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Info")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class InfoDo {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String infoId;

    @Column(length = 128)
    private String title;

    @Column(columnDefinition="LONGTEXT")
    private String content;

    public String getInfoId() {
        return infoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }
    
}