package com.anas.project2.Model;

public class ModelStudFB {

    String stud_name;
    String stud_uid;

    public ModelStudFB(String stud_name, String stud_uid) {
        this.stud_name = stud_name;
        this.stud_uid = stud_uid;
    }

    public String getStud_name() {
        return stud_name;
    }

    public void setStud_name(String stud_name) {
        this.stud_name = stud_name;
    }

    public String getStud_uid() {
        return stud_uid;
    }

    public void setStud_uid(String stud_uid) {
        this.stud_uid = stud_uid;
    }
}
