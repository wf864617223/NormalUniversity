package com.rf.hp.normaluniversitystu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.activity.kaoqin.AdInfoActivity;
import com.rf.hp.normaluniversitystu.bean.HeadData;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.IntentManager;
import com.rf.hp.normaluniversitystu.utils.T;

import java.util.List;

/**
 * 考勤顶部的ViewPager适配器
 * Created by huanghua on 16/2/19.
 */
public class MyPagerAdapter extends PagerAdapter{
    private List<HeadData.ResultBean> list;
    private Context context;

    public MyPagerAdapter(List<HeadData.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //HeadData game = list.get(position);
        String picStr = list.get(position).getAdvertPicture();
         //list.get(position).getImage();
        final String url = HttpContent.SERVER_ADDRESS+picStr;
        //System.out.println("==url==>"+url);
        Glide.with(context).load(url).placeholder(R.drawable.nopicture).error(R.drawable.nopicture).into(imageView);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //T.ShowToast(context,"=img=>"+position);
                /*Intent intent = new Intent(context, AdInfoActivity.class);
                context.startActivity(intent);*/
                Bundle bundle = new Bundle();
                bundle.putString("advertId",list.get(position).getAdvertId()+"");
                bundle.putString("advertName",list.get(position).getAdvertName());
                bundle.putString("advertLevel",list.get(position).getAdvertLevel()+"");
                bundle.putString("advertOrder",list.get(position).getAdvertOrder()+"");
                bundle.putString("advertTitle",list.get(position).getAdvertTitle());
                bundle.putString("advertPicture",url);
                bundle.putString("advertTime",list.get(position).getAdvertTime());
                bundle.putString("advertContent",list.get(position).getAdvertContent());
                //IntentManager.getInstance(context).toAdInfoActivity(bundle);
                Intent intent = new Intent(context,AdInfoActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((ImageView)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
