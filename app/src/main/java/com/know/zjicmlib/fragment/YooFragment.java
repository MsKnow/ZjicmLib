package com.know.zjicmlib.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.know.zjicmlib.APP;
import com.know.zjicmlib.R;
import com.know.zjicmlib.adapter.MyBooListAdapter;
import com.know.zjicmlib.fragment.view.YooView;
import com.know.zjicmlib.modle.YooModel;
import com.know.zjicmlib.modle.YooModelImp;
import com.know.zjicmlib.modle.bean.Boo;
import com.know.zjicmlib.modle.bean.Yoo;
import com.know.zjicmlib.retrofit.ServiceFactory;
import com.know.zjicmlib.util.ToastUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by know on 2016/4/16.
 */
public class YooFragment extends Fragment implements YooView{

    @Bind(R.id.et_id)EditText idText;
    @Bind(R.id.et_password)EditText passwordText;
    @Bind(R.id.bt_login)Button loginButton;

    @Bind(R.id.card_login)View loginCard;
    @Bind(R.id.card_me)View meCard;
    @Bind(R.id.name_me)TextView myNameText;
    @Bind(R.id.my_tip)TextView myTip;
    @Bind(R.id.warning)TextView myWarning;

    @Bind(R.id.list_me_boo)RecyclerView myBooList;
    MyBooListAdapter adapter ;
    List<Boo> boos ;

    Yoo yoo;
    String id,password,tip;

    YooModel yooModel;

    ProgressDialog progressDialog ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("youfragment on create ");
        if (APP.online){
            System.out.println("youfragment on create online");
            yoo = APP.yoo;
            if(yoo == null){
                Log.e("app.online ","en app.yoo == null,接下来从db获取,不可能");

                yoo = APP.getYoo();

            }else{
                System.out.println("内存中的yoo");
            }

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yoo, container, false);
        ButterKnife.bind(this, view);

        yooModel = new YooModelImp(this);

        progressDialog = new ProgressDialog(this.getContext(),R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("登陆中。。。");

        initMyBooList();

        if(APP.online){
            //onLogin();
            id = yoo.getId();
            password = yoo.getPassword();
            cardOnline();
            System.out.println("onlineooooo:::::::"+id+" "+password);
            login(false);
        }else {
            onLogout();
        }

        return view;
    }

    private void initMyBooList(){

        boos = new ArrayList<>();
        adapter = new MyBooListAdapter(boos);
        adapter.setOnBooClickListener(this::renewBoo);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getActivity(),1,false);
        myBooList.setLayoutManager(manager);

        myBooList.setAdapter(adapter);
    }

    private void renewBoo(int position){
        Boo boo = boos.get(position);
        ServiceFactory.getService().renewMyBoo(boo.getId(),boo.getCheck(),"")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(body -> {
                try {
                    String str = body.string();
                    //Log.e("renewBody", body.string());
                    ToastUtil.tShort(Html.fromHtml(str));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, throwable -> {

            });
    }

    @OnClick(R.id.bt_login)
    void onLogin(){
        id = idText.getText().toString();
        password = passwordText.getText().toString();
        if("".equals(id)||"".equals((password))){
            //ToastUtil.tShort("用户名或密码不能为空");
            idText.setError("用户名或密码不能为空");
        }else {
            login(true);
        }

    }

    private void login(boolean click){
        if (click)
        showLoading();
        else showLoading_gray();
        ServiceFactory.getService().login(id, password, "cert_no")
                .map(yooModel::parseHtml1)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext((yooo) -> {
                    if (yooo != null) {

                        yoo = yooo;
                        yoo.setId(id);
                        yoo.setPassword(password);
                        //用户名入库
                        yooModel.storeYoo(yoo);
                        APP.online = true;///////////////////放这里
                        //onLoginSuccess();
                    } else {
                        ToastUtil.tShort("用户名或密码错误");
                        throw new RuntimeException();
                    }
                }).observeOn(Schedulers.io())
                .flatMap(yooo -> ServiceFactory.getService().getMyBoos())
                .map(yooModel::parseHtml2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(booss -> {

                    hideLoading();
                    boos.clear();
                    boos.addAll(booss);
                    adapter.notifyDataSetChanged();

                    tip = "距超期5天之内可续借";
                    myTip.setText(tip);

                }, throwable -> {
                    throwable.printStackTrace();
                    System.out.println(throwable.toString());
                    System.out.println(throwable.getClass().toString());
                    System.out.println(java.lang.NullPointerException.class.toString());
                    System.out.println(SocketTimeoutException.class.toString());
                    if (throwable instanceof java.lang.NullPointerException) {
                        tip = "人丑就要多读书";
                        myTip.setText(tip);
                        ToastUtil.tShort("人丑就要多读书");
                    } else if (throwable instanceof SocketTimeoutException) {
                        System.out.println("超时，请重试");
                        ToastUtil.tShort("连接超时，请重试 (须校内网)");
                    } else if (throwable instanceof ConnectException) {
                        System.out.println("失败，请重试");
                        ToastUtil.tShort("连接失败，请重试 (须校内网)");
                    } /*else if (throwable instanceof RuntimeException) {
                        ToastUtil.tShort("用户名或密码错误");
                    }*/
                    hideLoading();
                });
    }

    public void logout(){
        APP.mDb.delete(Yoo.class);
        yoo = null;
        APP.yoo = null;
        APP.online = false;
        onLogout();
    }

    private void showLoading(){
        showLoading_gray();
        progressDialog.show();
    }
    private void showLoading_gray(){
        loginButton.setText("登陆中");
        loginButton.setEnabled(false);
    }
    private void hideLoading(){
        loginButton.setText("再登陆");
        loginButton.setEnabled(true);
        progressDialog.dismiss();
    }
    private void cardOnline(){

        loginCard.setVisibility(View.GONE);
        meCard.setVisibility(View.VISIBLE);
        myBooList.setVisibility(View.VISIBLE);
        myNameText.setTextColor(Color.GRAY);
        myNameText.setText(yoo.getNickname());
        myWarning.setText(yoo.getWarning());
    }
    public void onLoginSuccess(){
        cardOnline();
        myNameText.setTextColor(Color.WHITE);
    }
    private void onLogout(){
        loginCard.setVisibility(View.VISIBLE);
        meCard.setVisibility(View.GONE);
        myBooList.setVisibility(View.GONE);
        myNameText.setText("怎么还不登陆");
    }

}
