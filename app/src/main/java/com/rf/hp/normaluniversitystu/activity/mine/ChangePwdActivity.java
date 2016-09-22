package com.rf.hp.normaluniversitystu.activity.mine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangePwdActivity extends AppCompatActivity {

    @Bind(R.id.changPwd_toolbar)
    Toolbar changPwdToolbar;
    @Bind(R.id.et_oldPwd)
    EditText etOldPwd;
    @Bind(R.id.et_newPwd)
    EditText etNewPwd;
    @Bind(R.id.et_affinePwd)
    EditText etAffinePwd;
    @Bind(R.id.btn_affinePwd)
    Button btnAffinePwd;

    Context context;
    private PrgDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
        context = ChangePwdActivity.this;
        initView();
        initData();
    }

    private void initData() {

        btnAffinePwd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String oldPwd = etOldPwd.getText().toString();
                        final String newPwd = etNewPwd.getText().toString();
                        final String affinePwd = etAffinePwd.getText().toString();
                        int length = newPwd.length();
                        if (TextUtils.isEmpty(oldPwd) || TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(affinePwd)) {
                            T.ShowToast(context, "输入不能为空");
                            return;
                        }
                        if (!newPwd.equals(affinePwd)) {
                            T.ShowToast(context, "两次输入密码不一致");
                            return;
                        }

                        if(length<6||length>10){
                            T.ShowToast(context,"建议输入6-10位密码");
                            return;
                        }
                        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
                        String token = resultBean.getToken();
                        HashMap<String, String> params = new HashMap<String, String>();
                        String account = SharePreferInfoUtils.readLoginName(context);
                        params.put("account", account);
                        params.put("oldPassword", oldPwd);
                        params.put("newPassword", newPwd);
                        params.put("token", token);
                        prgDialog = new PrgDialog(context);
                        HttpUtil.doHttp(HttpContent.CHANGE_PWD_URL, params, new HttpUtil.IHttpResult() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int status = jsonObject.optInt("status");
                                    if (status == 0) {
                                        prgDialog.closeDialog();
                                        T.ShowToast(context, "修改成功!");
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
                                prgDialog.closeDialog();
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                prgDialog.closeDialog();
                                T.ShowToast(context, "服务器出错了，请稍后再试");
                            }
                        });

                    }
                });
    }

    private void initView() {

        changPwdToolbar.setTitle("");
        setSupportActionBar(changPwdToolbar);
        changPwdToolbar.setNavigationIcon(R.mipmap.icon_reg_back);
        changPwdToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        changPwdToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
