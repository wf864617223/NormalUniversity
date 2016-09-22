package com.rf.hp.normaluniversitystu.bean;

import java.util.List;

/**
 * 通知的实体类
 * Created by hp on 2016/7/21.
 */
public class NoiceBean {

    /**
     * status : 0
     * message : success
     * result : [{"noticeId":1,"noticeContent":"通知内容","noticeDate":" 2016-07-07","noticeTitle":"通知标题","noticeObject":"学生"}]
     */

    private int status;
    private String message;
    /**
     * noticeId : 1
     * noticeContent : 通知内容
     * noticeDate :  2016-07-07
     * noticeTitle : 通知标题
     * noticeObject : 学生
     */

    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private int noticeId;
        private String noticeContent;
        private String noticeDate;
        private String noticeTitle;
        private String noticeObject;

        public int getNoticeId() {
            return noticeId;
        }

        public void setNoticeId(int noticeId) {
            this.noticeId = noticeId;
        }

        public String getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
        }

        public String getNoticeDate() {
            return noticeDate;
        }

        public void setNoticeDate(String noticeDate) {
            this.noticeDate = noticeDate;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public String getNoticeObject() {
            return noticeObject;
        }

        public void setNoticeObject(String noticeObject) {
            this.noticeObject = noticeObject;
        }
    }
}
