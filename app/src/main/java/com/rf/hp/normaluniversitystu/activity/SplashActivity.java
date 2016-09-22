package com.rf.hp.normaluniversitystu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    Context context;
    private PrgDialog prgDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        context = SplashActivity.this;
        initIntent();
    }

    /**
     * 添加自动登陆
     */
    private void initIntent() {
        String loginName = SharePreferInfoUtils.readLoginName(context);
        String loginPwd = SharePreferInfoUtils.readLoginPwd(context);
        if(TextUtils.isEmpty(loginName)||TextUtils.isEmpty(loginPwd)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent loginIntent = new Intent(context,LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }, 2000);
        }else{
            login(loginName,loginPwd);
        }
    }

    /**
     * 登录方法
     * @param loginName 登录名
     * @param loginPwd 密码
     */
    private void login(String loginName, String loginPwd) {
        HashMap<String,String> parmas = new HashMap<>();
        parmas.put("account",loginName);
        parmas.put("password",loginPwd);
        //prgDialog = new PrgDialog(context);
        HttpUtil.doHttp(HttpContent.LOGIN_URL, parmas, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                System.out.println("==userInfo==>"+result);
                LoginBean loginBean = GsonTools.getBean(result, LoginBean.class);
                int status = loginBean.getStatus();
                if (status == 0) {
                    LoginBean.ResultBean resultInfo = loginBean.getResult();
                    SharePreferInfoUtils.saveUserInfo(context, resultInfo);
                    //prgDialog.closeDialog();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //prgDialog.closeDialog();
                    Intent loginIntent = new Intent(context,LoginActivity.class);
                    SharePreferInfoUtils.clearLoginPwd(context);
                    startActivity(loginIntent);
                    finish();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Intent loginIntent = new Intent(context,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }
}
