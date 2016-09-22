package com.rf.hp.normaluniversitystu.receiver;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.rf.hp.normaluniversitystu.activity.MainActivity;
import com.rf.hp.normaluniversitystu.activity.mine.RequestLeave;
import com.rf.hp.normaluniversitystu.activity.noice.NoiceInfoActivity;
import com.rf.hp.normaluniversitystu.bean.PushBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hp on 2016/9/8.
 */
public class PushTestReceiver extends PushMessageReceiver {
    /**
     * TAG to Log
     */
    public static final String TAG = PushTestReceiver.class.getSimpleName();
    private String noticeTitle;
    private String noticeDate;
    private String noticeContent;

    /**
     * 判断应用是否已经启动
     *
     * @param context     一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
    private void updateContent(Context context, String content) {
        Log.d(TAG, "updateContent");
        String logText = "" + Utils.logStringCache;

        if (!logText.equals("")) {
            logText += "\n";
        }

        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss");
        logText += sDateFormat.format(new Date()) + ": ";
        logText += content;

        Utils.logStringCache = logText;

        Intent intent = new Intent();
        intent.setClass(context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }
    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid=" + appid + " userId=" + userId + " channelId=" + channelId + " requestId=" + requestId;
        Log.d(TAG, "++==>"+responseString);
        System.out.println("++==>"+responseString);
        if (errorCode == 0) {
            // 绑定成功
            Log.d(TAG, "绑定成功");
            SharePreferInfoUtils.saveChannelId(context,channelId);
        }
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
       // updateContent(context, responseString);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String message, String contentString) {

        System.out.println("onMessage---- message==" + message
                + " contentString==" + contentString);
    }

    /**
     *
     * @param context
     * @param title 通知标题
     * @param description 通知内容
     * @param customContentString k,v键值对
     */
    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {

        /*String[] split = s2.replace("{", "").replace("}", "").split(":");
        T.ShowToast(context,"key="+split[0]+"value="+split[1]);
        System.out.println("s="+s+"s1="+s1+"s2="+s2);*/
        System.out.println("customContentString=>" + customContentString);
        String type = getMessageType(customContentString);
        if(isAppAlive(context,"com.rf.hp.normaluniversitystu")){

            //如果存活的话，就直接启动DetailActivity，但要考虑一种情况，就是app的进程虽然仍然在
            //但Task栈已经空了，比如用户点击Back键退出应用，但进程还没有被系统回收，如果直接启动
            //DetailActivity,再按Back键就不会返回MainActivity了。所以在启动
            //DetailActivity前，要先启动MainActivity。
            Log.i("NotificationReceiver", "the app process is alive");
            System.out.println("type=>" + type);
            if ("通知".equals(type)) {//跳转到就诊凭证
                context.startActivities(makeNoiceIntentStack(context,customContentString));
                System.out.println("提示=>" + customContentString);
            } else if ("批假".equals(type)) {//跳转到消息列表
                context.startActivities(makeRequestIntentStack(context));
                System.out.println("系统=>" + customContentString);
            }
        }else{
            //SplashActivity传入MainActivity，此时app的初始化已经完成，在MainActivity中就可以根据传入
            // 参数跳转到DetailActivity中去了
            Log.i("NotificationReceiver", "the app process is dead");
            Intent launchIntent = context.getPackageManager().
                    getLaunchIntentForPackage("com.rf.hp.normaluniversitystu");
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            if ("通知".equals(type)) {//跳转到通知详情
                Bundle bundle = new Bundle();
                bundle.putString("noticeTitle", noticeTitle);
                bundle.putString("noticeDate", noticeDate);
                bundle.putString("noticeContent", noticeContent + "");
                launchIntent.putExtras(bundle);
                //launchIntent.putExtra("onNotification", "truetrue");
            } else if ("批假".equals(type)) {//跳转到请假列表
                //launchIntent.putExtra("onNotification", "true");
            }
            context.startActivity(launchIntent);
        }
    }

    @Override
    public void onNotificationArrived(Context context, String title, String description, String contentString) {

        System.out.println("onNotificationArrived----- title==" + title
                + " description==" + description + " contentString=="
                + contentString);
        PushBean pushBean = GsonTools.getBean(contentString,PushBean.class);
        noticeTitle = pushBean.getNoticeTitle();
        noticeDate = pushBean.getNoticeDate();
        noticeContent = pushBean.getNoticeContent();

    }

    /**
     * 跳转到通知详情
     *
     * @param context
     * @return
     */
    private Intent[] makeNoiceIntentStack(Context context,String customContentString) {
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context, MainActivity.class));
        intents[0].setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PushBean pushBean = GsonTools.getBean(customContentString,PushBean.class);
        noticeTitle = pushBean.getNoticeTitle();
        noticeDate = pushBean.getNoticeDate();
        noticeContent = pushBean.getNoticeContent();
        intents[1] = new Intent(context, NoiceInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("noticeTitle", noticeTitle);
        bundle.putString("noticeDate", noticeDate);
        bundle.putString("noticeContent", noticeContent + "");
        intents[1].putExtras(bundle);
        intents[1].toUri(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intents;
    }
    /**
     * 跳转到请假列表
     *
     * @param context
     * @return
     */
    private Intent[] makeRequestIntentStack(Context context) {
        Intent[] intents = new Intent[2];
        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context, MainActivity.class));
        intents[0].setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        intents[1] = new Intent(context, RequestLeave.class);
        intents[1].toUri(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intents;
    }
    /**
     * 判断消息类别做时间分发
     *
     * @param customContentString
     */
    public String getMessageType(String customContentString) {
        String type = "批假";
        if (!TextUtils.isEmpty(customContentString)) {
            PushBean pushBean = GsonTools.getBean(customContentString,PushBean.class);
            type = pushBean.getType();
        }
        return type;
    }
}
