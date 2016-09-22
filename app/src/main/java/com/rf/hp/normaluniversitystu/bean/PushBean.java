package com.rf.hp.normaluniversitystu.bean;

/**
 * Created by hp on 2016/9/14.
 */
public class PushBean {

    /**
     * noticeDate : 2016-09-14 11:07:24
     * noticeContent : <p>1234<br></p>
     * flag : 提示
     * type : 通知
     * noticeTitle : 测试
     */

    private String noticeDate;
    private String noticeContent;
    private String flag;
    private String type;
    private String noticeTitle;

    public String getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(String noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    @Override
    public String toString() {
        return "PushBean{" +
                "noticeDate='" + noticeDate + '\'' +
                ", noticeContent='" + noticeContent + '\'' +
                ", flag='" + flag + '\'' +
                ", type='" + type + '\'' +
                ", noticeTitle='" + noticeTitle + '\'' +
                '}';
    }
}
