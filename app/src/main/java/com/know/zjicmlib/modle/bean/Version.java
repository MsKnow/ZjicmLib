package com.know.zjicmlib.modle.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by yang on 2016/6/13.
 */
public class Version {
    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("versionCode")
    int versionCode;
    @Column("versionName")
    String versionName;
    @Column("versionLog")
    String versionLog;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionLog() {
        return versionLog;
    }

    public void setVersionLog(String versionLog) {
        this.versionLog = versionLog;
    }
}
