package com.know.zjicmlib.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.know.zjicmlib.R;
import com.know.zjicmlib.activity.view.OneBoosView;
import com.know.zjicmlib.adapter.OneBooListAdapter;
import com.know.zjicmlib.adapter.oneBoosPagerAdapter;
import com.know.zjicmlib.modle.OneBoosModel;
import com.know.zjicmlib.modle.OneBoosModelImp;
import com.know.zjicmlib.modle.bean.Boo;
import com.know.zjicmlib.myview.WrapContentViewPager;
import com.know.zjicmlib.retrofit.ServiceFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by know on 2016/4/15.
 */
public class OneBoosActivity extends ToolbarActivity implements OneBoosView{

    OneBoosModel oneBoosModel;

    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.tab_one)TabLayout oneTab;
    @Bind(R.id.CoolToolbar)CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.img_boo_one)ImageView booImage;
    @Bind(R.id.tv_boo_one)TextView booText;
    TextView summaryText;
    RecyclerView oneBooList;

    @Bind(R.id.vp_one)WrapContentViewPager booPagers;

    oneBoosPagerAdapter pagerAdapter;
    List<View> layouts = new ArrayList<>();

    List<Boo> oneBoos;
    OneBooListAdapter adapter;

    private void initPager(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        View lLayout = LayoutInflater.from(this).inflate(R.layout.layout_summary,null);
        View rLayout = LayoutInflater.from(this).inflate(R.layout.layout_list_ones,null);
        summaryText = (TextView) lLayout.findViewById(R.id.tv_summary);
        oneBooList = (RecyclerView) rLayout.findViewById(R.id.list_one_boo);
        initList();
        layouts.add(lLayout);
        layouts.add(rLayout);
        pagerAdapter = new oneBoosPagerAdapter(layouts);

        booPagers.setAdapter(pagerAdapter);

        oneTab.setupWithViewPager(booPagers);
    }
    private void initList(){
        oneBoos = new ArrayList<>();
        adapter = new OneBooListAdapter(oneBoos,R.layout.item_boos_one);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        oneBooList.setLayoutManager(layoutManager);
        oneBooList.setAdapter(adapter);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_oneboos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        initPager();

        oneBoosModel = new OneBoosModelImp(this);

        Boo boo = (Boo) getIntent().getSerializableExtra("boo");
        String oneBooStr = boo.getName()+"\n\n"+boo.getAuthor()+"\n\n"+boo.getId();
        booText.setText(oneBooStr);
        //collapsingToolbarLayout.setTitle(boo.getName());/////////


        Log.e("booCheck", boo.getCheck());//item.php?marc_no=0000467162

        String marc_no = boo.getCheck().substring(17);

        ServiceFactory.getService().getOneBoos(marc_no)
                .map(responseBody -> {
                    String html = "";
                    try {
                        html = responseBody.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String isbn = oneBoosModel.parseHtml(html);

                    return isbn;
                })
                .flatMap(s -> ServiceFactory.getService().getDouban(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(douban -> {

                    Glide.with(this).load(douban.getImage())
                            .placeholder(R.drawable.ic_wait_24dp)
                            .into(booImage);
                    Log.e("summary", douban.getSummary());
                    summaryText.setText(douban.getSummary());adapter.notifyDataSetChanged();
                }, Throwable::printStackTrace);

        //ServiceFactory.getService().getOneBoos(marc_no)金瓶梅

    }

    @Override
    public void setOneBoos(List<Boo> oneBooss) {
        Log.e("setOneboos",oneBooss.get(0).getId());

        oneBoos.clear();
        oneBoos.addAll(oneBooss);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
