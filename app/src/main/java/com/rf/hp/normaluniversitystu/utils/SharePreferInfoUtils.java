package com.rf.hp.normaluniversitystu.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.Voice;

import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.bean.NoiceBean;

/**
 * Created by hp on 2016/7/19.
 */
public class SharePreferInfoUtils {

    /**
     *保存登录名
     */
    public static void saveLoginName(Context context,String stuNo){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginName",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("stuNo",stuNo);
        editor.commit();
    }

    /**
     * 读取登录名
     * @param context
     * @return
     */
    public  static String readLoginName(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginName",Context.MODE_PRIVATE);
        return sharedPreferences.getString("stuNo","");
    }

    /**
     * 保存登录密码
     * @param context
     * @param pwd
     */
    public static void saveLoginPwd(Context context,String pwd){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginPwd",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pwd",pwd);
        editor.commit();
    }

    /**
     * 读取登录密码
     * @param context
     * @return
     */
    public static String readLoginPwd(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginPwd",Context.MODE_PRIVATE);
        return sharedPreferences.getString("pwd","");
    }

    /**
     * 保存百度推送的channelId
     * @param context
     * @param channelId
     */
    public static void saveChannelId(Context context,String channelId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("channelId",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("channelId",channelId);
        editor.commit();
    }

    /**
     * 读取百度推送的channelId
     * @param context
     * @return
     */
    public static String readChannId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("channelId",Context.MODE_PRIVATE);
        return sharedPreferences.getString("channelId","");
    }
    /**
     * 清除登录密码
     * @param context
     */
    public static void clearLoginPwd(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginPwd",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pwd","");
        editor.commit();
    }

    /**
     * 保存学生的辅导员Id
     * @param context
     * @param staffId
     */
    public static void saveStaffId(Context context,String staffId){
        SharedPreferences sharedPreferences = context.getSharedPreferences("staffId",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("staffId",staffId);
        editor.commit();
    }

    /**
     * 读取学生的辅导员Id
     * @param context
     * @return
     */
    public static String readStaffId(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("staffId",Context.MODE_PRIVATE);
        return sharedPreferences.getString("staffId","");
    }
    /**
     * 保存登陆的用户的信息
     * @param context
     * @param resultInfo
     */
    public static void saveUserInfo(Context context,LoginBean.ResultBean resultInfo){

        SharedPreferences sharedPreferences = context.getSharedPreferences("result",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //学生Id
        String studentId = resultInfo.getStudentId()+"";
        //学生账号
        String studentAccount = resultInfo.getStudentAccount();
        //学生班级
        String studentClass = resultInfo.getStudentClass();
        //学生院校
        String studentCollege = resultInfo.getStudentCollege();
        //学生姓名
        String studentName = resultInfo.getStudentName();
        //学生专业
        String studentProfession = resultInfo.getStudentProfession();
        //备注信息
        String studentRemark = resultInfo.getStudentRemark();
        //学生性别
        String studentSex = resultInfo.getStudentSex();
        //班级Id
        String classId = resultInfo.getClassId();
        //
        String studentBatchNumber = resultInfo.getStudentBatchNumber();
        //
        String studentIdcard = resultInfo.getStudentIdcard();
        //学生登录密码
        String studentPassword = resultInfo.getStudentPassword();
        //学生手机
        String studentTelphone = resultInfo.getStudentTelphone();
        //学生头像
        String studentPicture = resultInfo.getStudentPicture();
        //登录token
        String token = resultInfo.getToken();
        editor.putString("classId",classId);
        editor.putString("studentBatchNumber",studentBatchNumber);
        editor.putString("studentIdcard",studentIdcard);
        editor.putString("studentPassword",studentPassword);
        editor.putString("studentTelphone",studentTelphone);
        editor.putString("studentId",studentId);
        editor.putString("studentAccount",studentAccount);
        editor.putString("studentClass",studentClass);
        editor.putString("studentCollege",studentCollege);
        editor.putString("studentName",studentName);
        editor.putString("studentProfession",studentProfession);
        editor.putString("studentRemark",studentRemark);
        editor.putString("studentSex",studentSex);
        editor.putString("studentPicture",studentPicture);
        editor.putString("token",token);
        editor.commit();
    }


    /**
     * 读取保存的用户信息
     * @param context
     * @return
     */
    public static LoginBean.ResultBean readUserInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("result",Context.MODE_PRIVATE);
        String studentId = sharedPreferences.getString("studentId", "");
        String studentAccount = sharedPreferences.getString("studentAccount", "");
        String studentClass = sharedPreferences.getString("studentClass", "");
        String studentCollege = sharedPreferences.getString("studentCollege", "");
        String studentName = sharedPreferences.getString("studentName", "");
        String studentProfession = sharedPreferences.getString("studentProfession", "");
        String studentRemark = sharedPreferences.getString("studentRemark", "");
        String studentSex = sharedPreferences.getString("studentSex", "");
        String classId = sharedPreferences.getString("classId", "");
        String studentBatchNumber = sharedPreferences.getString("studentBatchNumber", "");
        String studentIdcard = sharedPreferences.getString("studentIdcard", "");
        String studentPassword = sharedPreferences.getString("studentPassword", "");
        String studentTelphone = sharedPreferences.getString("studentTelphone", "");
        String studentPicture = sharedPreferences.getString("studentPicture", "");
        String token = sharedPreferences.getString("token", "");
        LoginBean.ResultBean resultInfo = new LoginBean.ResultBean();
        resultInfo.setStudentAccount(studentAccount);
        resultInfo.setStudentClass(studentClass);
        resultInfo.setStudentCollege(studentCollege);
        resultInfo.setStudentId(studentId);
        resultInfo.setStudentName(studentName);
        resultInfo.setStudentProfession(studentProfession);
        resultInfo.setStudentRemark(studentRemark);
        resultInfo.setStudentSex(studentSex);
        resultInfo.setClassId(classId);
        resultInfo.setStudentBatchNumber(studentBatchNumber);
        resultInfo.setStudentIdcard(studentIdcard);
        resultInfo.setStudentPassword(studentPassword);
        resultInfo.setStudentTelphone(studentTelphone);
        resultInfo.setStudentPicture(studentPicture);
        resultInfo.setToken(token);
        return resultInfo;
    }

    /**
     * 保存通知的信息
     * @param context
     * @param notice
     */
    public static void saveNoticeInfo(Context context, NoiceBean.ResultBean notice){
        SharedPreferences sharedPreferences = context.getSharedPreferences("noticeContent",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String noticeTitle = notice.getNoticeTitle();
        String noticeContent = notice.getNoticeContent();
        String noticeDate = notice.getNoticeDate();
        String noticeId = notice.getNoticeId() + "";
        String noticeObject = notice.getNoticeObject();
        editor.putString("noticeTitle",noticeTitle);
        editor.putString("noticeContent",noticeContent);
        editor.putString("noticeDate",noticeDate);
        editor.putString("noticeId",noticeId);
        editor.putString("noticeObject",noticeObject);
        editor.commit();
    }

    /**
     * 读取通知的信息
     * @param context
     * @return
     */
    public static NoiceBean.ResultBean readNoticeInfo(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("noticeContent",Context.MODE_PRIVATE);
        String noticeTitle = sharedPreferences.getString("noticeTitle", "");
        String noticeContent = sharedPreferences.getString("noticeContent", "");
        String noticeDate = sharedPreferences.getString("noticeDate", "");
        //String noticeId = sharedPreferences.getString("noticeId", "");
        String noticeObject = sharedPreferences.getString("noticeObject", "");
        NoiceBean.ResultBean resultBean = new NoiceBean.ResultBean();
        resultBean.setNoticeContent(noticeContent);
        resultBean.setNoticeDate(noticeDate);
        resultBean.setNoticeTitle(noticeTitle);
        //resultBean.setNoticeId(noticeId);
        resultBean.setNoticeObject(noticeObject);
        return resultBean;

    }
    /**
     * 保存是否已经点击定时提醒
     * @param context
     * @param b
     */
    public static void saveIsChecked(Context context,boolean b,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,b);
        editor.commit();
    }

    /**
     * 读取是否点击提醒
     * @param context
     * @return
     */
    public static boolean readIsChecked(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,false);
    }

    /**
     * 保存提醒时间
     * @param context
     * @param key
     * @param time
     */
    public static void saveNoiceTime(Context context,String key,String time){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,time);
        editor.commit();
    }

    /**
     * 读取提醒时间
     * @param context
     * @param key
     * @return
     */
    public static String readNoiceTime(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
    /**
     * 清除提醒时间
     * @param context
     * @param key
     */
    public static void clearNoiceTime(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,"");
        editor.commit();
    }
}
