package com.rf.hp.normaluniversitystu.activity.learn;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rf.hp.normaluniversitystu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索资料界面
 */
public class NoOpenStyle extends AppCompatActivity {

    Context context;
    @Bind(R.id.toolbar_nofileopen)
    Toolbar toolbarNofileopen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofileopen);
        ButterKnife.bind(this);
        context = NoOpenStyle.this;
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        toolbarNofileopen.setTitle("");
        setSupportActionBar(toolbarNofileopen);
        toolbarNofileopen.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarNofileopen.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarNofileopen.setNavigationOnClickListener(new View.OnClickListener() {
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
