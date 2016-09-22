package com.rf.hp.normaluniversitystu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.activity.learn.OnLineWorkActivity;
import com.rf.hp.normaluniversitystu.adapter.OnLineWorkAdapter;
import com.rf.hp.normaluniversitystu.bean.OnLineWorkBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.view.LoadingView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 在线学习界面
 * Created by hp on 2016/7/18.
 */
@ContentView(R.layout.fragment_learn_online)
public class OnLineLearnFragment extends Fragment {

    @Bind(R.id.lv_onlineLearn)
    ListView lvOnlineLearn;
    @Bind(R.id.lv_view)
    LoadingView lvView;
    private List<OnLineWorkBean.ResultBean> result1 = new ArrayList<>();
    OnLineWorkAdapter onLineWorkAdapter;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.fragment_learn_online, null);
        View view = x.view().inject(this, inflater, container);
        ButterKnife.bind(this, view);
        context = getContext();
        initData();
        return view;
    }

    private void initData() {
        lvView.setVisibility(View.VISIBLE);
        HttpUtil.doHttp(HttpContent.GET_ONLINE_LEARN, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                lvView.setVisibility(View.GONE);
                OnLineWorkBean onLineWorkBean = GsonTools.getBean(result, OnLineWorkBean.class);
                int status = onLineWorkBean.getStatus();
                if (status == 0) {
                    result1 = onLineWorkBean.getResult();
                    onLineWorkAdapter = new OnLineWorkAdapter(result1, context);
                    lvOnlineLearn.setAdapter(onLineWorkAdapter);
                    onLineWorkAdapter.notifyDataSetChanged();
                }else{
                    T.ShowToast(context,onLineWorkBean.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                lvView.setVisibility(View.GONE);
                T.ShowToast(context,"服务器连接失败");
            }
        });
        lvOnlineLearn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uriPath = result1.get(position).getUriPath();
                Intent intent = new Intent(context, OnLineWorkActivity.class);
                intent.putExtra("uri", uriPath);
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
