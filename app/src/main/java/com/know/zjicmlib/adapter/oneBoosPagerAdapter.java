package com.know.zjicmlib.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by know on 2016/4/20.
 */
public class oneBoosPagerAdapter extends PagerAdapter {

    List<View> layouts;

    public oneBoosPagerAdapter(List<View> layouts) {
        this.layouts = layouts;
    }

    @Override
    public int getCount() {
        return layouts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ((ViewPager) container).addView(layouts.get(position));

        return layouts.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "简介";
            case 1:
                return "借阅情况";
            default: return "df";
        }
    }
}
