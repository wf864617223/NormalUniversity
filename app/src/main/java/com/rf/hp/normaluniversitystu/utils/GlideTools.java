package com.rf.hp.normaluniversitystu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by hp on 2016/8/1.
 */
public class GlideTools {
    public static  void downLoadImg( final File file, final Context mContext, final String testUrl, final ImageView imageView){
        Glide.with(mContext)
                .load(testUrl)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                        FileOutputStream fout = null;
                        try {
                            String loginName = SharePreferInfoUtils.readLoginName(mContext);
                            //保存图片
                            fout = new FileOutputStream(file+"/"+loginName+".jpg");
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                            Bitmap bm = BitmapFactory.decodeFile(file+"/"+loginName+".jpg");
                         /*   bm.compress(Bitmap.CompressFormat.JPEG, 100, fout);// 把数据写入文件*/
                            Drawable drawable = new BitmapDrawable(bm);//转换成drawable
                            imageView.setImageDrawable(drawable);
                            // 将保存的地址给SubsamplingScaleImageView,这里注意设置ImageViewState
                            //imageView.setImage(ImageSource.bitmap(bm), new ImageViewState(0.5F, new PointF(0, 0), 0));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (fout != null) fout.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
