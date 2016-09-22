package com.rf.hp.normaluniversitystu.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.activity.noice.NoiceInfoActivity;
import com.rf.hp.normaluniversitystu.adapter.NoiceAdapter;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.bean.NoiceBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.LoadingView;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通知界面
 * Created by hp on 2016/7/15.
 */
@ContentView(R.layout.frag_noice)
public class NoticeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.lv_noice)
    ListView lvNoice;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    /*@Bind(R.id.notice_loading)
    LoadingView noticeLoading;*/
    private OnFragmentInteractionListener mListener;
    private List<NoiceBean.ResultBean> noiceBeanResult = new ArrayList<>();
    private NoiceAdapter noiceAdapter;

    public NoticeFragment() {

    }

    public static NoticeFragment newInstance(String param1, String param2) {
        NoticeFragment noticeFragment = new NoticeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        noticeFragment.setArguments(args);
        return noticeFragment;
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
        initView();
        return view;
    }

    private void initView() {
        swipeRefresh.setColorSchemeColors(Color.BLUE);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                initData();
                if (lvNoice != null) {
                    swipeRefresh.setRefreshing(false);
                }
            }
        });
        lvNoice.setOnItemClickListener(this);
    }

    private void initData() {
        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(getContext());
        String studentId = resultBean.getStudentId();
        //prgDialog = new PrgDialog(getContext());

        String student = "";
        try {
            student = URLEncoder.encode("学生", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //noticeLoading.setVisibility(View.VISIBLE);
        HttpUtil.doHttp(HttpContent.GET_NOICE_LIST + "userId=" + studentId + "&userType=" + student, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                //noticeLoading.setVisibility(View.GONE);
                NoiceBean noiceBean = GsonTools.getBean(result, NoiceBean.class);
                int status = noiceBean.getStatus();
                if (status == 0) {
                    noiceBeanResult = noiceBean.getResult();
                    noiceAdapter = new NoiceAdapter(getContext(), noiceBeanResult);
                    //Collections.reverse(noiceBeanResult);
                    lvNoice.setAdapter(noiceAdapter);
                    noiceAdapter.notifyDataSetChanged();

                    //prgDialog.closeDialog();

                } else {
                    //prgDialog.closeDialog();
                    String message = noiceBean.getMessage();
                    T.ShowToast(getContext(), message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //prgDialog.closeDialog();
                //noticeLoading.setVisibility(View.GONE);
                T.ShowToast(getContext(), "通知获取失败");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String noticeTitle = noiceBeanResult.get(i).getNoticeTitle();
        String noticeContent = noiceBeanResult.get(i).getNoticeContent();
        String noticeDate = noiceBeanResult.get(i).getNoticeDate();
        Intent intent = new Intent(getContext(), NoiceInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("noticeTitle", noticeTitle);
        bundle.putString("noticeDate", noticeDate);
        bundle.putString("noticeContent", noticeContent + "");
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
