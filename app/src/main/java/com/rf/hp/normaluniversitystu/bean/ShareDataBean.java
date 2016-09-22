package com.rf.hp.normaluniversitystu.bean;

import java.util.List;

/**
 * Created by hp on 2016/8/5.
 */
public class ShareDataBean {

    /**
     * status : 0
     * message : success
     * result : [{"datumId":1,"datumName":" 英语六级复习资料(完整版)","datumPath":" /datum/英语六级复习资料(完整版).doc","datumUploadtime":" 2016-08-05 10:12:31"}]
     */

    private int status;
    private String message;
    /**
     * datumId : 1
     * datumName :  英语六级复习资料(完整版)
     * datumPath :  /datum/英语六级复习资料(完整版).doc
     * datumUploadtime :  2016-08-05 10:12:31
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
        private int datumId;
        private String datumName;
        private String datumPath;
        private String datumUploadtime;

        public int getDatumId() {
            return datumId;
        }

        public void setDatumId(int datumId) {
            this.datumId = datumId;
        }

        public String getDatumName() {
            return datumName;
        }

        public void setDatumName(String datumName) {
            this.datumName = datumName;
        }

        public String getDatumPath() {
            return datumPath;
        }

        public void setDatumPath(String datumPath) {
            this.datumPath = datumPath;
        }

        public String getDatumUploadtime() {
            return datumUploadtime;
        }

        public void setDatumUploadtime(String datumUploadtime) {
            this.datumUploadtime = datumUploadtime;
        }
    }
}
