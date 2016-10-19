package com.rf.hp.normaluniversitystu.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.activity.kaoqin.QiandaoActivity;
import com.rf.hp.normaluniversitystu.activity.kaoqin.QiantuiActivity;
import com.rf.hp.normaluniversitystu.activity.kaoqin.QingjiaActivity;
import com.rf.hp.normaluniversitystu.bean.ProjectBean;
import com.rf.hp.normaluniversitystu.utils.GpsUtils;
import com.rf.hp.normaluniversitystu.utils.T;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 考勤界面的listView适配器
 * Created by hp on 2016/7/18.
 */
public class ProjectAdapter extends BaseAdapter {
    Context context;
    private List<ProjectBean.ResultBean> prolist;

    public ProjectAdapter(Context context, List<ProjectBean.ResultBean> prolist) {
        this.context = context;
        this.prolist = prolist;
    }

    @Override
    public int getCount() {
        return prolist.size();
    }

    @Override
    public Object getItem(int i) {
        return prolist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.viewholder_project, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final String curriculumName = prolist.get(i).getCurriculumName();
        viewHolder.tvProjectName.setText(curriculumName);
        final String classroom = prolist.get(i).getHouseName() + "-" + prolist.get(i).getClassroomName();
        viewHolder.tvProjectClass.setText("教室："+classroom);
        final String teachName = prolist.get(i).getTeachName();
        viewHolder.tvProjectTeacher.setText("老师："+teachName);
        final String curriculumStartTime = prolist.get(i).getCurriculumStartTime();
        final String curriculumEndTime = prolist.get(i).getCurriculumEndTime();
        String[] split = curriculumStartTime.split(" ");
        String[] split1 = curriculumEndTime.split(" ");
        String[] split3 = split[1].split(":");
        String[] split4 = split1[1].split(":");
        String[] split2 = split[0].split("-");
        String nowDate = split2[1]+"/"+split2[2];
        final String startEndTime = nowDate+"  "+split3[0]+":"+split3[1]+ "-" + split4[0]+":"+split4[1];
        final String curriculumId = prolist.get(i).getCurriculumId() + "";
        viewHolder.tvProjectTime.setText("时间："+startEndTime);
        final String ifSignIn = prolist.get(i).getIfSignIn();
        String ifSignOff = prolist.get(i).getIfSignOff();
        String ifAskleave = prolist.get(i).getIfAskleave();
        viewHolder.btnQingjia.setVisibility(View.VISIBLE);
        viewHolder.btnTomap.setVisibility(View.VISIBLE);
        viewHolder.btnTomapTwo.setVisibility(View.VISIBLE);
        if(ifSignIn.equals("true")){
            //viewHolder.btnQingjia.setVisibility(View.GONE);
            viewHolder.btnTomap.setText("已签到");
            viewHolder.btnTomapTwo.setVisibility(View.VISIBLE);
            viewHolder.btnTomap.setEnabled(false);
            viewHolder.btnTomap.setBackgroundResource(R.color.color_et_bg);
        }else{
            viewHolder.btnTomap.setText("签到");
            viewHolder.btnTomapTwo.setVisibility(View.GONE);
            viewHolder.btnTomap.setEnabled(true);
            viewHolder.btnTomap.setBackgroundResource(R.color.allback);
        }
        if(ifSignOff.equals("true")){
            viewHolder.btnQingjia.setVisibility(View.GONE);
            //viewHolder.btnTomapTwo.setVisibility(View.VISIBLE);
            viewHolder.btnTomapTwo.setText("已签退");
            viewHolder.btnTomapTwo.setEnabled(false);
            viewHolder.btnTomapTwo.setBackgroundResource(R.color.color_et_bg);
        }else{
            //viewHolder.btnTomapTwo.setVisibility(View.VISIBLE);
            viewHolder.btnTomapTwo.setText("签退");
            //viewHolder.btnTomapTwo.setTextColor(R.color.white);
            viewHolder.btnTomapTwo.setEnabled(true);
            viewHolder.btnTomapTwo.setBackgroundResource(R.color.allback);
        }
        if(ifAskleave.equals("true")){
            viewHolder.btnTomap.setVisibility(View.GONE);
            viewHolder.btnTomapTwo.setVisibility(View.GONE);
            viewHolder.btnQingjia.setText("已请假");
            viewHolder.btnQingjia.setEnabled(false);
            viewHolder.btnQingjia.setBackgroundResource(R.color.color_et_bg);
        }else{
            viewHolder.btnQingjia.setText("请假");
            viewHolder.btnQingjia.setEnabled(true);
            viewHolder.btnQingjia.setBackgroundResource(R.color.btnbackground);
        }
        //情假的点击事件
        viewHolder.btnQingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QingjiaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("askForLeaveStartTime",curriculumStartTime);
                bundle.putString("askForLeaveEndTime",curriculumEndTime);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        final GpsUtils gpsUtils = new GpsUtils(context);
        //签到的点击事件
        viewHolder.btnTomap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean oPen = gpsUtils.isOPen(context);
                if(oPen){
                    Intent intent = new Intent(context, QiandaoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("curriculumId",curriculumId);
                    bundle.putString("curriculumName",curriculumName);
                    bundle.putString("classroom",classroom);
                    bundle.putString("teachName",teachName);
                    bundle.putString("startEndTime",startEndTime);
                    bundle.putString("curriculumStartTime",curriculumStartTime);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else {
                    /*gpsUtils.openGPS(context);
                    Intent intent = new Intent(context, QiandaoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("curriculumId",curriculumId);
                    bundle.putString("curriculumName",curriculumName);
                    bundle.putString("classroom",classroom);
                    bundle.putString("teachName",teachName);
                    bundle.putString("startEndTime",startEndTime);
                    intent.putExtras(bundle);
                    context.startActivity(intent);*/
                    T.ShowToast(context,"请打开GPS后，再进行签到");
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                }

            }
        });
        //final boolean clickable = viewHolder.btnTomap.isClickable();
        //签退的点击事件
        viewHolder.btnTomapTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ifSignIn.equals("true")){
                    boolean oPen = gpsUtils.isOPen(context);
                    if(oPen){
                        Intent intent = new Intent(context, QiantuiActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("curriculumId",curriculumId);
                        bundle.putString("curriculumName",curriculumName);
                        bundle.putString("classroom",classroom);
                        bundle.putString("teachName",teachName);
                        bundle.putString("startEndTime",startEndTime);
                        bundle.putString("curriculumStartTime",curriculumStartTime);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }else{
                        /*gpsUtils.openGPS(context);
                        T.ShowToast(context,"没有打开GPS");
                        Intent intent = new Intent(context, QiantuiActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("curriculumId",curriculumId);
                        bundle.putString("curriculumName",curriculumName);
                        bundle.putString("classroom",classroom);
                        bundle.putString("teachName",teachName);
                        bundle.putString("startEndTime",startEndTime);
                        intent.putExtras(bundle);
                        context.startActivity(intent);*/
                        T.ShowToast(context,"请打开GPS后，再进行签退");
                        // 转到手机设置界面，用户设置GPS
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }

                }else{
                    T.ShowToast(context,"您没有签到");
                }

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_project_name)
        TextView tvProjectName;
        @Bind(R.id.tv_project_teacher)
        TextView tvProjectTeacher;
        @Bind(R.id.tv_project_class)
        TextView tvProjectClass;
        @Bind(R.id.tv_project_time)
        TextView tvProjectTime;
        @Bind(R.id.btn_tomap)
        Button btnTomap;
        @Bind(R.id.btn_tomap_two)
        Button btnTomapTwo;
        @Bind(R.id.btn_qingjia)
        Button btnQingjia;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
