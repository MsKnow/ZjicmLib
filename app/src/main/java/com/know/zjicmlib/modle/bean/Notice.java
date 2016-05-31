package com.know.zjicmlib.modle.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.util.Date;

/**
 * Created by know on 2016/5/26.
 */
public class Notice {


    @Column("title")
    private String title;
    @Column("date")
    private String date;
    @Column("src")
    @PrimaryKey(AssignType.BY_MYSELF)
    private String src;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
