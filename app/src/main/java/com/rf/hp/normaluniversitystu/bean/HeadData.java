package com.rf.hp.normaluniversitystu.bean;

import java.util.List;

/**
 * 考勤界面ViewPager的bean
 * Created by hp on 2016/7/18.
 */
public class HeadData {

    /**
     * message : Success
     * result : [{"advertContent":"2","advertId":37,"advertLevel":2,"advertName":"广告2","advertOrder":2,"advertPicture":"/picture/114198.jpg","advertTime":"2016-07-22","advertTitle":"2"},{"advertContent":"3","advertId":38,"advertLevel":3,"advertName":"广告3","advertOrder":3,"advertPicture":"/picture/476015.jpg","advertTime":"2016-07-21","advertTitle":"4"},{"advertContent":"1","advertId":44,"advertLevel":1,"advertName":"1","advertOrder":1,"advertPicture":"/picture/490519.jpg","advertTime":"2016-07-22","advertTitle":"1"},{"advertContent":"1","advertId":45,"advertLevel":1,"advertName":"1","advertOrder":1,"advertPicture":"/picture/504914.jpg","advertTime":"2016-07-21","advertTitle":"1"}]
     * status : 0
     */

    private String message;
    private int status;
    /**
     * advertContent : 2
     * advertId : 37
     * advertLevel : 2
     * advertName : 广告2
     * advertOrder : 2
     * advertPicture : /picture/114198.jpg
     * advertTime : 2016-07-22
     * advertTitle : 2
     */

    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        private String advertContent;
        private int advertId;
        private int advertLevel;
        private String advertName;
        private int advertOrder;
        private String advertPicture;
        private String advertTime;
        private String advertTitle;

        public String getAdvertContent() {
            return advertContent;
        }

        public void setAdvertContent(String advertContent) {
            this.advertContent = advertContent;
        }

        public int getAdvertId() {
            return advertId;
        }

        public void setAdvertId(int advertId) {
            this.advertId = advertId;
        }

        public int getAdvertLevel() {
            return advertLevel;
        }

        public void setAdvertLevel(int advertLevel) {
            this.advertLevel = advertLevel;
        }

        public String getAdvertName() {
            return advertName;
        }

        public void setAdvertName(String advertName) {
            this.advertName = advertName;
        }

        public int getAdvertOrder() {
            return advertOrder;
        }

        public void setAdvertOrder(int advertOrder) {
            this.advertOrder = advertOrder;
        }

        public String getAdvertPicture() {
            return advertPicture;
        }

        public void setAdvertPicture(String advertPicture) {
            this.advertPicture = advertPicture;
        }

        public String getAdvertTime() {
            return advertTime;
        }

        public void setAdvertTime(String advertTime) {
            this.advertTime = advertTime;
        }

        public String getAdvertTitle() {
            return advertTitle;
        }

        public void setAdvertTitle(String advertTitle) {
            this.advertTitle = advertTitle;
        }
    }
}
