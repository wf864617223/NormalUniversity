package com.rf.hp.normaluniversitystu.bean;

import java.util.List;

/**
 * Created by hp on 2016/7/27.
 */
public class TestBean {

    /**
     * errorCode : 200
     * message : 操作成功
     * data : [{"id":"1","shopping_business_id":"1","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638792.jpg","shopping_introduce":"等你来抢\r \t\t\t\t\t\t\t狗\t\t\t\t\t\t\t<\/p>","shopping_url":"http://item.jd.com/2740923.html","shopping_release_time":"2016-07-16 11:19:05","shopping_price":"1"},{"id":"2","shopping_business_id":"2","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638810.jpg","shopping_introduce":"夏日疯狂购","shopping_url":"http://item.jd.com/2740922.html","shopping_release_time":"2016-07-16 11:19:05","shopping_price":"2"},{"id":"3","shopping_business_id":"3","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638822.jpg","shopping_introduce":"清凉一夏","shopping_url":"http://item.jd.com/2740923.html","shopping_release_time":"2016-07-16 11:19:06","shopping_price":"3"},{"id":"4","shopping_business_id":"4","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638833.jpg","shopping_introduce":"T恤","shopping_url":"http://item.jd.com/2740922.html","shopping_release_time":"2016-07-16 11:19:06","shopping_price":"4"},{"id":"5","shopping_business_id":"5","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638844.jpg","shopping_introduce":"裤子","shopping_url":"http://item.jd.com/2740923.html","shopping_release_time":"2016-07-16 11:19:06","shopping_price":"5"},{"id":"6","shopping_business_id":"6","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638854.jpg","shopping_introduce":"鞋子","shopping_url":"http://item.jd.com/2740922.html","shopping_release_time":"2016-07-16 11:19:06","shopping_price":"6"},{"id":"7","shopping_business_id":"7","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638865.jpg","shopping_introduce":"5折起","shopping_url":"http://item.jd.com/2740923.html","shopping_release_time":"2016-07-16 11:19:06","shopping_price":"7"},{"id":"8","shopping_business_id":"8","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638877.jpg","shopping_introduce":"夏日疯狂购","shopping_url":"http://item.jd.com/2740922.html","shopping_release_time":"2016-07-16 11:19:06","shopping_price":"8"},{"id":"9","shopping_business_id":"9","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638891.jpg","shopping_introduce":"周年庆","shopping_url":"http://item.jd.com/2740923.html","shopping_release_time":"2016-07-16 11:19:06","shopping_price":"9"},{"id":"10","shopping_business_id":"10","shopping_image":"http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638910.jpg","shopping_introduce":"hi翻天","shopping_url":"http://item.jd.com/2740922.html","shopping_release_time":"2016-07-16 11:19:06","shopping_price":"10"}]
     */

    private int errorCode;
    private String message;
    /**
     * id : 1
     * shopping_business_id : 1
     * shopping_image : http://fenxiang.qihetimes.com/Uploads/others/20160716/1468638792.jpg
     * shopping_introduce : 等你来抢 							狗							</p>
     * shopping_url : http://item.jd.com/2740923.html
     * shopping_release_time : 2016-07-16 11:19:05
     * shopping_price : 1
     */

    private List<DataBean> data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String shopping_business_id;
        private String shopping_image;
        private String shopping_introduce;
        private String shopping_url;
        private String shopping_release_time;
        private String shopping_price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getShopping_business_id() {
            return shopping_business_id;
        }

        public void setShopping_business_id(String shopping_business_id) {
            this.shopping_business_id = shopping_business_id;
        }

        public String getShopping_image() {
            return shopping_image;
        }

        public void setShopping_image(String shopping_image) {
            this.shopping_image = shopping_image;
        }

        public String getShopping_introduce() {
            return shopping_introduce;
        }

        public void setShopping_introduce(String shopping_introduce) {
            this.shopping_introduce = shopping_introduce;
        }

        public String getShopping_url() {
            return shopping_url;
        }

        public void setShopping_url(String shopping_url) {
            this.shopping_url = shopping_url;
        }

        public String getShopping_release_time() {
            return shopping_release_time;
        }

        public void setShopping_release_time(String shopping_release_time) {
            this.shopping_release_time = shopping_release_time;
        }

        public String getShopping_price() {
            return shopping_price;
        }

        public void setShopping_price(String shopping_price) {
            this.shopping_price = shopping_price;
        }
    }
}
