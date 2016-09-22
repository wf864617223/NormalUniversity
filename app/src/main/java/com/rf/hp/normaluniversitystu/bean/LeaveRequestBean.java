package com.rf.hp.normaluniversitystu.bean;

import java.util.List;

/**
 * Created by hp on 2016/7/20.
 */
public class LeaveRequestBean {

    /**
     * status : 0
     * message : success
     * result : [{"askForLeaveId":1,"askForLeaveType":"事假","askForLeaveStartTime":"2016-07-07 08:00:00","askForLeaveProcesseTime":"2016-07-07 08:00:00","askForLeaveContent":"有事请假啊","askForLeaveRemark":"没事不要请假","askForLeaveApplicationTime":"2016-07-07 08:00:00","askForLeaveStatus":"不通过","askForLeaveEndTime":"2016-07-08 08:00:00"}]
     */

    private int status;
    private String message;
    /**
     * askForLeaveId : 1
     * askForLeaveType : 事假
     * askForLeaveStartTime : 2016-07-07 08:00:00
     * askForLeaveProcesseTime : 2016-07-07 08:00:00
     * askForLeaveContent : 有事请假啊
     * askForLeaveRemark : 没事不要请假
     * askForLeaveApplicationTime : 2016-07-07 08:00:00
     * askForLeaveStatus : 不通过
     * askForLeaveEndTime : 2016-07-08 08:00:00
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
        private int askForLeaveId;
        private String askForLeaveType;
        private String askForLeaveStartTime;
        private String askForLeaveProcesseTime;
        private String askForLeaveContent;
        private String askForLeaveRemark;
        private String askForLeaveApplicationTime;
        private String askForLeaveStatus;
        private String askForLeaveEndTime;

        public int getAskForLeaveId() {
            return askForLeaveId;
        }

        public void setAskForLeaveId(int askForLeaveId) {
            this.askForLeaveId = askForLeaveId;
        }

        public String getAskForLeaveType() {
            return askForLeaveType;
        }

        public void setAskForLeaveType(String askForLeaveType) {
            this.askForLeaveType = askForLeaveType;
        }

        public String getAskForLeaveStartTime() {
            return askForLeaveStartTime;
        }

        public void setAskForLeaveStartTime(String askForLeaveStartTime) {
            this.askForLeaveStartTime = askForLeaveStartTime;
        }

        public String getAskForLeaveProcesseTime() {
            return askForLeaveProcesseTime;
        }

        public void setAskForLeaveProcesseTime(String askForLeaveProcesseTime) {
            this.askForLeaveProcesseTime = askForLeaveProcesseTime;
        }

        public String getAskForLeaveContent() {
            return askForLeaveContent;
        }

        public void setAskForLeaveContent(String askForLeaveContent) {
            this.askForLeaveContent = askForLeaveContent;
        }

        public String getAskForLeaveRemark() {
            return askForLeaveRemark;
        }

        public void setAskForLeaveRemark(String askForLeaveRemark) {
            this.askForLeaveRemark = askForLeaveRemark;
        }

        public String getAskForLeaveApplicationTime() {
            return askForLeaveApplicationTime;
        }

        public void setAskForLeaveApplicationTime(String askForLeaveApplicationTime) {
            this.askForLeaveApplicationTime = askForLeaveApplicationTime;
        }

        public String getAskForLeaveStatus() {
            return askForLeaveStatus;
        }

        public void setAskForLeaveStatus(String askForLeaveStatus) {
            this.askForLeaveStatus = askForLeaveStatus;
        }

        public String getAskForLeaveEndTime() {
            return askForLeaveEndTime;
        }

        public void setAskForLeaveEndTime(String askForLeaveEndTime) {
            this.askForLeaveEndTime = askForLeaveEndTime;
        }
    }
}
