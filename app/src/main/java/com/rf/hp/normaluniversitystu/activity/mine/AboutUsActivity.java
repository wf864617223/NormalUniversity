package com.rf.hp.normaluniversitystu.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.AboutUsBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.LoadingView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends AppCompatActivity {


    @Bind(R.id.toolbar_about_us)
    Toolbar toolbarAboutUs;
    @Bind(R.id.tv_about_content)
    TextView tvAboutContent;

    Context context;
    @Bind(R.id.wb_about_us)
    WebView wbAboutUs;
    @Bind(R.id.about_loading)
    LoadingView aboutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        context = AboutUsActivity.this;
        initView();
        initData();
    }

    private void initData() {
        aboutLoading.setVisibility(View.VISIBLE);
        HttpUtil.doHttp(HttpContent.ABOUT_US, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                aboutLoading.setVisibility(View.GONE);
                AboutUsBean aboutUsBean = GsonTools.getBean(result, AboutUsBean.class);
                int status = aboutUsBean.getStatus();
                if (status == 0) {
                    AboutUsBean.ResultBean result1 = aboutUsBean.getResult();
                    String aboutusContent = result1.getAboutusContent();
                    tvAboutContent.setText(aboutusContent+"");
                    try {
                        if(!TextUtils.isEmpty(aboutusContent)){
                            String decode = URLDecoder.decode(aboutusContent, "utf-8");
                            wbAboutUs.loadDataWithBaseURL(
                                    HttpContent.ABOUT_US,
                                    decode,
                                    "text/html",
                                    "utf-8",
                                    null);
                        }else{
                            T.ShowToast(context,"服务器没有数据");
                        }

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                } else {
                    T.ShowToast(context, "数据获取失败");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                aboutLoading.setVisibility(View.GONE);
                T.ShowToast(context, "服务器连接失败");
            }
        });
    }

    private void initView() {

        toolbarAboutUs.setTitle("");
        setSupportActionBar(toolbarAboutUs);
        toolbarAboutUs.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarAboutUs.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarAboutUs.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
