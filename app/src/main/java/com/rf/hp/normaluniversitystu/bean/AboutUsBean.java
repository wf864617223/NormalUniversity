package com.rf.hp.normaluniversitystu.bean;

/**
 * Created by hp on 2016/8/4.
 */
public class AboutUsBean {

    /**
     * status : 0
     * message : success
     * result : {"aboutusId":0,"aboutusContent":"123"}
     */

    private int status;
    private String message;
    /**
     * aboutusId : 0
     * aboutusContent : 123
     */

    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private int aboutusId;
        private String aboutusContent;

        public int getAboutusId() {
            return aboutusId;
        }

        public void setAboutusId(int aboutusId) {
            this.aboutusId = aboutusId;
        }

        public String getAboutusContent() {
            return aboutusContent;
        }

        public void setAboutusContent(String aboutusContent) {
            this.aboutusContent = aboutusContent;
        }
    }
}
