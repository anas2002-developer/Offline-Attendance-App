package com.anas.project2.Model;

import io.realm.RealmObject;

public class ModelSec extends RealmObject {

    String id;
    String sec_name;
    String sub_name;

    public ModelSec() {
    }

    public ModelSec(String id, String sec_name, String sub_name) {
        this.id = id;
        this.sec_name = sec_name;
        this.sub_name = sub_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
