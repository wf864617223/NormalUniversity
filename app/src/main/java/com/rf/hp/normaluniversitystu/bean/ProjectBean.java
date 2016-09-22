package com.rf.hp.normaluniversitystu.bean;

import java.util.List;

/**
 * Created by hp on 2016/7/21.
 */
public class ProjectBean {

    /**
     * status : 0
     * message : success
     * result : [{"curriculumId":1,"curriculumName":"课程名称","teacherName":"老师名称","curriculumStartTime":"09:00:00","curriculumEndTime":"09:00:00","houseName":"\u201d第二教学楼\u201d","classroomName":"\u201d205\u201d"}]
     */

    private int status;
    private String message;
    /**
     * curriculumId : 1
     * curriculumName : 课程名称
     * teacherName : 老师名称
     * curriculumStartTime : 09:00:00
     * curriculumEndTime : 09:00:00
     * houseName : ”第二教学楼”
     * classroomName : ”205”
     * ifSignIn:”true”
     * ifSignOff:”true”
     * ifAskleave:”false”
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
        private int curriculumId;
        private String curriculumName;
        private String teacherName;
        private String curriculumStartTime;
        private String curriculumEndTime;
        private String houseName;
        private String classroomName;
        private String ifSignIn;
        private String ifSignOff;
        private String ifAskleave;
        private String signInlatitude;
        private String signInlongitude;
        private String signOfflongitude;
        private String signOfflatitude;

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

        public String getIfSignIn() {
            return ifSignIn;
        }

        public void setIfSignIn(String ifSignIn) {
            this.ifSignIn = ifSignIn;
        }

        public String getIfSignOff() {
            return ifSignOff;
        }

        public void setIfSignOff(String ifSignOff) {
            this.ifSignOff = ifSignOff;
        }

        public String getIfAskleave() {
            return ifAskleave;
        }

        public void setIfAskleave(String ifAskleave) {
            this.ifAskleave = ifAskleave;
        }

        public int getCurriculumId() {
            return curriculumId;
        }

        public void setCurriculumId(int curriculumId) {
            this.curriculumId = curriculumId;
        }

        public String getCurriculumName() {
            return curriculumName;
        }

        public void setCurriculumName(String curriculumName) {
            this.curriculumName = curriculumName;
        }

        public String getTeachName() {
            return teacherName;
        }

        public void setTeachName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getCurriculumStartTime() {
            return curriculumStartTime;
        }

        public void setCurriculumStartTime(String curriculumStartTime) {
            this.curriculumStartTime = curriculumStartTime;
        }

        public String getCurriculumEndTime() {
            return curriculumEndTime;
        }

        public void setCurriculumEndTime(String curriculumEndTime) {
            this.curriculumEndTime = curriculumEndTime;
        }

        public String getHouseName() {
            return houseName;
        }

        public void setHouseName(String houseName) {
            this.houseName = houseName;
        }

        public String getClassroomName() {
            return classroomName;
        }

        public void setClassroomName(String classroomName) {
            this.classroomName = classroomName;
        }
    }
}
