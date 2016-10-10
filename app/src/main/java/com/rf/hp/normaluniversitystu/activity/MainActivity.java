package com.rf.hp.normaluniversitystu.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;


import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.fragment.KaoqinFragment;
import com.rf.hp.normaluniversitystu.fragment.LearnFragment;
import com.rf.hp.normaluniversitystu.fragment.MineFragment;
import com.rf.hp.normaluniversitystu.fragment.NoticeFragment;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.MarsQuestion;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.ViewIndicator;

import java.io.File;
import java.util.HashMap;

/**
 * 主界面--包含四个Fragment
 */
public class MainActivity extends AppCompatActivity implements KaoqinFragment.OnFragmentInteractionListener,LearnFragment.OnFragmentInteractionListener,
        NoticeFragment.OnFragmentInteractionListener,MineFragment.OnFragmentInteractionListener{

    private static int defaultWhich = 0;

    private static String TAG = MainActivity.class.getSimpleName();
    private long exitTime = 0;
    Context context;
    private Fragment kaoqinFrag,learnFrag,noiceFrag,mineFrag;
    private ViewIndicator ma_viewindicator;
    private int positinId;//关闭应用时的Fragment ID 防止UI重叠
    private String temp;
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        // 从savedInstanceState中恢复数据, 如果没有数据需要恢复savedInstanceState为null
        if (savedInstanceState != null) {
            positinId = savedInstanceState.getInt("positinId");
            showFragment(positinId);
        }
        initView();
        initBottomMenu();
        putToken();
        MarsQuestion.verifyStoragePermissions(MainActivity.this);
        mkAppDirs();
    }

    private void mkAppDirs() {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            System.out.println("SD卡不可用");
            T.ShowToast(context,"SD卡不可用");
        }else{
            //T.ShowToast(context,"SD卡可以用");
            File sdcardFile = Environment.getExternalStorageDirectory();
            File userFile = new File(sdcardFile.toString() + "/myHead/");
            if(!userFile.exists()){
                boolean mkdirs = userFile.mkdirs();
                if(!mkdirs){
                    T.ShowToast(context,"创建存储文件夹失败，你将不可以下载资料和修改头像");
                }
            }

        }
    }

    private void putToken() {
        //PushManager.isConnected(context);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,"ws1mNhewLGwi5NNHyHuGW3TS");
        boolean pushEnabled = PushManager.isPushEnabled(context);
        if(!pushEnabled){
            PushManager.resumeWork(context);
        }
        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
        String studentId = resultBean.getStudentId();
        String token = resultBean.getToken();
        String channId = SharePreferInfoUtils.readChannId(context);
        HashMap<String,String> params = new HashMap<>();
        params.put("studentId",studentId);
        params.put("deviceName","安卓");
        params.put("pushToken",channId);
        params.put("token",token);
        HttpUtil.doHttp(HttpContent.PUT_TOKEN, params, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                //T.ShowToast(context,result);
                System.out.println("==同步推送==》"+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //T.ShowToast(context,ex.getMessage());
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //记录当前的position
        outState.putInt("positinId", positinId);
    }

    private void initView() {
        ma_viewindicator = (ViewIndicator) findViewById(R.id.viewindicator_ma);
        kaoqinFrag = new KaoqinFragment();
        learnFrag = new LearnFragment();
        noiceFrag = new NoticeFragment();
        mineFrag = new MineFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_frame_layout, kaoqinFrag);
        fragmentTransaction.add(R.id.main_frame_layout, learnFrag);
        fragmentTransaction.add(R.id.main_frame_layout, noiceFrag);
        fragmentTransaction.add(R.id.main_frame_layout, mineFrag);
        fragmentTransaction.commit();
        showFragment(defaultWhich);
    }

    private void showFragment(int which) {
        positinId = which;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(kaoqinFrag);
        fragmentTransaction.hide(learnFrag);
        fragmentTransaction.hide(noiceFrag);
        fragmentTransaction.hide(mineFrag);
        switch (which) {
            case 0:
                fragmentTransaction.show(kaoqinFrag);
                fragmentTransaction.hide(learnFrag);
                fragmentTransaction.hide(noiceFrag);
                fragmentTransaction.hide(mineFrag);
                break;
            case 1:
                fragmentTransaction.show(learnFrag);
                fragmentTransaction.hide(kaoqinFrag);
                fragmentTransaction.hide(noiceFrag);
                fragmentTransaction.hide(mineFrag);
                break;
            case 2:
                fragmentTransaction.show(noiceFrag);
                fragmentTransaction.hide(kaoqinFrag);
                fragmentTransaction.hide(learnFrag);
                fragmentTransaction.hide(mineFrag);
                break;
            case 3:
                fragmentTransaction.show(mineFrag);
                fragmentTransaction.hide(kaoqinFrag);
                fragmentTransaction.hide(learnFrag);
                fragmentTransaction.hide(noiceFrag);
                break;
        }
        fragmentTransaction.commit();
    }

    private void initBottomMenu() {
        ViewIndicator.setIndicator(defaultWhich);
        ma_viewindicator.setOnIndicateListener(new ViewIndicator.OnIndicateListener() {
            @Override
            public void onIndicate(View v, int which) {
                showFragment(which);
            }
        });
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                T.ShowToast(MainActivity.this,"再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        /*System.out.println("Page01 -->onKeyDown: keyCode: " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            System.out.println("HOME has been pressed yet ...");
            // android.os.Process.killProcess(android.os.Process.myPid());
            *//*if((System.currentTimeMillis()-exitTime) > 2000){
                T.ShowToast(MainActivity.this,"再按一次退出");
                exitTime = System.currentTimeMillis();
            } else {*//*
                finish();
            //}
            return true;
        }*/
        return false;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        positinId = savedInstanceState.getInt("positinId");
        showFragment(positinId);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
