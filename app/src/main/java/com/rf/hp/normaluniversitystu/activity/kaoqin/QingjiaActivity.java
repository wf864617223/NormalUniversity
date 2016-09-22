package com.rf.hp.normaluniversitystu.activity.kaoqin;

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

/**
 * 请假界面
 */
public class QingjiaActivity extends AppCompatActivity {

    @Bind(R.id.toobar_add_request)
    Toolbar toobarAddRequest;
    @Bind(R.id.rb_thing_leave)
    RadioButton rbThingLeave;
    @Bind(R.id.rb_sick_leave)
    RadioButton rbSickLeave;
    @Bind(R.id.ll_startTime)
    LinearLayout llStartTime;
    @Bind(R.id.ll_endTime)
    LinearLayout llEndTime;
    @Bind(R.id.et_myAdvice)
    EditText etMyAdvice;
    @Bind(R.id.tv_warning)
    TextView tvWarning;
    @Bind(R.id.btn_requestLeave)
    Button btnRequestLeave;

    Context context;
    private PrgDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);
        ButterKnife.bind(this);
        context = QingjiaActivity.this;
        initView();
        initData();
    }

    private void initData() {

        btnRequestLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String askForLeaveType = "";
                if(rbThingLeave.isChecked()){
                    askForLeaveType = "事假";
                }else if(rbSickLeave.isChecked()){
                    askForLeaveType = "病假";
                }
                LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
                String token = resultBean.getToken();
                String studentId = resultBean.getStudentId();
                String staffId = SharePreferInfoUtils.readStaffId(context);
                Bundle bundle = getIntent().getExtras();
                String askForLeaveStartTime = bundle.getString("askForLeaveStartTime");
                String askForLeaveEndTime = bundle.getString("askForLeaveEndTime");
                String stuRequest = etMyAdvice.getText().toString();
                String replace = stuRequest.replace(" ", "");
                boolean otherData = DateUtils.isNoOtherData(stuRequest);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String time = format.format(date);
                /*String[] nowTime = time.split(" ");
                String[] startTime = askForLeaveStartTime.split(" ");*/
                int i = DateUtils.compare_date(time, askForLeaveStartTime);
                int bigOneHour = DateUtils.isBigOneHour(time, askForLeaveStartTime);
                /*if(nowTime[0].equals(startTime[0])){
                    String[] nowData = nowTime[1].split(":");
                    String[] startData = startTime[1].split(":");
                    int parseIntNow = Integer.parseInt(nowData[0]);
                    int parseIntStart = Integer.parseInt(startData[0]);
                    if(parseIntStart-parseIntNow<=1){
                        T.ShowToast(context,"开课前1小时不能请假");
                        return;
                    }
                }*/
                if(bigOneHour == 0){
                    T.ShowToast(context,"开课前1小时不能请假");
                    return;
                }
                if(TextUtils.isEmpty(replace)){
                    T.ShowToast(context,"理由不能为空");
                    return;
                }
                if (!otherData) {
                    T.ShowToast(context, "输入内容有误");
                    return;
                }
                if(i == 1){
                    T.ShowToast(context,"已经过了请假时间，不可以请假");
                    return;
                }
                if(stuRequest.length()>100){
                    T.ShowToast(context,"理由过长，请缩短字数");
                    return;
                }
                HashMap<String,String> parms = new HashMap<String, String>();
                parms.put("studentId",studentId);
                parms.put("staffId",staffId);
                parms.put("askForLeaveType",askForLeaveType);
                parms.put("askForLeaveStartTime",askForLeaveStartTime);
                parms.put("askForLeaveEndTime",askForLeaveEndTime);
                parms.put("askForLeaveContent",stuRequest);
                parms.put("token",token);
                prgDialog = new PrgDialog(context);
                HttpUtil.doHttp(HttpContent.COMMIT_LEAVE_REQUEST_URL, parms, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {
                        prgDialog.closeDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int status = jsonObject.optInt("status");
                            if(status == 0){
                                T.ShowToast(context,"已经提交给导师，请稍后在请假申请里查看状态");
                                finish();
                            }else{
                                String message = jsonObject.optString("message");
                                T.ShowToast(context,message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        prgDialog.closeDialog();
                        T.ShowToast(context,"提交失败。请稍后再试");
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
        tvWarning.setVisibility(View.VISIBLE);
        llStartTime.setVisibility(View.GONE);
        llEndTime.setVisibility(View.GONE);
        rbThingLeave.setChecked(true);
        etMyAdvice.setHint("输入理由，不超过100个字");
        etMyAdvice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        etMyAdvice.setHint("输入请假理由，不超过100个字，只能输入汉字，数字，英文字符");
    }
}
