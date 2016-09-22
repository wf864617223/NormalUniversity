package com.rf.hp.normaluniversitystu.utils;

/**
 * Created by hp on 2016/7/20.
 */
public class HttpContent {

    public static final String TEST_URL = "http://app.cdangel.com/api/product/getProductList";
    /**
     * 服务器地址
     */
    //public static final String SERVER_ADDRESS = "http://do.rimiedu.com/wxjy";
    public static final String SERVER_ADDRESS = "http://192.168.10.3";
    /**
     * 学生账号：密码:
     * 登录接口 post
     * 参数 account password
     */
    public static final String LOGIN_URL = SERVER_ADDRESS+"/api/student/login.html";
    /**
     * 获取广告列表接口
     */
    public static final String GET_AD_URL = SERVER_ADDRESS+"/api/student/getAdvertList.html?studentId=";
    /**
     * 修改密码接口
     */
    public static final String CHANGE_PWD_URL = SERVER_ADDRESS+"/api/student/changePassword.html";
    /**
     * 学生请假列表接口
     * GET
     */
    public static final String LEAVE_REQUSET_URL = SERVER_ADDRESS+"/api/student/getAskList.html?studentId=";
    //public static final String LEAVE_REQUSET_URL = "http://192.168.6.65:8080/api/student/getAskList.html?studentId=";
    /**
     * 提交请假申请接口
     * POST
     */
    public static final String COMMIT_LEAVE_REQUEST_URL = SERVER_ADDRESS+"/api/student/setAsk.html";
    //public static final String COMMIT_LEAVE_REQUEST_URL = "http://192.168.6.65:8080/api/student/setAsk.html";
    /**
     * 获取课程列表接口
     * GET
     */
    public static final String GET_PROJECT_URL = SERVER_ADDRESS+"/api/student/getCurrList.html?";
    /**
     * 保存签到信息
     * POST
     */
    public static final String SAVE_QIANDAO_INFO = SERVER_ADDRESS+"/api/student/saveRollcallStudent.html";
    /**
     * 保存广告详细信息接口
     * POST
     */
    public static final String SAVE_AD_INFO = SERVER_ADDRESS+"/api/student/saveAdvertInfo.html";
    /**
     * 获取通知
     */
    public static final String GET_NOICE_LIST = SERVER_ADDRESS+"/api/notice/getNoticeList.html?";
    /**
     * 设置学生头像接口
     */
    public static final String SETTING_HEAD_IMG = SERVER_ADDRESS+"/api/student/setStuPicture.html";
    /**
     * 获取签到经纬度接口
     */
    public static final String GET_QIANDAO_ADD = SERVER_ADDRESS+"/api/student/getSignIntude.html?curriculumId=";
    /**
     * 获取签退经纬度接口
     */
    public static final String GET_QIANTUI_ADD = SERVER_ADDRESS+"/api/student/getSignOfftude.html?curriculumId=";
    /**
     * 保存用户反馈信息接口
     */
    public static final String SAVE_USER_ADVICE = SERVER_ADDRESS+"/api/advice/saveAdvice.html";
    /**
     * 关于我们的接口
     */
    public static final String ABOUT_US = SERVER_ADDRESS+"/api/aboutus/getAboutUs.html";
    /**
     * 获取共享资料接口
     */
    public static final String GET_SHARE_DATA = SERVER_ADDRESS+"/api/student/getDatumList.html?searchName=";
    /**
     * 在线学习接口
     */
    public static final String GET_ONLINE_LEARN = SERVER_ADDRESS+"/api/student/getOnlineEduList.html";
    /**
     * 推送token信息接口
     */
    public static final String PUT_TOKEN = SERVER_ADDRESS+"/api/student/token.html";
    //public static final String PUT_TOKEN = "http://192.168.6.65:8080/api/student/token.html";
}
