package com.rf.hp.normaluniversitystu.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.LeaveRequestBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 请假申请界面的适配器
 * Created by hp on 2016/7/19.
 */
public class LeaveRequestAdapter extends BaseAdapter {

    Context context;
    private List<LeaveRequestBean.ResultBean> resultBeanList;

    public LeaveRequestAdapter(Context context, List<LeaveRequestBean.ResultBean> resultBeanList) {
        this.context = context;
        this.resultBeanList = resultBeanList;
    }

    @Override
    public int getCount() {
        return resultBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return resultBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.viewholder_leaverequest, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String askForLeaveStartTime = resultBeanList.get(i).getAskForLeaveStartTime();
            String askForLeaveEndTime = resultBeanList.get(i).getAskForLeaveEndTime();
            viewHolder.tvRequestTime.setText(askForLeaveStartTime+"至"+askForLeaveEndTime);
            viewHolder.tvRequestaccount.setText(resultBeanList.get(i).getAskForLeaveContent());
            viewHolder.tvSendTime.setText("申请时间:"+resultBeanList.get(i).getAskForLeaveApplicationTime());
            String askForLeaveStatus = resultBeanList.get(i).getAskForLeaveStatus();
            String askForLeaveProcesseTime = resultBeanList.get(i).getAskForLeaveProcesseTime();
            Drawable drawable = context.getResources().getDrawable(R.mipmap.agree);
            Drawable drawable1 = context.getResources().getDrawable(R.mipmap.no);
            if("待批".equals(askForLeaveStatus)){
                viewHolder.tvLeaveing.setText("待审批");
                //viewHolder.ivRequestresult.setVisibility(View.GONE);
                viewHolder.ivRequestresult.setImageDrawable(null);
                viewHolder.tvResulttime.setText("");
            }else if("同意".equals(askForLeaveStatus)){
                viewHolder.ivRequestresult.setImageDrawable(drawable);
                viewHolder.tvResulttime.setText("处理时间:"+askForLeaveProcesseTime);
                viewHolder.tvLeaveing.setText("");
            }else if("不同意".equals(askForLeaveStatus)){
                viewHolder.ivRequestresult.setImageDrawable(drawable1);
                viewHolder.tvResulttime.setText("处理时间:"+askForLeaveProcesseTime);
                viewHolder.tvLeaveing.setText("");
            }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_request_time)
        TextView tvRequestTime;
        @Bind(R.id.imageView)
        ImageView imageView;
        @Bind(R.id.tv_requestaccount)
        TextView tvRequestaccount;
        @Bind(R.id.rl_account)
        RelativeLayout rlAccount;
        @Bind(R.id.tv_requestresult)
        TextView tvRequestresult;
        @Bind(R.id.tv_leaveing)
        TextView tvLeaveing;
        @Bind(R.id.iv_requestresult)
        ImageView ivRequestresult;
        @Bind(R.id.tv_resulttime)
        TextView tvResulttime;
        @Bind(R.id.tv_send_time)
        TextView tvSendTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
