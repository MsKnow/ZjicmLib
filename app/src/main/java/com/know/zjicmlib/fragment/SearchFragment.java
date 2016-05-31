package com.know.zjicmlib.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.know.zjicmlib.R;
import com.know.zjicmlib.activity.OneBoosActivity;
import com.know.zjicmlib.adapter.SearchListAdapter;
import com.know.zjicmlib.modle.bean.Boo;
import com.know.zjicmlib.modle.SearchModel;
import com.know.zjicmlib.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by know on 2016/4/12.
 */
public class SearchFragment extends Fragment{

    @Bind(R.id.et_search)EditText searchEdit;
    @Bind(R.id.bt_search)Button searchButton;
    @Bind(R.id.rv_search)RecyclerView searchBooList;
    @Bind(R.id.swipe_search)SwipeRefreshLayout refreshLayout;
    String bookName;
    private int page = 1;

    List<Boo> boos;
    SearchListAdapter adapter;

    private void getBoos(){

        SearchModel.searchBoos(bookName, page, new Subscriber<List<Boo>>() {
            @Override
            public void onCompleted() {
                Log.e("onCompleted", "en");
                hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError", e.toString());
                ToastUtil.tShort("网络错误");
                hideLoading();
            }

            @Override
            public void onNext(List<Boo> booss) {
                if (page == 1)
                    boos.clear();
                boos.addAll(booss);
                adapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);

        initBoosList();
        initRefresh();

        searchButton.setOnClickListener(view1 -> {
            page = 1;
            bookName = searchEdit.getText().toString();
            if ("".equals(bookName)) {
                ToastUtil.tShort("不能为空");

            } else {
                showLoading();
                getBoos();
            }

        });

        return view;
    }

    private RecyclerView.OnScrollListener getScrollToBottomListener(
        LinearLayoutManager layoutManager){
        return new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int last = layoutManager.findLastCompletelyVisibleItemPosition();
                boolean isBottom = last>=adapter.getItemCount()-1;
                //Log.e("last: ",last+" "+adapter.getItemCount());
                if (!refreshLayout.isRefreshing()&&isBottom){
                    if(adapter.getItemCount()>=page*30){
                        showLoading();
                        page+=1;
                        getBoos();
                    }

                }
            }
        };
    }

    private void initBoosList(){
        boos = new ArrayList<>();
        adapter = new SearchListAdapter(this.getActivity(),boos);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false);
        searchBooList.setLayoutManager(layoutManager);

        adapter.setBooClickListener((position) -> {
            Boo boo = boos.get(position);
            Log.e("click", boo.getName());
            Intent intent = new Intent(this.getActivity(), OneBoosActivity.class);
            intent.putExtra("boo", boo);
            startActivity(intent);
        });

        searchBooList.setAdapter(adapter);
        searchBooList.setOnScrollListener(getScrollToBottomListener(layoutManager));
    }
    private void initRefresh(){
        refreshLayout.setColorSchemeColors(R.color.color_refresh_1, R.color.color_refresh_2,
                R.color.color_refresh_3);
        refreshLayout.setOnRefreshListener(()->{
            page = 1;
            getBoos();
        });
    }

    private void showLoading(){
        refreshLayout.setRefreshing(true);
        searchButton.setEnabled(false);
    }

    private void hideLoading(){
        refreshLayout.setRefreshing(false);
        searchButton.setEnabled(true);
    }

}
