package com.rf.hp.normaluniversitystu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.bean.OnLineWorkBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hp on 2016/8/23.
 */
public class OnLineWorkAdapter extends BaseAdapter {
    private List<OnLineWorkBean.ResultBean> result1;
    Context context;

    public OnLineWorkAdapter(List<OnLineWorkBean.ResultBean> result1, Context context) {
        this.result1 = result1;
        this.context = context;
    }

    @Override
    public int getCount() {
        return result1.size();
    }

    @Override
    public Object getItem(int position) {
        return result1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.viewholder_onlinework, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDataName.setText(result1.get(position).getName());
        viewHolder.tvDataDate.setText(result1.get(position).getType());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_data_name)
        TextView tvDataName;
        @Bind(R.id.tv_data_date)
        TextView tvDataDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
