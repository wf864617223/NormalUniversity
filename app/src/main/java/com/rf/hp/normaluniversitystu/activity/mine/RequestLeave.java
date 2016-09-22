package com.rf.hp.normaluniversitystu.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.adapter.LeaveRequestAdapter;
import com.rf.hp.normaluniversitystu.bean.LeaveRequestBean;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.LoadingView;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 请假申请界面
 */
public class RequestLeave extends AppCompatActivity {

    @Bind(R.id.toobar_request_leave)
    Toolbar toobarRequestLeave;
    Context context;
    @Bind(R.id.lv_my_requestleave)
    ListView lvMyRequestleave;
    @Bind(R.id.tv_no_request)
    TextView tvNoRequest;
    @Bind(R.id.request_loading)
    LoadingView requestLoading;
    private List<LeaveRequestBean.ResultBean> resultBeanList;
    private LeaveRequestAdapter leaveRequestAdapter;
    private PrgDialog prgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_leave);
        ButterKnife.bind(this);
        context = RequestLeave.this;
        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        //String account = SharePreferInfoUtils.readLoginName(context);
        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
        String studentId = resultBean.getStudentId();
        String token = resultBean.getToken();
        //prgDialog = new PrgDialog(context);
        requestLoading.setVisibility(View.VISIBLE);
        HttpUtil.doHttp(HttpContent.LEAVE_REQUSET_URL + studentId + "&token=" + token, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                LeaveRequestBean leaveRequestBean = GsonTools.getBean(result, LeaveRequestBean.class);
                int status = leaveRequestBean.getStatus();
                if (status == 0) {
                    resultBeanList = leaveRequestBean.getResult();
                    leaveRequestAdapter = new LeaveRequestAdapter(context, resultBeanList);
                    lvMyRequestleave.setAdapter(leaveRequestAdapter);
                    leaveRequestAdapter.notifyDataSetChanged();
                    //prgDialog.closeDialog();
                    requestLoading.setVisibility(View.GONE);
                    if (resultBeanList.size() == 0 || lvMyRequestleave == null) {
                        tvNoRequest.setVisibility(View.VISIBLE);
                    }
                    leaveRequestAdapter.notifyDataSetChanged();
                } else {
                    //prgDialog.closeDialog();
                    requestLoading.setVisibility(View.GONE);
                    String message = leaveRequestBean.getMessage();
                    if ("Invalid Token".equals(message)) {
                        T.ShowToast(context, "账号在其他地方登录");
                    } else {
                        T.ShowToast(context, message);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
    }

    private void initView() {
        toobarRequestLeave.setTitle("");
        setSupportActionBar(toobarRequestLeave);
        toobarRequestLeave.setNavigationIcon(R.mipmap.icon_reg_back);
        toobarRequestLeave.setTitleTextColor(getResources().getColor(R.color.white));
        toobarRequestLeave.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvNoRequest.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_request_leave, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_request) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                Intent intent = new Intent(RequestLeave.this, LolAddRequestActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(RequestLeave.this, AddRequestActivity.class);
                startActivity(intent);
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
