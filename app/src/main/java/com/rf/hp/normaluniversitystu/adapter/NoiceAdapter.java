package com.rf.hp.normaluniversitystu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.NoiceBean;
import com.rf.hp.normaluniversitystu.utils.DateUtils;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/7/21.
 */
public class NoiceAdapter extends BaseAdapter {
    Context context;
    private List<NoiceBean.ResultBean> noiceBeanResult;

    public NoiceAdapter(Context context, List<NoiceBean.ResultBean> noiceBeanResult) {
        this.context = context;
        this.noiceBeanResult = noiceBeanResult;
    }

    @Override
    public int getCount() {
        return noiceBeanResult.size();
    }

    @Override
    public Object getItem(int i) {
        return noiceBeanResult.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.viewholder_noice, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String noticeTitle = noiceBeanResult.get(i).getNoticeTitle();
        viewHolder.tvMessageTitle.setText(noticeTitle);
        viewHolder.tvMessageContent.setText(noiceBeanResult.get(i).getNoticeContent());
        String noticeDate = noiceBeanResult.get(i).getNoticeDate();
        viewHolder.tvMessageSendtime.setText(noticeDate);
        boolean b = SharePreferInfoUtils.readIsChecked(context, noticeTitle);
        /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String nowDate = formatter.format(curDate);
        int i1 = DateUtils.compare_date(nowDate, noticeDate);
        if(i1 == 1){
            b = false;
        }*/
        if(b){
            viewHolder.ivMessageRead.setVisibility(View.VISIBLE);
        }else{
            viewHolder.ivMessageRead.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_message_title)
        TextView tvMessageTitle;
        @Bind(R.id.tv_message_content)
        TextView tvMessageContent;
        @Bind(R.id.tv_message_sendtime)
        TextView tvMessageSendtime;
        @Bind(R.id.iv_message_read)
        ImageView ivMessageRead;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
