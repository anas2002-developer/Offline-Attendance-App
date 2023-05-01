package com.anas.project2.Model;

public class ModelSecFB {

    String sec_name;
    String sub_name;

    public ModelSecFB(String sec_name, String sub_name) {
        this.sec_name = sec_name;
        this.sub_name = sub_name;
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
