package com.rf.hp.normaluniversitystu.activity.noice;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.T;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoiceInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_noice_info)
    Toolbar toolbarNoiceInfo;
    @Bind(R.id.tv_noice)
    TextView tvNoice;
    @Bind(R.id.tv_noice_content)
    TextView tvNoiceContent;
    @Bind(R.id.tv_noice_data)
    TextView tvNoiceData;
    Context context;
    @Bind(R.id.wb_notice_content)
    WebView wbNoticeContent;
    private String noticeTitle;
    private String noticeDate;
    private String noticeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noice_info);
        ButterKnife.bind(this);
        context = NoiceInfoActivity.this;
        initData();
    }

    private void initData() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(1);
        toolbarNoiceInfo.setTitle("");
        setSupportActionBar(toolbarNoiceInfo);
        toolbarNoiceInfo.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarNoiceInfo.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarNoiceInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        noticeTitle = bundle.getString("noticeTitle");
        noticeDate = bundle.getString("noticeDate");
        noticeContent = bundle.getString("noticeContent");
        try {
            String decode = URLDecoder.decode(noticeContent, "utf-8");
            wbNoticeContent.loadDataWithBaseURL(
                    HttpContent.ABOUT_US,
                    decode,
                    "text/html",
                    "utf-8",
                    null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        tvNoice.setText(noticeTitle);
        tvNoiceContent.setText(noticeContent);
        tvNoiceData.setText(noticeDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_noice_remind, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_noice_remind) {
            //使用定时通知功能。当应用被系统回收后就不会显示通知
            /*Intent intent = new Intent(context,StartNoticeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("noticeTitle",noticeTitle);
            bundle.putString("noticeDate",noticeDate);
            bundle.putString("noticeContent",noticeContent);
            intent.putExtras(bundle);
            startActivity(intent);*/
            //进入一个页面，用来查看是否已经设置闹钟
            Intent intent = new Intent(context,StartNoticeTwoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("noticeTitle",noticeTitle);
            bundle.putString("noticeDate",noticeDate);
            bundle.putString("noticeContent",noticeContent);
            intent.putExtras(bundle);
            startActivity(intent);
            //跳到系统闹钟界面
            /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("是否要设定闹钟？")
                    .setNegativeButton("否", null)
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent alarmas = new Intent(AlarmClock.ACTION_SET_ALARM);

                            //alarmas.putExtra(AlarmClock.EXTRA_HOUR, 9);
                           // alarmas.putExtra(AlarmClock.EXTRA_MINUTES, 37);
                            startActivity(alarmas);
                        }
                    });
            builder.show();*/
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
