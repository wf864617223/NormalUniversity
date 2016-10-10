package com.rf.hp.normaluniversitystu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import org.xutils.view.annotation.ContentView;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.et_login_username)
    EditText etLoginUsername;
    @Bind(R.id.et_login_pwd)
    EditText etLoginPwd;
    @Bind(R.id.la_btn_login)
    Button laBtnLogin;
    @Bind(R.id.tv_forgetpwd)
    TextView tvForgetpwd;

    private PrgDialog prgDialog;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context = LoginActivity.this;
        getSupportActionBar().hide();

        initData();
    }

    private void initData() {
        etLoginUsername.setText(SharePreferInfoUtils.readLoginName(context));
        etLoginPwd.setText(SharePreferInfoUtils.readLoginPwd(context));
        laBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = etLoginUsername.getText().toString();
                String pwd = etLoginPwd.getText().toString();
                if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(pwd)){
                    T.ShowToast(context,"输入不能为空");
                }else{
                    HashMap<String,String> params = new HashMap<String, String>();
                    params.put("account",userName);
                    params.put("password",pwd);
                    prgDialog = new PrgDialog(context);
                    HttpUtil.doHttp(HttpContent.LOGIN_URL, params, new HttpUtil.IHttpResult() {
                        @Override
                        public void onSuccess(String result) {
                            LoginBean loginBean = GsonTools.getBean(result,LoginBean.class);
                            int status = loginBean.getStatus();
                            if(status == 0){
                                SharePreferInfoUtils.saveLoginName(context,etLoginUsername.getText().toString());
                                SharePreferInfoUtils.saveLoginPwd(context,etLoginPwd.getText().toString());
                                LoginBean.ResultBean resultInfo = loginBean.getResult();
                                SharePreferInfoUtils.saveUserInfo(context,resultInfo);
                                LoginBean.ResultBean.StaffBean staff = resultInfo.getStaff();
                                String staffId = staff.getStaffId();
                                SharePreferInfoUtils.saveStaffId(context,staffId);
                                prgDialog.closeDialog();
                                Intent intent = new Intent(context,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                prgDialog.closeDialog();
                                String message = loginBean.getMessage();
                                T.ShowToast(context,message);

                            }
                        }
                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            prgDialog.closeDialog();
                            T.ShowToast(context,"服务器连接失败");
                        }
                    });
                }
            }
        });
        tvForgetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ForgotPwdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
