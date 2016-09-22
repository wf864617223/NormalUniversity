package com.rf.hp.normaluniversitystu.bean;

import java.util.List;

/**
 * Created by hp on 2016/8/23.
 */
public class OnLineWorkBean {

    /**
     * status : 0
     * message : success
     * result : [{"id":1,"name":"在线学习","type":"在线学习","uriPath":" http://do.rimiedu.com/wxjy"}]
     */

    private int status;
    private String message;
    /**
     * id : 1
     * name : 在线学习
     * type : 在线学习
     * uriPath :  http://do.rimiedu.com/wxjy
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
        private int id;
        private String name;
        private String type;
        private String uriPath;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUriPath() {
            return uriPath;
        }

        public void setUriPath(String uriPath) {
            this.uriPath = uriPath;
        }
    }
}
