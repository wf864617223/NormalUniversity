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
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyadviceActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_my_advice)
    Toolbar toolbarMyAdvice;
    @Bind(R.id.et_myAdvice)
    EditText etMyAdvice;
    @Bind(R.id.btn_commit)
    Button btnCommit;

    private PrgDialog prgDialog;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myadvice);
        ButterKnife.bind(this);
        context = MyadviceActivity.this;
        initView();
    }

    private void initView() {
        toolbarMyAdvice.setTitle("");
        setSupportActionBar(toolbarMyAdvice);
        toolbarMyAdvice.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarMyAdvice.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarMyAdvice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        etMyAdvice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
            }
        });
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String advice = etMyAdvice.getText().toString();
                String replace = advice.replace(" ", "");
                if(TextUtils.isEmpty(replace)){
                    T.ShowToast(context,"意见不能为空");
                    return;
                }
                if(advice.length()>140){
                    T.ShowToast(context,"输入字符过长");
                    return;
                }
                //?suerId=1&userType=学生&adviceContent=qwerty
                LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
                String userName = resultBean.getStudentName();
                String userType = "学生";
                /*try {
                    userType = URLEncoder.encode("学生", "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/
                HashMap<String,String> params = new HashMap<String, String>();
                params.put("userName",userName);
                params.put("userType",userType);
                params.put("adviceContent",advice);

                prgDialog = new PrgDialog(context);
                //这里提交建议
                HttpUtil.doHttp(HttpContent.SAVE_USER_ADVICE, params, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {
                        prgDialog.closeDialog();
                        //System.out.println("hahha==>"+result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int status = jsonObject.optInt("status");
                            if(status == 0){
                                etMyAdvice.setText("");
                                T.ShowToast(context,"提交成功");
                            }else{
                                String message = jsonObject.optString("message");
                                T.ShowToast(context,message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        prgDialog.closeDialog();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        prgDialog.closeDialog();
                        T.ShowToast(context,"提交失败，请稍后再试");
                    }
                });
            }
        });
    }
}
