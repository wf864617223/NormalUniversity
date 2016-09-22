package com.rf.hp.normaluniversitystu.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.rf.hp.normaluniversitystu.activity.LoginActivity;
import com.rf.hp.normaluniversitystu.activity.MainActivity;
import com.rf.hp.normaluniversitystu.activity.kaoqin.AdInfoActivity;

/**
 * 该类可以用来跳转到某个Activity
 * Created by hp on 2016/7/19.
 */
public class IntentManager {
    private static volatile IntentManager intentManager;
    private Context context;

    public IntentManager(Context context) {
        this.context = context.getApplicationContext();
    }
    public static IntentManager getInstance(Context context) {
        if (intentManager == null) {
            synchronized (IntentManager.class) {
                if (intentManager == null) {
                    return new IntentManager(context);
                }
            }
        }
        return intentManager;
    }

    public void toMainActivity(Bundle bundle){
        Intent intent = new Intent(context, MainActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);

    }
    public void toLoginActivity(Bundle bundle){
        Intent logoutIntent = new Intent(context,
                LoginActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(logoutIntent);
    }
    public void toAdInfoActivity(Bundle bundle){
        Intent intent = new Intent(context, AdInfoActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        if (bundle != null && bundle.size() > 0) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
