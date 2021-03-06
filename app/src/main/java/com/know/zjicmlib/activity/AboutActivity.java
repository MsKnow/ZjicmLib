package com.know.zjicmlib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.know.zjicmlib.APP;
import com.know.zjicmlib.R;
import com.know.zjicmlib.retrofit.ServiceFactory;
import com.know.zjicmlib.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yang on 2016/6/13.
 */
public class AboutActivity extends ToolbarActivity{

    @Bind(R.id.version_me)TextView myVersionText;
    @Bind(R.id.version_latest)TextView latestVersionText;
    @Bind(R.id.bt_version_check)Button versionCheckButton;
    @Bind(R.id.card_versionLog)View versionLogCard;
    @Bind(R.id.tv_about_versionLog)TextView versionLogText;
    //@Bind(R.id.tv_about_app)TextView appDownText;

    int myVersion;
    String myVersionName;
    @Override
    protected int getContentId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        //appDownText.setText(Html.fromHtml("http://fir.im/ZCLib"));

        if (getSupportActionBar()!=null){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");}
        toolbar.setNavigationOnClickListener(v->AboutActivity.this.onBackPressed());

        myVersion = APP.getVersionCode();
        myVersionName = APP.getVersionName();
        myVersionText.setText("当前版本："+myVersionName);

        checkVersion();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_share:



                break;
            case R.id.action_sug:

                Intent intent = new Intent(AboutActivity.this,SugestActivity.class);
                startActivity(intent);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.bt_version_check)
    public void checkVersion(){

        versionCheckButton.setText("检测中...");
        versionCheckButton.setEnabled(false);

        ServiceFactory.getService().getLatestVersion(System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(version -> {

                    Log.e("version",version.getVersionCode()+version.getVersionName()+version.getVersionLog());


                    if(version.getVersionCode()>myVersion){
                        latestVersionText.setText("有新版本：" + version.getVersionName()+"\n\nhttp://fir.im/ZCLib");

                        versionLogText.setText(version.getVersionLog());
                        versionLogCard.setVisibility(View.VISIBLE);

                    }else {
                        latestVersionText.setText("当前为最新版本");
                    }
                    latestVersionText.setVisibility(View.VISIBLE);

                    versionCheckButton.setText("检测");
                    versionCheckButton.setEnabled(true);

                },e -> {
                    e.printStackTrace();
                    ToastUtil.tShort("检查网络后重试");
                    versionCheckButton.setText("重新检测");
                    versionCheckButton.setEnabled(true);

                });

    }

}
