package com.demo.index.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Recommon")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class RecommonDo {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String recommonId;

    @Column(length = 128)
    private String title;

    @Column(length = 128)
    private String content;

    @Column(columnDefinition = "LONGTEXT")
    private String pic;

    public void createRC(JSONObject jsonObject){
        this.title = jsonObject.get("title").toString();
        this.content = jsonObject.get("content").toString();
        this.pic = jsonObject.get("pic").toString();
    }

    public String getContent() {
        return content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRecommonId() {
        return recommonId;
    }

    public void setRecommonId(String recommonId) {
        this.recommonId = recommonId;
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

    
}