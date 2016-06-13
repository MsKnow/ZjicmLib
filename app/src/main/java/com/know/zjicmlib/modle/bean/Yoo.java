package com.know.zjicmlib.modle.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by know on 2016/4/16.
 */
public class Yoo {

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("id")String id="000000000";
    String password;
    @Column("name")String name;
    @Column("nickname")String nickname;
    @Ignore
    String start;
    @Ignore
    String college;
    @Ignore
    String major;
    @Ignore
    String born;
    @Column("sex")
    String sex;
    @Column("warning")String warning;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    @Column("imgUrl")String imgUrl;



    /*private static Yoo yoo;

    private  Yoo(){}

    public static Yoo getInstance(){
        if(yoo == null){
            yoo = new Yoo();
        }

        return yoo;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
