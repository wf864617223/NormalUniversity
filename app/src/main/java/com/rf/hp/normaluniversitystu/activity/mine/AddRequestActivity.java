package com.rf.hp.normaluniversitystu.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.DateChooseUtils;
import com.rf.hp.normaluniversitystu.utils.DateTimePickDialogUtil;
import com.rf.hp.normaluniversitystu.utils.DateUtils;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddRequestActivity extends AppCompatActivity {

    @Bind(R.id.toobar_add_request)
    Toolbar toobarAddRequest;
    @Bind(R.id.tv_startTime)
    TextView tvStartTime;
    @Bind(R.id.ll_startTime)
    LinearLayout llStartTime;
    @Bind(R.id.tv_endTime)
    TextView tvEndTime;
    @Bind(R.id.ll_endTime)
    LinearLayout llEndTime;
    @Bind(R.id.et_myAdvice)
    EditText etMyAdvice;
    @Bind(R.id.btn_requestLeave)
    Button btnRequestLeave;

    Context context;
    @Bind(R.id.rb_thing_leave)
    RadioButton rbThingLeave;
    @Bind(R.id.rb_sick_leave)
    RadioButton rbSickLeave;

    private PrgDialog prgDialog;
    private String initStartDateTime;
    private String initEndDateTime;
    private String nowDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        ButterKnife.bind(this);
        context = AddRequestActivity.this;
        initView();
        initData();
    }

    private void initData() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        initStartDateTime = formatter.format(curDate);
        nowDate = formatter.format(curDate);
        tvStartTime.setText(initStartDateTime);
        tvEndTime.setText(initStartDateTime);
        llStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        AddRequestActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog("设定开始时间", tvStartTime);

            }
        });
        llEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        AddRequestActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog("设定结束时间", tvEndTime);
            }
        });
        etMyAdvice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        btnRequestLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startTime = tvStartTime.getText().toString()+":00";
                String endTime = tvEndTime.getText().toString()+":00";
                String advice = etMyAdvice.getText().toString();
                String replace = advice.replace(" ", "");
                if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime) || TextUtils.isEmpty(replace)) {
                    T.ShowToast(context, "输入不能为空");
                    return;
                }
                if (advice.length() > 100) {
                    T.ShowToast(context, "理由过长，请缩短字数");
                    return;
                }
                int i = DateUtils.compare_date(startTime, endTime);
                if (i == 1) {
                    T.ShowToast(context, "请假起始时间选择错误");
                    return;
                }
                if(i == 0){
                    T.ShowToast(context, "请假起始时间选择错误");
                    return;
                }
                int i1 = DateUtils.compare_date(startTime, nowDate);
                if(i1 == -1){
                    T.ShowToast(context,"请假起始时间错误");
                    return;
                }
                boolean otherData = DateUtils.isNoOtherData(advice);
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
                params.put("askForLeaveContent", advice);
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
                                T.ShowToast(context, "已经提交给导师，请稍后查看请假状态");
                                finish();
                            } else {
                                prgDialog.closeDialog();
                                String message = jsonObject.optString("message");
                                if("Invalid Token".equals(message)){
                                    T.ShowToast(context,"账号在其他地方登录");
                                }else{
                                    T.ShowToast(context, message);
                                }
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
        toobarAddRequest.setTitle("");
        setSupportActionBar(toobarAddRequest);
        toobarAddRequest.setNavigationIcon(R.mipmap.icon_reg_back);
        toobarAddRequest.setTitleTextColor(getResources().getColor(R.color.white));
        toobarAddRequest.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rbThingLeave.setChecked(true);
        etMyAdvice.setHint("输入请假理由，不超过100个字，只能输入汉字，数字，英文字符");
    }
}
