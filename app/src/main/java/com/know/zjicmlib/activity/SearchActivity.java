package com.know.zjicmlib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import com.know.zjicmlib.R;
import com.know.zjicmlib.adapter.SearchListAdapter;
import com.know.zjicmlib.adapter.SearchListAdapterA;
import com.know.zjicmlib.modle.SearchModel;
import com.know.zjicmlib.modle.bean.Boo;
import com.know.zjicmlib.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by yang on 2016/6/2.
 */
public class SearchActivity extends AppCompatActivity{

    @Bind(R.id.search_view)SearchView searchView;
    @Bind(R.id.rv_search)RecyclerView searchBooList;
    @Bind(R.id.swipe_search)SwipeRefreshLayout refreshLayout;
    private int page = 1;
    private String bookName = "hehehe";
    List<Boo> boos;
    SearchListAdapterA adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        initBoosList();
        initRefresh();

        searchView.setSubmitButtonEnabled(true);

        searchView.onActionViewExpanded();

        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bookName = query;

                Log.e("boooooooooookname搜索",bookName);

                page = 1;

                if ("".equals(bookName)) {
                    ToastUtil.tShort("不能为空");

                } else {
                    showLoading();
                    getBoos();
                }


                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bookName = newText;
                Log.e("boooooooooookname",bookName);
                return true;
            }
        });

    }


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
        adapter = new SearchListAdapterA(boos);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        searchBooList.setLayoutManager(layoutManager);

        adapter.setOnItemClickListener((position) -> {
            Boo boo = boos.get(position);
            Log.e("click", boo.getName());
            Intent intent = new Intent(this, OneBoosActivity.class);
            intent.putExtra("boo", boo);
            startActivity(intent);
        });

        searchBooList.setAdapter(adapter);
        searchBooList.setOnScrollListener(getScrollToBottomListener(layoutManager));
    }

    private void initRefresh(){
        refreshLayout.setColorSchemeResources(R.color.color_refresh_1, R.color.color_refresh_2,
                R.color.color_refresh_3);

        refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            getBoos();
        });

        /*refreshLayout.setOnRefreshListener(() -> {
            page = 1;
            getBoos();
        });*/
    }

    private void showLoading(){
        refreshLayout.setRefreshing(true);
        //searchButton.setEnabled(false);
    }

    private void hideLoading(){
        refreshLayout.setRefreshing(false);
        //searchButton.setEnabled(true);
    }


}
