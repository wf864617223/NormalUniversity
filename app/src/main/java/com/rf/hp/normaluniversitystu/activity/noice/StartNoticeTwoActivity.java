package com.rf.hp.normaluniversitystu.activity.noice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.AlarmClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.NoiceBean;
import com.rf.hp.normaluniversitystu.receiver.NoiceReceiver;
import com.rf.hp.normaluniversitystu.utils.DateTimePickDialogUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartNoticeTwoActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_start_two_notice);
        ButterKnife.bind(this);
        context = StartNoticeTwoActivity.this;
        initView();
        initData();
    }
    private void initData() {
        isChecked = SharePreferInfoUtils.readIsChecked(context,noticeTitle);
        if(isChecked){
            cbIschecked.setChecked(true);
        }else{
            cbIschecked.setChecked(false);
        }
        cbIschecked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbIschecked.isChecked()){
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("是否要设定闹钟？")
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cbIschecked.setChecked(false);
                                }
                            })
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharePreferInfoUtils.saveIsChecked(context,true);
                                    Intent alarmas = new Intent(AlarmClock.ACTION_SET_ALARM);
                                    startActivity(alarmas);
                                }
                            });
                    builder.show();*/
                    TimePickerDialog tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int i, int i1) {
                            //tvStartTimeTime.setText(i+":"+i1);
                            SharePreferInfoUtils.saveIsChecked(context,true,noticeTitle);
                            Intent alarmas = new Intent(AlarmClock.ACTION_SET_ALARM);
                            alarmas.putExtra(AlarmClock.EXTRA_HOUR, i);
                            alarmas.putExtra(AlarmClock.EXTRA_MINUTES, i1);
                            startActivity(alarmas);
                        }
                    },12,10,true);
                    tpd.setTitle("设定提醒时间");
                    tpd.show();
                }else if(!cbIschecked.isChecked()){
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

        Bundle bundle = getIntent().getExtras();
        noticeTitle = bundle.getString("noticeTitle");
        tvNoiceTitle.setText(noticeTitle);
    }


}
