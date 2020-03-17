package com.demo.index.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Paperclip")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class PaperclipDo {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String paperclipId;

    @Column(length = 32)
    private String belongBbs;

    @Column(length = 64)
    private String paperclipsUrl;

    @Column(length = 128)
    private String paperclipName;

    
    public String getPaperclipId() {
        return paperclipId;
    }

    public String getPaperclipName() {
        return paperclipName;
    }

    public void setPaperclipName(String paperclipName) {
        this.paperclipName = paperclipName;
    }

    public String getPaperclipsUrl() {
        return paperclipsUrl;
    }

    public void setPaperclipsUrl(String paperclipsUrl) {
        this.paperclipsUrl = paperclipsUrl;
    }

    public String getBelongBbs() {
        return belongBbs;
    }

    public void setBelongBbs(String belongBbs) {
        this.belongBbs = belongBbs;
    }

    public void setPaperclipId(String paperclipId) {
        this.paperclipId = paperclipId;
    }  
}