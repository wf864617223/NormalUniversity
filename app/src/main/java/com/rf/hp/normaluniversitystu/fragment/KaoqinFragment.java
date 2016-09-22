package com.rf.hp.normaluniversitystu.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.adapter.MyPagerAdapter;
import com.rf.hp.normaluniversitystu.adapter.ProjectAdapter;
import com.rf.hp.normaluniversitystu.bean.HeadData;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.bean.ProjectBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.LoadingView;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 考勤界面
 * Created by hp on 2016/7/15.
 */
@ContentView(R.layout.frag_kaoqin)
public class KaoqinFragment extends Fragment implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.ll_user_info)
    LinearLayout llUserInfo;
    @Bind(R.id.vp_head)
    ViewPager vpHead;
    @Bind(R.id.btn_datajian)
    ImageView btnDatajian;
    @Bind(R.id.tv_data)
    TextView tvData;
    @Bind(R.id.btn_data_add)
    ImageView btnDataAdd;
    @Bind(R.id.rb_img1)
    RadioButton rbImg1;
    @Bind(R.id.rb_img2)
    RadioButton rbImg2;
    /*@Bind(R.id.rb_img3)
    RadioButton rbImg3;
    @Bind(R.id.rb_img4)
    RadioButton rbImg4;*/
    @Bind(R.id.rg_img_group)
    RadioGroup rgImgGroup;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_class_info)
    TextView tvUserClassInfo;
    @Bind(R.id.tv_dept_info)
    TextView tvDeptInfo;
    @Bind(R.id.lv_project)
    ListView lvProject;
    @ViewInject(R.id.tv_kaoqin_noclass)
    TextView tvKaoqinNoclass;
    @ViewInject(R.id.project_loading)
    LoadingView projectLoading;
    private OnFragmentInteractionListener mListener;
    private MyPagerAdapter pagerAdapter;
    //private List<HeadData.ResultBean.ListBean> list = new ArrayList<>();
    private String today;
    private String yesterDay;
    private String tomorrow;
    private int selected;
    private Date curDate;
    private SimpleDateFormat formatter;
    private PrgDialog prgDialog;
    private LoginBean.ResultBean resultBean;
    private List<ProjectBean.ResultBean> prolist = new ArrayList<>();
    private ProjectAdapter projectAdapter;
    private List<HeadData.ResultBean> list = new ArrayList<>();
    Context context;

    public KaoqinFragment() {

    }

    public static KaoqinFragment newInstance(String param1, String param2) {
        KaoqinFragment kaoqinFragment = new KaoqinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        kaoqinFragment.setArguments(args);
        return kaoqinFragment;
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        initView();
        initData();
        //initClass();
        return view;
    }

    private void initData() {
        resultBean = SharePreferInfoUtils.readUserInfo(getContext());
        String studentId = resultBean.getStudentId();
        String token = resultBean.getToken();
        tvUserName.setText(resultBean.getStudentName());
        tvDeptInfo.setText(resultBean.getStudentProfession());
        tvUserClassInfo.setText(resultBean.getStudentClass());
        //顶部动画
        HttpUtil.doHttp(HttpContent.GET_AD_URL + studentId + "&token=" + token, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                HeadData headData = GsonTools.getBean(result, HeadData.class);
                int status = headData.getStatus();
                if (status == 0) {
                    list = headData.getResult();
                    //list = result1.getList();
                    System.out.println("==>" + list);
                    //Glide.with(getContext()).load(pic).placeholder(R.drawable.nopicture).into(imageView);
                    pagerAdapter = new MyPagerAdapter(list, context);
                    vpHead.setAdapter(pagerAdapter);

                } else {
                    String message = headData.getMessage();
                    System.out.println("==msg==>" + message);
                    if ("Invalid Token".equals(message)) {
                        T.ShowToast(context, "账号在其他地方登录");
                    } else {
                        //T.ShowToast(context, message);
                    }
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        initClass();
        //prgDialog.closeDialog();
    }

    public void initClass() {
        String studentId = resultBean.getStudentId();
        String token = resultBean.getToken();
        String time = tvData.getText().toString();
        if ("今天".equals(time)) {
            time = today;
        }
        tvKaoqinNoclass.setVisibility(View.GONE);
        //prgDialog = new PrgDialog(context);
        projectLoading.setVisibility(View.VISIBLE);
        HttpUtil.doHttp(HttpContent.GET_PROJECT_URL + "studentId=" + studentId + "&currDate=" + time + "&token=" + token, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                //prgDialog.closeDialog();
                projectLoading.setVisibility(View.GONE);
                ProjectBean projectBean = GsonTools.getBean(result, ProjectBean.class);
                int status = projectBean.getStatus();

                if (status == 0) {
                    //prgDialog.closeDialog();
                    prolist = projectBean.getResult();
                    projectAdapter = new ProjectAdapter(context, prolist);
                    lvProject.setAdapter(projectAdapter);
                    projectAdapter.notifyDataSetChanged();
                    if (prolist.size() == 0 || lvProject == null) {
                        tvKaoqinNoclass.setVisibility(View.VISIBLE);
                    }

                } else {
                    //prgDialog.closeDialog();
                    String message = projectBean.getMessage();
                    System.out.println("==msg==>" + message);
                    if ("Invalid Token".equals(message)) {
                        T.ShowToast(context, "账号在其他地方登录");
                    } else {
                        T.ShowToast(context, message);
                    }

                }
                //prgDialog.closeDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //prgDialog.closeDialog();
                //projectLoading.setVisibility(View.GONE);
                tvKaoqinNoclass.setVisibility(View.VISIBLE);
                tvKaoqinNoclass.setText("课程信息获取失败");
                //T.ShowToast(context, "课程信息获取失败");
            }
        });
        //prgDialog.closeDialog();
        //projectLoading.setVisibility(View.GONE);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            Calendar calendar = new GregorianCalendar();

            switch (message.what) {
                case 1:
                    /*data--;
                    Calendar calendar ;
                    String chooseTime = split[0] + "-" + split[1] + "-" + data;
                    if(today.equals(chooseTime)){
                        tvData.setText("今天");
                    }else{
                        tvData.setText(chooseTime);
                    }*/
                    projectLoading.setVisibility(View.GONE);
                    calendar.setTime(curDate);
                    calendar.add(calendar.DATE, -1);
                    curDate = calendar.getTime();
                    yesterDay = formatter.format(curDate);
                    if (yesterDay.equals(today)) {
                        tvData.setText("今天");
                    } else {
                        tvData.setText(yesterDay);
                    }
                    initClass();
                    break;
                case 2:
                    /*data++;
                    String chooseTime2 = split[0] + "-" + split[1] + "-" + data;
                    if(today.equals(chooseTime2)){
                        tvData.setText("今天");
                    }else{
                        tvData.setText(chooseTime2);
                    }*/
                    projectLoading.setVisibility(View.GONE);
                    calendar.setTime(curDate);
                    calendar.add(calendar.DATE, 1);
                    curDate = calendar.getTime();
                    tomorrow = formatter.format(curDate);
                    if (tomorrow.equals(today)) {
                        tvData.setText("今天");
                    } else {
                        tvData.setText(tomorrow);
                    }
                    initClass();
                    break;
                case 4:
                    /*int curItem = message.arg1;//0,1,2,3
                    //1,2,3,0
                    //final int nextItem = (curItem + 1) % 4;
                    int nextItem = (curItem + 1) % 4;
                    vpHead.setCurrentItem(nextItem);
                    //设置viewager现在要展示的页面位置

                    *//*try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*//*
                    *//*new Thread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }
                    ).start();*//*
                    Message msg = Message.obtain();
                    msg.what = 4;
                    msg.arg1 = nextItem;
                    handler.sendMessage(msg);*/

                    break;
            }
            return true;
        }
    });

    @Override
    public void onResume() {
        super.onResume();
        Message message = handler.obtainMessage();
        message.what = 4;//做标记
        int currentItem = vpHead.getCurrentItem();//获取到viewager现在展示的页面的位置信息
        message.arg1 = currentItem;
        handler.sendMessage(message);
    }

    private void initView() {
        vpHead.setOnPageChangeListener(this);
        rbImg1.setChecked(true);
        rgImgGroup.setOnCheckedChangeListener(this);
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        curDate = new Date(System.currentTimeMillis());
        today = formatter.format(curDate);
        tvData.setText("今天");
        btnDatajian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                handler.sendMessage(msg);
                msg.what = 1;
            }
        });
        btnDataAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg = new Message();
                handler.sendMessage(msg);
                msg.what = 2;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //T.ShowToast(getContext(),);
        //System.out.println("==onPageScrolled==>" + position);
    }

    @Override
    public void onPageSelected(int position) {
        //T.ShowToast(getContext(),"==>"+position);
        //System.out.println("==onPageSelected==>" + position);
        //selected = position;
        RadioButton radioButton = (RadioButton) rgImgGroup.getChildAt(position);
        radioButton.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //System.out.println("==onPageScrollStateChanged==>" + state);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        //获取到RadioGroup里面子控件的个数
        int count = radioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            RadioButton button = (RadioButton) radioGroup.getChildAt(i);
            if (button.isChecked()) {//说明这个button就是被选中的按钮，当前的位置就是需要显示的viewpager的页面位置
                vpHead.setCurrentItem(i);
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
