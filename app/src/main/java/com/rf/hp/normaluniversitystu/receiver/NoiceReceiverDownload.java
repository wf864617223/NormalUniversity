package com.rf.hp.normaluniversitystu.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.activity.noice.NoiceInfoActivity;
import com.rf.hp.normaluniversitystu.bean.NoiceBean;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;

/**
 * Created by hp on 2016/7/28.
 */
public class NoiceReceiverDownload extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NoiceBean.ResultBean resultBean = SharePreferInfoUtils.readNoticeInfo(context);
        String noticeTitle = resultBean.getNoticeTitle();
        String noticeContent = resultBean.getNoticeContent();
        String noticeDate = resultBean.getNoticeDate();

        //设置通知内容并在onReceive()这个函数执行时开启
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        //通过builder对象设置属性值
        builder.setSmallIcon(R.mipmap.ic_launcher)//设置通知的小图标
                .setContentTitle("文件已经下载完成")//设置通知的标题
                .setContentText("点击查看下载文件")//设置通知的内容
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(), R.mipmap.ic_launcher))
                .setDefaults(Notification.DEFAULT_SOUND //设置通知的通知方式,声音
                        | Notification.DEFAULT_VIBRATE //震动
                        | Notification.DEFAULT_LIGHTS);//光

        intent = new Intent(context, NoiceInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("noticeTitle",noticeTitle);
        bundle.putString("noticeDate",noticeDate);
        bundle.putString("noticeContent",noticeContent);
        intent.putExtras(bundle);
        //创建一个延迟意图（点击通知后要跳转的意图）
        PendingIntent pendingIntent = PendingIntent.
                getActivity(context, 100, intent, PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(pendingIntent);

        //发送通知：通知的id   通过builder构造的Notification对象
        manager.notify(1, builder.build());

    }
}
