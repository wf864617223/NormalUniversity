package com.rf.hp.normaluniversitystu.activity.noice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.NoiceBean;
import com.rf.hp.normaluniversitystu.receiver.NoiceReceiver;
import com.rf.hp.normaluniversitystu.utils.DateTimePickDialogUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartNoticeActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_noice_start)
    Toolbar toolbarNoiceStart;
    @Bind(R.id.cb_ischecked)
    CheckBox cbIschecked;
    @Bind(R.id.tv_noice_title)
    TextView tvNoiceTitle;
    @Bind(R.id.tv_noice_time)
    TextView tvNoiceTime;

    Context context;
    private String noticeTitle;
    private String noticeDate;
    private boolean isChecked = false;
    private boolean reminder;
    private String initEndDateTime;
    private String noticeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_notice);
        ButterKnife.bind(this);
        context = StartNoticeActivity.this;
        initView();
        initData();
    }

    private long times;
    private String initStartDateTime;
    private Handler handler = new Handler(new Handler.Callback() {

        private Date date;

        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    isChecked = true;
                    String stutime = tvNoiceTime.getText().toString();
                    SharePreferInfoUtils.saveNoiceTime(context,noticeTitle,stutime);
                    if(!TextUtils.isEmpty(stutime)){
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            System.out.println("==stutime=>"+stutime);
                            date = formatter.parse(stutime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        times = date.getTime();
                        //T.ShowToast(context, "可以通知");
                        setReminder(isChecked, times);
                    }

                    break;
                case 2:
                    //T.ShowToast(context, "不可以通知");
                    isChecked = false;
                    setReminder(isChecked, times);
                    break;
            }
            return true;
        }
    });

    private void initData() {

        if(isChecked){
            cbIschecked.setChecked(true);
            String s = tvNoiceTime.getText().toString();
            if(TextUtils.isEmpty(s)){
                T.ShowToast(context,"请设定提醒时间");
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        StartNoticeActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog("请设定要提醒的时间",tvNoiceTime);
            }
        }else{
            cbIschecked.setChecked(false);
            T.ShowToast(context,"请设定提醒时间");
            DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                    StartNoticeActivity.this, initEndDateTime);
            dateTimePicKDialog.dateTimePicKDialog("请设定要提醒的时间",tvNoiceTime);
        }
        cbIschecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbIschecked.isChecked()){
                    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    NoiceBean.ResultBean noiceContent = new NoiceBean.ResultBean();
                    noiceContent.setNoticeTitle(noticeTitle);
                    noiceContent.setNoticeDate(noticeDate);
                    noiceContent.setNoticeContent(noticeContent);
                    SharePreferInfoUtils.saveNoticeInfo(context,noiceContent);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    SharePreferInfoUtils.saveIsChecked(context,true,noticeTitle);
                }else if(!cbIschecked.isChecked()){
                    Message msg = new Message();
                    msg.what = 2;
                    handler.sendMessage(msg);
                    SharePreferInfoUtils.saveIsChecked(context,false,noticeTitle);
                }
            }
        });


    }

    private void initView() {
        toolbarNoiceStart.setTitle("");
        setSupportActionBar(toolbarNoiceStart);
        toolbarNoiceStart.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarNoiceStart.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarNoiceStart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        isChecked = SharePreferInfoUtils.readIsChecked(context,noticeTitle+"");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis()+600000);//获取当前时间后的10分钟

        initStartDateTime = formatter.format(curDate);
        System.out.println("=kk=>"+initStartDateTime);
        Bundle bundle = getIntent().getExtras();
        noticeTitle = bundle.getString("noticeTitle");
        noticeDate = bundle.getString("noticeDate");
        noticeContent = bundle.getString("noticeContent");
        tvNoiceTitle.setText(noticeTitle);
        //tvNoiceTime.setText(noticeDate);
        String time = SharePreferInfoUtils.readNoiceTime(context, noticeTitle);
        tvNoiceTime.setText(time);
    }

    public void setReminder(boolean b, long time) {
        // 获取 AlarmManager instance
        AlarmManager am= (AlarmManager) getSystemService(ALARM_SERVICE);
        //创建一个延迟意图（点击通知后要跳转的意图）
        PendingIntent pi = PendingIntent.
                getBroadcast(this, 0, new Intent(this,NoiceReceiver.class), 0);

        if(b){
            am.set(AlarmManager.RTC_WAKEUP, time, pi);
        }
        else{
            // 关闭当前 alarm
            am.cancel(pi);
        }
    }
}
