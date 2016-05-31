package com.know.zjicmlib.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.know.zjicmlib.R;
import com.know.zjicmlib.activity.ToolbarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by know on 2016/5/26.
 */
public class WebActivity extends ToolbarActivity{

    @Bind(R.id.web_home)WebView webView;
    @Bind(R.id.progressbar)NumberProgressBar progressBar;

    @Override
    protected int getContentId() {
        return R.layout.activity_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.setWebChromeClient(new ChromeClient());

        String src = getIntent().getStringExtra("id");
        webView.loadUrl(src);

    }

    private class ChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.e("progress",newProgress+"%");
            progressBar.setProgress(newProgress);
            if (newProgress == 100){
                progressBar.setVisibility(View.GONE);
            }else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
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
