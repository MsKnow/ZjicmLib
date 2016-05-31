package com.know.zjicmlib.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.know.zjicmlib.APP;
import com.know.zjicmlib.R;
import com.know.zjicmlib.activity.WebActivity;
import com.know.zjicmlib.adapter.NoticeListAdapter;
import com.know.zjicmlib.modle.NoticesModel;
import com.know.zjicmlib.modle.bean.Notice;
import com.know.zjicmlib.modle.bean.Yoo;
import com.know.zjicmlib.retrofit.ServiceFactory;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by know on 2016/3/6.
 */
public class HomeFragment extends Fragment {

    String url;// = "http://218.75.124.139:8091/sms/opac/news/showNewsList.action?type=1&xc=4&pageSize=20";

    private NoticesModel model;

    private View view;

    @Bind(R.id.list_notice)RecyclerView noticeList;
    //@Bind(R.id.web_home)WebView webView;

    List<Notice> notices = new ArrayList<>();
    NoticeListAdapter adapter;
    Notice myNotice;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this, view);

        model = new NoticesModel();

        /*webView.canGoBack();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        url = "file://"+getActivity().getFilesDir().getPath()+"/home.html";
        webView.loadUrl(url);*/

        //notices.add(myNotice);

        initNoticeList();

        getNotices();

        return view;

    }

    private void initNoticeList(){

        QueryBuilder query = new QueryBuilder(Notice.class);
        notices.clear();
        notices.addAll(APP.mDb.query(query));

        adapter = new NoticeListAdapter(notices,R.layout.item_notice);
        adapter.setOnItemClickListener(position -> {

            Intent intent = new Intent(this.getActivity(), WebActivity.class);
            String src = notices.get(position).getSrc();
            intent.putExtra("id",src);
            startActivity(intent);

        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),1,false);
        noticeList.setAdapter(adapter);
        noticeList.setLayoutManager(layoutManager);

    }

    private void getNotices(){

        ServiceFactory.getService().getHome()
                .map(model::parseHtml)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        notices1 ->{
                            notices.clear();
                            notices.addAll(notices1);
                            saveNotices(notices1);
                            adapter.notifyDataSetChanged();
                        }
                );

    }

    private void saveNotices(List<Notice> notices){
        APP.mDb.insert(notices, ConflictAlgorithm.Ignore);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
