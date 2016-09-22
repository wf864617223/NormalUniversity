package com.rf.hp.normaluniversitystu.fragment;

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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.activity.LoginActivity;
import com.rf.hp.normaluniversitystu.activity.mine.AboutUsActivity;
import com.rf.hp.normaluniversitystu.activity.mine.ChangePwdActivity;
import com.rf.hp.normaluniversitystu.activity.mine.MineInfoActivity;
import com.rf.hp.normaluniversitystu.activity.mine.MyadviceActivity;
import com.rf.hp.normaluniversitystu.activity.mine.RequestLeave;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.utils.GlideTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的 界面
 * Created by hp on 2016/7/15.
 */
@ContentView(R.layout.frag_mine)
public class MineFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.relative_changePhone)
    RelativeLayout relativeChangePhone;
    @Bind(R.id.my_request_leave)
    RelativeLayout myRequestLeave;
    @Bind(R.id.about_us)
    RelativeLayout aboutUs;
    @Bind(R.id.btn_loginOut)
    Button btnLoginOut;
    @Bind(R.id.mine_img_header)
    ImageView mineImgHeader;
    @Bind(R.id.tv_userName)
    TextView tvUserName;
    @Bind(R.id.tv_stu_no)
    TextView tvStuNo;
    @Bind(R.id.rl_my_advice)
    RelativeLayout rlMyAdvice;
    /*@Bind(R.id.ll_user_head)
    LinearLayout llUserHead;*/
    @ViewInject(R.id.ic_user_head)
    private View includehead;
    private OnFragmentInteractionListener mListener;
    Context context;
    private Bitmap head;//头像Bitmap
    private static String path = "/sdcard/myHead/";//sd路径
    private String loginName;

    public MineFragment() {

    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment mineFragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        mineFragment.setArguments(args);
        return mineFragment;

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        context = getContext();
        loginName = SharePreferInfoUtils.readLoginName(context);
        initView();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        File file = new File(path);
        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
        Bitmap bt = BitmapFactory.decodeFile(path+loginName+".jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            mineImgHeader.setImageDrawable(drawable);
        } else if(!file.exists()){
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
            file.mkdirs();// 创建文件夹
            String studentPicture = resultBean.getStudentPicture();
            String url = HttpContent.SERVER_ADDRESS + studentPicture;
            System.out.println("url=img=>"+url);
            //Glide.with(context).load(url).error(R.drawable.nopicture).into(mineImgHeader);
            File temp = new File(path);
            GlideTools.downLoadImg(temp,context,url,mineImgHeader);
        }else{
            String studentPicture = resultBean.getStudentPicture();
            String url = HttpContent.SERVER_ADDRESS + studentPicture;
            System.out.println("url=img=>"+url);
            //Glide.with(context).load(url).error(R.drawable.nopicture).into(mineImgHeader);
            File temp = new File(path);
            GlideTools.downLoadImg(temp,context,url,mineImgHeader);
        }
    }

    private void initData() {
        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
        tvStuNo.setText(resultBean.getStudentAccount());
        tvUserName.setText(resultBean.getStudentName());
        String studentSex = resultBean.getStudentSex();
        if ("男".equals(studentSex)) {
            mineImgHeader.setImageResource(R.mipmap.man);
        } else if ("女".equals(studentSex)) {
            mineImgHeader.setImageResource(R.mipmap.woman);
        }

        Bitmap bt = BitmapFactory.decodeFile(path + SharePreferInfoUtils.readLoginName(context)+".jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            mineImgHeader.setImageDrawable(drawable);
        } else {
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
            String studentPicture = resultBean.getStudentPicture();
            String url = HttpContent.SERVER_ADDRESS + studentPicture;
            System.out.println("url=img=>"+url);
            //Glide.with(context).load(url).into(mineImgHeader);
            File temp = new File(path);
            GlideTools.downLoadImg(temp,context,url,mineImgHeader);
        }
    }

    private void initView() {
        //个人信息
        includehead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MineInfoActivity.class);
                startActivity(intent);
            }
        });
        //修改密码
        relativeChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePwdActivity.class);
                startActivity(intent);
            }
        });
        rlMyAdvice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyadviceActivity.class);
                startActivity(intent);
            }
        });
        //请假申请列表
        myRequestLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RequestLeave.class);
                startActivity(intent);
            }
        });
        //关于我们
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //T.ShowToast(context, "暂未开放");
                //Toast.makeText(context, "qwerty", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AboutUsActivity.class);
                startActivity(intent);
            }
        });
        //退出登录
        btnLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext()).setTitle("确认").setMessage("确定要退出登录吗？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Intent intent = new Intent(getContext(), LoginActivity.class);
//                                startActivity(intent);
//                                getActivity().finish();
                                SharePreferInfoUtils.clearLoginPwd(context);
                                Intent logoutIntent = new Intent(getContext(),
                                        LoginActivity.class);
                                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(logoutIntent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("否", null).show();

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
