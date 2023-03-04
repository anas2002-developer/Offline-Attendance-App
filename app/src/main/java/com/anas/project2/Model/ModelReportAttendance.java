package com.anas.project2.Model;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ModelReportAttendance extends RealmObject {

    String stud_name;
    String stud_uid;
    String status;
    String room_id;
    String date_room_id;

    @PrimaryKey
    String unik_id;

    public ModelReportAttendance() {

    }

    public ModelReportAttendance(String stud_name, String stud_uid, String status, String room_id, String date_room_id, String unik_id) {
        this.stud_name = stud_name;
        this.stud_uid = stud_uid;
        this.status = status;
        this.room_id = room_id;
        this.date_room_id = date_room_id;
        this.unik_id = unik_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getDate_room_id() {
        return date_room_id;
    }

    public void setDate_room_id(String date_room_id) {
        this.date_room_id = date_room_id;
    }

    public String getUnik_id() {
        return unik_id;
    }

    public void setUnik_id(String unik_id) {
        this.unik_id = unik_id;
    }
}
