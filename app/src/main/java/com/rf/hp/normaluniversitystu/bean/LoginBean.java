package com.rf.hp.normaluniversitystu.bean;

/**
 * Created by hp on 2016/7/20.
 */
public class LoginBean {

    /**
     * message : Success
     * result : {"classId":3,"staff":{"administrationId":0,"staffId":1,"staffIsinpost":false},"studentAccount":"X1001","studentBatchNumber":"-","studentClass":"计科系16级1班","studentCollege":"计算机科学学院","studentId":1,"studentIdcard":"510000000000000000","studentName":"学生1","studentPassword":"91u1t133t3qsyyy7i1u71913ras3t37u3","studentPicture":"/headpicture/","studentProfession":"电子科学与技术专业","studentRemark":"无","studentSex":"男","studentTelphone":"13800000000"}
     * status : 0
     */

    private String message;
    /**
     * classId : 3
     * staff : {"administrationId":0,"staffId":1,"staffIsinpost":false}
     * studentAccount : X1001
     * studentBatchNumber : -
     * studentClass : 计科系16级1班
     * studentCollege : 计算机科学学院
     * studentId : 1
     * studentIdcard : 510000000000000000
     * studentName : 学生1
     * studentPassword : 91u1t133t3qsyyy7i1u71913ras3t37u3
     * studentPicture : /headpicture/
     * studentProfession : 电子科学与技术专业
     * studentRemark : 无
     * studentSex : 男
     * studentTelphone : 13800000000
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
        private String classId;
        /**
         * administrationId : 0
         * staffId : 1
         * staffIsinpost : false
         */

        private StaffBean staff;
        private String studentAccount;
        private String studentBatchNumber;
        private String studentClass;
        private String studentCollege;
        private String studentId;
        private String studentIdcard;
        private String studentName;
        private String studentPassword;
        private String studentPicture;
        private String studentProfession;
        private String studentRemark;
        private String studentSex;
        private String studentTelphone;
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public StaffBean getStaff() {
            return staff;
        }

        public void setStaff(StaffBean staff) {
            this.staff = staff;
        }

        public String getStudentAccount() {
            return studentAccount;
        }

        public void setStudentAccount(String studentAccount) {
            this.studentAccount = studentAccount;
        }

        public String getStudentBatchNumber() {
            return studentBatchNumber;
        }

        public void setStudentBatchNumber(String studentBatchNumber) {
            this.studentBatchNumber = studentBatchNumber;
        }

        public String getStudentClass() {
            return studentClass;
        }

        public void setStudentClass(String studentClass) {
            this.studentClass = studentClass;
        }

        public String getStudentCollege() {
            return studentCollege;
        }

        public void setStudentCollege(String studentCollege) {
            this.studentCollege = studentCollege;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStudentIdcard() {
            return studentIdcard;
        }

        public void setStudentIdcard(String studentIdcard) {
            this.studentIdcard = studentIdcard;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentPassword() {
            return studentPassword;
        }

        public void setStudentPassword(String studentPassword) {
            this.studentPassword = studentPassword;
        }

        public String getStudentPicture() {
            return studentPicture;
        }

        public void setStudentPicture(String studentPicture) {
            this.studentPicture = studentPicture;
        }

        public String getStudentProfession() {
            return studentProfession;
        }

        public void setStudentProfession(String studentProfession) {
            this.studentProfession = studentProfession;
        }

        public String getStudentRemark() {
            return studentRemark;
        }

        public void setStudentRemark(String studentRemark) {
            this.studentRemark = studentRemark;
        }

        public String getStudentSex() {
            return studentSex;
        }

        public void setStudentSex(String studentSex) {
            this.studentSex = studentSex;
        }

        public String getStudentTelphone() {
            return studentTelphone;
        }

        public void setStudentTelphone(String studentTelphone) {
            this.studentTelphone = studentTelphone;
        }

        public static class StaffBean {
            private int administrationId;
            private String staffId;
            private boolean staffIsinpost;

            public int getAdministrationId() {
                return administrationId;
            }

            public void setAdministrationId(int administrationId) {
                this.administrationId = administrationId;
            }

            public String getStaffId() {
                return staffId;
            }

            public void setStaffId(String staffId) {
                this.staffId = staffId;
            }

            public boolean isStaffIsinpost() {
                return staffIsinpost;
            }

            public void setStaffIsinpost(boolean staffIsinpost) {
                this.staffIsinpost = staffIsinpost;
            }
        }
    }
}
