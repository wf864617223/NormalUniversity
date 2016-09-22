package com.rf.hp.normaluniversitystu.activity.learn;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rf.hp.normaluniversitystu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OnLineWorkActivity extends AppCompatActivity {

    @Bind(R.id.toobar_online_work)
    Toolbar toobarOnlineWork;
    @Bind(R.id.pb_line)
    ProgressBar pbLine;
    @Bind(R.id.wv_online_work)
    WebView wvOnlineWork;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_line_work);
        ButterKnife.bind(this);
        context = OnLineWorkActivity.this;
        initView();
        initData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(wvOnlineWork.canGoBack()) {
                wvOnlineWork.goBack();//返回上一页面
                return true;
            }
            else {
                finish();//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        String uri = extras.getString("uri");
        System.out.println("=pathuri==>"+uri);
        wvOnlineWork.loadUrl(uri);
        WebSettings settings = wvOnlineWork.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        wvOnlineWork.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        wvOnlineWork.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    pbLine.setVisibility(View.GONE);
                }else{
                    if(View.INVISIBLE == pbLine.getVisibility()){
                        pbLine.setVisibility(View.VISIBLE);
                    }
                    pbLine.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);

            }
        });
    }

    private void initView() {
        toobarOnlineWork.setTitle("");
        setSupportActionBar(toobarOnlineWork);
        toobarOnlineWork.setNavigationIcon(R.mipmap.icon_reg_back);
        toobarOnlineWork.setTitleTextColor(getResources().getColor(R.color.white));
        toobarOnlineWork.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
