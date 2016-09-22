package com.rf.hp.normaluniversitystu.activity.mine;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.DateUtils;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LolAddRequestActivity extends AppCompatActivity {

    @Bind(R.id.toobar_lol_add_request)
    Toolbar toobarLolAddRequest;
    @Bind(R.id.rb_thing_leave)
    RadioButton rbThingLeave;
    @Bind(R.id.rb_sick_leave)
    RadioButton rbSickLeave;
    @Bind(R.id.tv_startTime_date)
    EditText tvStartTimeDate;
    @Bind(R.id.tv_startTime_Time)
    EditText tvStartTimeTime;
    @Bind(R.id.ll_startTime)
    LinearLayout llStartTime;
    @Bind(R.id.tv_endTime_date)
    EditText tvEndTimeDate;
    @Bind(R.id.tv_endTime_Time)
    EditText tvEndTimeTime;
    @Bind(R.id.ll_endTime)
    LinearLayout llEndTime;
    @Bind(R.id.et_myAdvice)
    EditText etMyAdvice;
    @Bind(R.id.tv_warning)
    TextView tvWarning;
    @Bind(R.id.btn_requestLeave)
    Button btnRequestLeave;

    Context context;
    private String nowDate;
    private PrgDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lol_add_request);
        ButterKnife.bind(this);
        context = LolAddRequestActivity.this;
        initView();
        initDate();
    }

    private void initDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        nowDate = formatter.format(curDate);
        final String[] start_date = nowDate.split("-");
        tvStartTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // 获得日历实例
                        Calendar calendar = Calendar.getInstance();

                        calendar.set(view.getYear(), view.getMonth(),
                                view.getDayOfMonth());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateTime = sdf.format(calendar.getTime());
                        tvStartTimeDate.setText(dateTime);
                    }
                }, Integer.parseInt(start_date[0]), Integer.parseInt(start_date[1]), Integer.parseInt(start_date[2]));
                dpd.show();
            }
        });
        tvStartTimeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tvStartTimeTime.setText(i+":"+i1);
                    }
                },12,10,true);
                tpd.show();
            }
        });
        tvEndTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // 获得日历实例
                        Calendar calendar = Calendar.getInstance();

                        calendar.set(view.getYear(), view.getMonth(),
                                view.getDayOfMonth());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateTime = sdf.format(calendar.getTime());
                        tvEndTimeDate.setText(dateTime);
                    }
                }, Integer.parseInt(start_date[0]), Integer.parseInt(start_date[1]), Integer.parseInt(start_date[2]));
                dpd.show();
            }
        });
        tvEndTimeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        tvEndTimeTime.setText(i+":"+i1);
                    }
                },12,10,true);
                tpd.show();
            }
        });
        initData();

    }

    private void initData() {
        btnRequestLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startTimeDate = tvStartTimeDate.getText().toString();
                String startTimeTime = tvStartTimeTime.getText().toString();
                String endTimeDate = tvEndTimeDate.getText().toString();
                String endTimeTime = tvEndTimeTime.getText().toString();
                String myAdvice = etMyAdvice.getText().toString();
                String replace = myAdvice.replace(" ", "");
                if(TextUtils.isEmpty(startTimeDate)||TextUtils.isEmpty(startTimeTime)||TextUtils.isEmpty(endTimeDate)||TextUtils.isEmpty(endTimeTime)||TextUtils.isEmpty(myAdvice)){
                    T.ShowToast(context,"请填写完整信息");
                    return;
                }
                if(TextUtils.isEmpty(replace)){
                    T.ShowToast(context,"请填写请假理由");
                    return;
                }
                if (myAdvice.length() > 100) {
                    T.ShowToast(context, "理由过长，请缩短字数");
                    return;
                }
                String startTime = startTimeDate+" "+startTimeTime+":00";
                String endTime = endTimeDate+" "+endTimeTime+":00";
                int i = DateUtils.compare_date(startTime, endTime);
                if (i == 1) {
                    T.ShowToast(context, "请假起始时间选择错误");
                    return;
                }
                int i1 = DateUtils.compare_date(startTime, nowDate);
                if(i1 == -1){
                    T.ShowToast(context,"请假起始时间错误");
                    return;
                }
                boolean otherData = DateUtils.isNoOtherData(myAdvice);
                if (!otherData) {
                    T.ShowToast(context, "输入内容有误");
                    return;
                }
                LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
                String studentId = resultBean.getStudentId();
                String leaveStyle = "";
                if (rbThingLeave.isChecked()) {
                    leaveStyle = "事假";
                } else if (rbSickLeave.isChecked()) {
                    leaveStyle = "病假";
                }
                String staffId = SharePreferInfoUtils.readStaffId(context);
                String token = resultBean.getToken();
                HashMap<String, String> params = new HashMap<>();
                params.put("studentId", studentId);
                params.put("staffId", staffId);
                params.put("askForLeaveType", leaveStyle);
                params.put("askForLeaveStartTime", startTime);
                params.put("askForLeaveEndTime", endTime);
                params.put("askForLeaveContent", myAdvice);
                params.put("token", token);
                prgDialog = new PrgDialog(context);
                HttpUtil.doHttp(HttpContent.COMMIT_LEAVE_REQUEST_URL, params, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int status = jsonObject.optInt("status");
                            if (status == 0) {
                                prgDialog.closeDialog();
                                T.ShowToast(context, "已经提交给导师，请稍后查看状态");
                                finish();
                            } else {
                                prgDialog.closeDialog();
                                String message = jsonObject.optString("message");
                                T.ShowToast(context, message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        prgDialog.closeDialog();
                        T.ShowToast(context, "提交失败");
                    }
                });
            }
        });

    }

    private void initView() {
        toobarLolAddRequest.setTitle("");
        setSupportActionBar(toobarLolAddRequest);
        toobarLolAddRequest.setNavigationIcon(R.mipmap.icon_reg_back);
        toobarLolAddRequest.setTitleTextColor(getResources().getColor(R.color.white));
        toobarLolAddRequest.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rbThingLeave.setChecked(true);
        etMyAdvice.setHint("输入请假理由，不超过100个字,只能输入汉字，数字，英文字符");
    }
}
