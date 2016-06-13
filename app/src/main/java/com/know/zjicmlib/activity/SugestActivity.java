package com.know.zjicmlib.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.know.zjicmlib.APP;
import com.know.zjicmlib.R;
import com.know.zjicmlib.modle.bean.Yoo;
import com.know.zjicmlib.retrofit.ServiceFactory;
import com.know.zjicmlib.util.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yang on 2016/6/14.
 */
public class SugestActivity extends ToolbarActivity{

    @Bind(R.id.et_sug)EditText sugEditText;
    @Bind(R.id.bt_sug)Button sugButton;
    Yoo yoo;

    @Override
    protected int getContentId() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        yoo = APP.getYoo();

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("建议");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }toolbar.setNavigationOnClickListener(v->SugestActivity.this.onBackPressed());
    }


    @OnClick(R.id.bt_sug)
    public void suggest(){

        sugButton.setText("提交中。。");
        sugButton.setEnabled(false);

        String sug = sugEditText.getText().toString();
        //Log.e("sug",yoo.getId()+" "+sug);
        if (sug.length()>50) {
            ToastUtil.tShort("不能超过50个字");
        }else if (sug.length()==0){
            ToastUtil.tShort("不能为空");
        }else {
            ServiceFactory.getService().postSug(yoo.getId(),sug)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(body -> {
                /*try {
                    String html = body.string();
                    Log.e("html-->",html);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                ToastUtil.tShort("提交成功，感谢！");
                sugButton.setText("提交");
                sugButton.setEnabled(true);
            }, Throwable->{
                   Throwable.printStackTrace();
                   ToastUtil.tShort("提交失败，请检查网络");
                    sugButton.setText("提交");
                    sugButton.setEnabled(true);
            });
        }

    }

}
