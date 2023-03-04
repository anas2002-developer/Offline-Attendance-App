package com.anas.project2.Model;

import io.realm.RealmObject;

public class ModelStud extends RealmObject {

    String id;
    String stud_name;
    String stud_uid;
    String room_id;

    public ModelStud() {
    }


    public ModelStud(String id, String stud_name, String stud_uid, String room_id) {
        this.id = id;
        this.stud_name = stud_name;
        this.stud_uid = stud_uid;
        this.room_id = room_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
