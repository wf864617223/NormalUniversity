package com.rf.hp.normaluniversitystu.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hp on 2016/8/8.
 */
public class DateUtils {
    /**
     * 比较两个日期大小
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 检测当前时间是否距离输入时间大于1小时
     * @param data1 当前时间
     * @param data2 输入时间
     * @return
     */
    public static int isBigOneHour(String data1,String data2){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(data1);
            Date dt2 = df.parse(data2);
            long time = dt1.getTime();
            long time1 = dt2.getTime();
            long times = time - time1;
            long abs = Math.abs(times);
            if(abs>1000*60*60){
                return 1;
            }else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    /**
     * 只能输入英文 汉字 数字
     */
    public static boolean isOtherData(String data){
        if(data.replaceAll("[a-z]*[A-Z]*\\d*[\\u4e00-\\u9fa5]*\\s*", "").length()==0){
            return true;
        }
        return false;
    }

    /**
     * \w匹配：中文字符，英文，数字，下划线
     *至于中文标点符号，如果有需要另外的就添加在中括号里面
     * @param data
     * @return
     */
    public static boolean isNoOtherData(String data){
        if(data.replaceAll("\\w+|[，。《》（）、—,.、\\s]+","").length()==0){
            return true;
        }
        return false;
    }
}
