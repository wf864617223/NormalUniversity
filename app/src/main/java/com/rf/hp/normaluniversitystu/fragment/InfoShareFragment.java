package com.rf.hp.normaluniversitystu.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.activity.learn.NoOpenStyle;
import com.rf.hp.normaluniversitystu.activity.learn.SearchShareActivity;
import com.rf.hp.normaluniversitystu.adapter.InfoShareAdapter;
import com.rf.hp.normaluniversitystu.bean.ShareDataBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.T;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 资料共享的Fragment
 * Created by hp on 2016/7/18.
 */
public class InfoShareFragment extends Fragment {
    @Bind(R.id.lv_learn_share)
    ListView lvLearnShare;
    @Bind(R.id.lv_srarch)
    ImageView lvSrarch;

    Context context;
    @Bind(R.id.tv_getsharefiled)
    TextView tvGetsharefiled;
    private List<ShareDataBean.ResultBean> result1 = new ArrayList<>();
    private InfoShareAdapter adapter;
    private String path = "/sdcard/myHead/";
    private ProgressDialog progressDialog;
    private String fileName;
    private NotificationManager manager;
    //private PrgDialog prgDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn_share, null);
        ButterKnife.bind(this, view);
        context = getContext();
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        initView();
        initData();
        return view;
    }

    private void initData() {
        lvLearnShare.setVisibility(View.VISIBLE);
        HttpUtil.doHttp(HttpContent.GET_SHARE_DATA + "", null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                ShareDataBean shareDataBean = GsonTools.getBean(result, ShareDataBean.class);
                int status = shareDataBean.getStatus();
                if (status == 0) {
                    result1 = shareDataBean.getResult();
                    adapter = new InfoShareAdapter(result1, context);
                    lvLearnShare.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    String message = shareDataBean.getMessage();
                    tvGetsharefiled.setVisibility(View.VISIBLE);
                    lvLearnShare.setVisibility(View.GONE);
                    //T.ShowToast(context,message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                /*System.out.println(ex.getMessage());
                tvGetsharefiled.setVisibility(View.VISIBLE);
                lvLearnShare.setVisibility(View.GONE);*/
            }
        });
        lvLearnShare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String fName = result1.get(position).getDatumPath();
                //URLEncoder.encode(searchContent, "UTF-8");
                String[] splitName = fName.split("/");
                fileName = splitName[splitName.length - 1];
                String encode = "";
                try {
                    encode = URLEncoder.encode(fileName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final String downUrl = HttpContent.SERVER_ADDRESS + "/datum/" + encode;

                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();// 创建文件夹
                }
                //String fileName = result1.get(position).getDatumName();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("是否要下载此资料到SD卡中？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                downFile(fName, path, fileName);
                            }
                        });
                builder.show();

                //T.ShowToast(context,downUrl);
            }
        });
    }

    private void downFile(String downUrl, final String path, String fileName) {
        progressDialog = new ProgressDialog(context);
        //prgDialog = new PrgDialog(context);
        RequestParams requestParams = new RequestParams(downUrl);

        requestParams.setSaveFilePath(path + fileName);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {

                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("亲，努力下载中...");
                progressDialog.show();
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) current);
            }

            @Override
            public void onSuccess(File result) {

                Toast.makeText(context, "文件已下载到/sdcard/myHead/里面", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                setReminder();
                //prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(context, "下载失败，请检查网络和SD卡" + ex.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                //prgDialog.closeDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private Intent getOpenStyle() {
        Intent intent;
        //String[] split = fileName.split(".");
        //String end = split[1];
        String end = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (end.equals("ppt")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path + "/" + fileName));
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            return intent;
        } else if (end.equals("xls")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path + "/" + fileName));
            intent.setDataAndType(uri, "application/vnd.ms-excel");
            return intent;
        } else if (end.equals("doc") || end.equals("docx")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path + "/" + fileName));
            intent.setDataAndType(uri, "application/msword");
            return intent;
        } else if (end.equals("txt")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri2 = Uri.fromFile(new File(path + "/" + fileName));
            intent.setDataAndType(uri2, "text/plain");
        } else if (end.equals("pdf")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path + "/" + fileName));
            intent.setDataAndType(uri, "application/pdf");
            return intent;
        } else if (end.equals("zip")) {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path + "/" + fileName));
            intent.setDataAndType(uri, "application/x-zip-compressed");
        }
        intent = new Intent(context, NoOpenStyle.class);
        return intent;
    }

    private void setReminder() {
        Intent openStyle = getOpenStyle();
        //return intent;
        PendingIntent pendingIntent = PendingIntent.
                getActivity(context, 100, openStyle, PendingIntent.FLAG_ONE_SHOT);
        Notification notification = new Notification.Builder(context).setSmallIcon(R.drawable.logo).setContentTitle("文件下载已完成")
                .setContentText("点击查看")//设置通知的内容
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setDefaults(Notification.DEFAULT_SOUND //设置通知的通知方式,声音
                        | Notification.DEFAULT_VIBRATE //震动
                        | Notification.DEFAULT_LIGHTS)//光
                .setContentIntent(pendingIntent)
                .getNotification();


        notification.flags |= Notification.FLAG_AUTO_CANCEL;


        //builder.setContentIntent(pendingIntent);

        //发送通知：通知的id   通过builder构造的Notification对象
        manager.notify(1, notification);
    }

    private void initView() {
        lvSrarch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchShareActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
