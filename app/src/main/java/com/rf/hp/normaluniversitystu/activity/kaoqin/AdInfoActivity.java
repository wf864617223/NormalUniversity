package com.rf.hp.normaluniversitystu.activity.kaoqin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.utils.GetAdviceInfo;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 广告详情界面
 */
public class AdInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_ad_info)
    Toolbar toolbarAdInfo;
    @Bind(R.id.iv_ad_img)
    ImageView ivAdImg;
    /*@Bind(R.id.tv_ad_content)
    TextView tvAdContent;*/
    @Bind(R.id.tv_ad_content)
    WebView tvAdContent;
    @Bind(R.id.tv_ad_time)
    TextView tvAdTime;

    Context context;

    private String advertId;
    public final static String CSS_STYLE ="<style>* {font-size:16px;line-height:20px;}p {color:#FFFFFF;}</style>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_info);
        ButterKnife.bind(this);
        context = AdInfoActivity.this;
        initView();
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        advertId = bundle.getString("advertId");
        String advertContent = bundle.getString("advertContent");
        String advertTime = bundle.getString("advertTime");
        String url = bundle.getString("advertPicture");
        //Glide.with(context).load(url).placeholder(R.drawable.nopicture).error(R.drawable.nopicture).into(ivAdImg);
        //tvAdContent.setText(advertContent);
        try {
            String decode = URLDecoder.decode(advertContent, "utf-8");
            tvAdContent.loadDataWithBaseURL(
                    HttpContent.GET_AD_URL,
                    CSS_STYLE+decode,
                    "text/html",
                    "utf-8",
                    null
            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        tvAdTime.setText(advertTime);
    }

    private void initView() {
        toolbarAdInfo.setTitle("");
        setSupportActionBar(toolbarAdInfo);
        toolbarAdInfo.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarAdInfo.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarAdInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvAdContent.setBackgroundColor(getResources().getColor(R.color.black));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String adviceInfo = GetAdviceInfo.adviceInfo();
        String phoneIp = GetAdviceInfo.getPhoneIp();
        HashMap<String, String> params = new HashMap<>();
        params.put("advertId", advertId);
        params.put("scanDevice", adviceInfo);
        params.put("scanIp", phoneIp);
        HttpUtil.doHttp(HttpContent.SAVE_AD_INFO, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
    }
}
