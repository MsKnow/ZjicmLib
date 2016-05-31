package com.know.zjicmlib.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.know.zjicmlib.R;

/**
 * Created by know on 2016/4/12.
 */
public abstract class RefreshFragment extends Fragment {

    public abstract int getRefreshId();
    private SwipeRefreshLayout refreshLayout;

    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefresh(view,getRefreshId());
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initRefresh(View view ,int id){
        refreshLayout = (SwipeRefreshLayout) view.findViewById(id);
        if (refreshLayout!=null){
            refreshLayout.setColorSchemeResources(R.color.color_refresh_1,R.color.color_refresh_2,
                    R.color.color_refresh_3);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    requestData();
                }
            });
        }
    }

    public abstract void requestData();

}
