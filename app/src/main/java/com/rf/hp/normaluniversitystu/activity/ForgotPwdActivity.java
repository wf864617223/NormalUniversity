package com.rf.hp.normaluniversitystu.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rf.hp.normaluniversitystu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 忘记密码界面
 */
public class ForgotPwdActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_forgot_pwd)
    Toolbar toolbarForgotPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);
        ButterKnife.bind(this);
        toolbarForgotPwd.setTitle("");
        setSupportActionBar(toolbarForgotPwd);
        toolbarForgotPwd.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarForgotPwd.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarForgotPwd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
