package com.rf.hp.normaluniversitystu.activity.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.GlideTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.MarsQuestion;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MineInfoActivity extends AppCompatActivity {

    @Bind(R.id.toobar_user_info)
    Toolbar toobarUserInfo;
    Context context;
    @Bind(R.id.iv_userHeader)
    ImageView ivUserHeader;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.tv_userNo)
    TextView tvUserNo;
    @Bind(R.id.tv_userPhone)
    TextView tvUserPhone;
    @Bind(R.id.tv_userDept)
    TextView tvUserDept;
    @Bind(R.id.tv_user_class)
    TextView tvUserClass;
    @Bind(R.id.rl_change_head)
    RelativeLayout rlChangeHead;
    @Bind(R.id.ll_last_menses)
    LinearLayout llLastMenses;
    @Bind(R.id.ll_huaiyun_time)
    LinearLayout llHuaiyunTime;

    private Bitmap head;//头像Bitmap
    private static String path="/sdcard/myHead/";//sd路径
    private Bitmap picToView;
    private PrgDialog prgDialog;
    private String loginName;
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_info);
        ButterKnife.bind(this);
        MarsQuestion.verifyStoragePermissions(this);
        context = MineInfoActivity.this;
        tempFile = new File(Environment.getExternalStorageDirectory(),
                getPhotoFileName());
        loginName = SharePreferInfoUtils.readLoginName(context);
        initView();
        initData();
    }

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HH");
        return dateFormat.format(date) + ".jpg";
    }
    private void initData() {
        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
        tvUserName.setText(resultBean.getStudentName());
        tvUserClass.setText(resultBean.getStudentClass());
        tvUserDept.setText(resultBean.getStudentProfession());
        tvUserNo.setText(resultBean.getStudentAccount());
        tvUserPhone.setText(resultBean.getStudentTelphone());
        String studentSex = resultBean.getStudentSex();
        if ("男".equals(studentSex)) {
            ivUserHeader.setImageResource(R.mipmap.man);
        } else if ("女".equals(studentSex)) {
            ivUserHeader.setImageResource(R.mipmap.woman);
        }
        Bitmap bt = BitmapFactory.decodeFile(path + loginName+".jpg");//从Sd中找头像，转换成Bitmap
        if(bt!=null){
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            ivUserHeader.setImageDrawable(drawable);
        }else{
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
            String studentPicture = resultBean.getStudentPicture();
            String url = HttpContent.SERVER_ADDRESS+studentPicture;
            //Glide.with(context).load(url).into(ivUserHeader);
            System.out.println("url=img=>"+url);
            File temp = new File(path);
            GlideTools.downLoadImg(temp,context,url,ivUserHeader);
        }
        rlChangeHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*new AlertDialog.Builder(context).setTitle("修改头像").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"图库", "相机"}, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which == 0){*/
                                    Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                                    intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                    startActivityForResult(intent1, 1);
                                /*}else{
                                    //Uri imageUri = Uri.parse(/sdcard/temp.jpg);
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/" ,getPhotoFileName())));
                                    startActivityForResult(intent, 4);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", null).show();*/

            }
        });
    }

    /**
     * 调用系统的裁剪
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/"+loginName+".jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        setPicToView(head);//保存在SD卡中
                        upload(path + loginName+".jpg");//上传到服务器中
                        ivUserHeader.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            case 4:
                File picture = new File(Environment.getExternalStorageDirectory()+"/"+getPhotoFileName());
                cropPhoto(Uri.fromFile(picture));
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传服务器代码
     */
    private void upload(String url) {
        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
        String studentId = resultBean.getStudentId();
        String token = resultBean.getToken();
        final RequestParams params = new RequestParams(HttpContent.SETTING_HEAD_IMG);
        params.addBodyParameter("studentId",studentId);
        params.addBodyParameter("token",token);
        params.addBodyParameter("file",new File(url));
        params.setMultipart(true);
        prgDialog = new PrgDialog(context);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    System.out.println("==params==>"+params);
                    JSONObject jsonObject = new JSONObject(result);
                    int status = jsonObject.optInt("status");
                    prgDialog.closeDialog();
                    if(status == 0){
                        T.ShowToast(context,"头像上传成功");
                    }else{
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
                System.out.println("==params==>"+params);
                T.ShowToast(context,ex.getMessage());
                System.out.println("==>"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                prgDialog.closeDialog();
            }

            @Override
            public void onFinished() {
                prgDialog.closeDialog();
            }
        });

    }

    private void initView() {
        toobarUserInfo.setTitle("");
        setSupportActionBar(toobarUserInfo);
        toobarUserInfo.setNavigationIcon(R.mipmap.icon_reg_back);
        toobarUserInfo.setTitleTextColor(getResources().getColor(R.color.white));
        toobarUserInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setPicToView(Bitmap picToView) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            System.out.println("SD卡不可用");
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName =path + loginName+".jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            picToView.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
