package com.anas.project2.Model;

public class ModelAttendanceFB {

    String stud_name;
    String status;

    public ModelAttendanceFB(String stud_uid, String status) {
        this.stud_name = stud_uid;
        this.status = status;
    }

    public String getStud_name() {
        return stud_name;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
