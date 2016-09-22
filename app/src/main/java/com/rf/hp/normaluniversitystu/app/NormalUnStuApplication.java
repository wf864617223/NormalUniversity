package com.rf.hp.normaluniversitystu.app;

import android.app.Activity;
import android.app.Application;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hp on 2016/7/15.
 */
public class NormalUnStuApplication extends Application {

    //回到首页
    private List<Activity> mList = new LinkedList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,"ws1mNhewLGwi5NNHyHuGW3TS");
        x.Ext.init(this);
        x.Ext.setDebug(false);
        verifyStoragePermissions();
    }

    private void verifyStoragePermissions() {

    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
