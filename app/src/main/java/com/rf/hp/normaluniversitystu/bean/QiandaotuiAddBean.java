package com.rf.hp.normaluniversitystu.bean;

/**
 * Created by hp on 2016/8/3.
 */
public class QiandaotuiAddBean {

    /**
     * status : 0
     * message : success
     * result : {"signOfflongitude":"\u201d\u201d","signOfflatitude":"\u201d\u201d"}
     */

    private int status;
    private String message;
    /**
     * signOfflongitude : ””
     * signOfflatitude : ””
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
        private String signOfflongitude;
        private String signOfflatitude;

        public String getSignOfflongitude() {
            return signOfflongitude;
        }

        public void setSignOfflongitude(String signOfflongitude) {
            this.signOfflongitude = signOfflongitude;
        }

        public String getSignOfflatitude() {
            return signOfflatitude;
        }

        public void setSignOfflatitude(String signOfflatitude) {
            this.signOfflatitude = signOfflatitude;
        }
    }
}
