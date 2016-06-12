package com.know.zjicmlib.activity;

import android.os.Bundle;

import com.know.zjicmlib.R;

/**
 * Created by yang on 2016/6/13.
 */
public class AboutActivity extends ToolbarActivity{


    @Override
    protected int getContentId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar()!=null){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");}
        toolbar.setNavigationOnClickListener(v->AboutActivity.this.onBackPressed());

    }
}
