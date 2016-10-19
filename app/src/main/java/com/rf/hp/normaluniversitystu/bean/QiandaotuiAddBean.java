package com.rf.hp.normaluniversitystu.bean;

/**
 * Created by hp on 2016/8/3.
 */
public class QiandaotuiAddBean {

    /**
     * message : Success
     * result : {"signInlatitude":"30.574764","signInlongitude":"104.064674"}
     * status : 0
     */

    private String message;
    /**
     * signInlatitude : 30.574764
     * signInlongitude : 104.064674
     */

    private ResultBean result;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class ResultBean {
        private String signInlatitude;
        private String signInlongitude;

        public String getSignInlatitude() {
            return signInlatitude;
        }

        public void setSignInlatitude(String signInlatitude) {
            this.signInlatitude = signInlatitude;
        }

        public String getSignInlongitude() {
            return signInlongitude;
        }

        public void setSignInlongitude(String signInlongitude) {
            this.signInlongitude = signInlongitude;
        }
    }
}
