package com.know.zjicmlib.modle.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Created by yang on 2016/6/7.
 */
public class Ranker {

    @PrimaryKey(AssignType.BY_MYSELF)
    @Column("word")
    String word;
    @Column("rank")
    int rank;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
