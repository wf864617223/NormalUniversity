package com.rf.hp.normaluniversitystu.activity.learn;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.adapter.InfoShareAdapter;
import com.rf.hp.normaluniversitystu.bean.ShareDataBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

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
 * 搜索资料界面
 */
public class SearchShareActivity extends AppCompatActivity {

    @Bind(R.id.toobar_add_request)
    Toolbar toobarAddRequest;
    @Bind(R.id.et_search_data)
    EditText etSearchData;
    @Bind(R.id.btn_start_search)
    Button btnStartSearch;
    @Bind(R.id.lv_search_view)
    ListView lvSearchView;

    Context context;
    @Bind(R.id.tv_search_nodata)
    TextView tvSearchNodata;
    private List<ShareDataBean.ResultBean> result1 = new ArrayList<>();
    private InfoShareAdapter adapter;
    private PrgDialog prgDialog;
    private String path = "/sdcard/myHead/";
    private ProgressDialog progressDialog;
    private NotificationManager manager;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_share);
        ButterKnife.bind(this);
        context = SearchShareActivity.this;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        initView();
        initData();
    }

    private void initData() {
        etSearchData.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String searchData = etSearchData.getText().toString();
                if(actionId== EditorInfo.IME_ACTION_SEARCH
                        ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)){
                    if (!TextUtils.isEmpty(searchData)) {
                        etSearchData.setText("");
                        prgDialog = new PrgDialog(context);
                        String encode = "";
                        try {
                            encode = URLEncoder.encode(searchData, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        HttpUtil.doHttp(HttpContent.GET_SHARE_DATA + encode, null, new HttpUtil.IHttpResult() {
                            @Override
                            public void onSuccess(String result) {
                                ShareDataBean shareDataBean = GsonTools.getBean(result, ShareDataBean.class);
                                int status = shareDataBean.getStatus();
                                prgDialog.closeDialog();
                                if (status == 0) {
                                    result1 = shareDataBean.getResult();
                                    adapter = new InfoShareAdapter(result1, context);
                                    lvSearchView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    if (result1.size() == 0 || lvSearchView == null) {
                                        tvSearchNodata.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    String message = shareDataBean.getMessage();
                                    T.ShowToast(context, message);
                                }
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                prgDialog.closeDialog();
                                T.ShowToast(context,"服务器连接失败");
                            }
                        });
                    return true;
                    }else{
                        T.ShowToast(context, "未输入任何内容");
                    }
                }
                return false;
            }
        });
        btnStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSearchNodata.setVisibility(View.GONE);
                String searchData = etSearchData.getText().toString();
                if (TextUtils.isEmpty(searchData)) {
                    T.ShowToast(context, "未输入任何内容");
                    return;
                }
                etSearchData.setText("");
                prgDialog = new PrgDialog(context);
                //URLEncoder.encode(searchContent, "UTF-8");

                String encode = "";
                try {
                    encode = URLEncoder.encode(searchData, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                HttpUtil.doHttp(HttpContent.GET_SHARE_DATA + encode, null, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {
                        ShareDataBean shareDataBean = GsonTools.getBean(result, ShareDataBean.class);
                        int status = shareDataBean.getStatus();
                        prgDialog.closeDialog();
                        if (status == 0) {
                            result1 = shareDataBean.getResult();
                            adapter = new InfoShareAdapter(result1, context);
                            lvSearchView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (result1.size() == 0 || lvSearchView == null) {
                                tvSearchNodata.setVisibility(View.VISIBLE);
                            }
                        } else {
                            String message = shareDataBean.getMessage();
                            T.ShowToast(context, message);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        prgDialog.closeDialog();
                        T.ShowToast(context,"服务器连接失败");
                    }
                });
            }
        });
        /**
         * 点击每个item的监听
         */
        lvSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                //final String downUrl = HttpContent.SERVER_ADDRESS+"/datum/"+encode;
                File file = new File(path);
                if(!file.exists()){
                    file.mkdirs();// 创建文件夹
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("是否要下载此资料到手机中？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                downFile(fName,path, fileName);
                            }
                        });
                builder.show();
                //String fileName = result1.get(position).getDatumName();
                //downFile(downUrl,path,fileName);
                //T.ShowToast(context,downUrl);
            }
        });
    }

    /**
     * 下载文件类
     * @param downUrl 下载地址
     * @param path 存储路径
     * @param fileName 文件名
     */
    private void downFile(String downUrl, String path, String fileName) {
        progressDialog = new ProgressDialog(context);
        //prgDialog = new PrgDialog(context);
        RequestParams requestParams = new RequestParams(downUrl);

        requestParams.setSaveFilePath(path+fileName);
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

                Toast.makeText(context, "文件已下载到文件夹myHead里面", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                setReminder();
                //prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(context, "下载失败，请检查网络和SD卡"+ex.getMessage(), Toast.LENGTH_SHORT).show();
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

    /**
     * 发送通知
     */
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

    /**
     * 得到文件后缀名 判断打开类型
     * @return 返回可以打开的app的Intent
     */
    private Intent getOpenStyle() {
        Intent intent;
        //String[] split = fileName.split(".");
        //String end = split[1];
        String end = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(end.equals("ppt")){
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path+"/"+fileName));
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            return intent;
        }else if(end.equals("xls")){
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path+"/"+fileName));
            intent.setDataAndType(uri, "application/vnd.ms-excel");
            return intent;
        }else if(end.equals("doc")||end.equals("docx")){
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path+"/"+fileName));
            intent.setDataAndType(uri, "application/msword");
            return intent;
        }else if(end.equals("txt")){
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri2 = Uri.fromFile(new File(path+"/"+fileName));
            intent.setDataAndType(uri2, "text/plain");
        }else if(end.equals("pdf")){
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path+"/"+fileName));
            intent.setDataAndType(uri, "application/pdf");
            return intent;
        }else if(end.equals("zip")){
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(path+"/"+fileName));
            intent.setDataAndType(uri, "application/x-zip-compressed");
        }
        intent = new Intent(context, NoOpenStyle.class);
        return intent;
    }

    private void initView() {
        toobarAddRequest.setTitle("");
        setSupportActionBar(toobarAddRequest);
        toobarAddRequest.setNavigationIcon(R.mipmap.icon_reg_back);
        toobarAddRequest.setTitleTextColor(getResources().getColor(R.color.white));
        toobarAddRequest.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
