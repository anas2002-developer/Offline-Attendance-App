package com.anas.project2.Model;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;

public class ModelReport extends RealmObject {

    String date;
    String monthOnly;
    String dateOnly;
    String room_id;
    String date_room_id;
    String sec_name;
    String sub_name;
    RealmList<ModelReportAttendance> attendance_list;

    public ModelReport() {
    }


    public ModelReport(String date, String monthOnly, String dateOnly, String room_id, String date_room_id, String sec_name, String sub_name, RealmList<ModelReportAttendance> attendance_list) {
        this.date = date;
        this.monthOnly = monthOnly;
        this.dateOnly = dateOnly;
        this.room_id = room_id;
        this.date_room_id = date_room_id;
        this.sec_name = sec_name;
        this.sub_name = sub_name;
        this.attendance_list = attendance_list;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonthOnly() {
        return monthOnly;
    }

    public void setMonthOnly(String monthOnly) {
        this.monthOnly = monthOnly;
    }

    public String getDateOnly() {
        return dateOnly;
    }

    public void setDateOnly(String dateOnly) {
        this.dateOnly = dateOnly;
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

    public String getSec_name() {
        return sec_name;
    }

    public void setSec_name(String sec_name) {
        this.sec_name = sec_name;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public RealmList<ModelReportAttendance> getAttendance_list() {
        return attendance_list;
    }

    public void setAttendance_list(RealmList<ModelReportAttendance> attendance_list) {
        this.attendance_list = attendance_list;
    }
}
