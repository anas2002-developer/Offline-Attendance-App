package com.anas.project2.Model;

public class ModelProfileFB {

    String username;
    String eid;

    public ModelProfileFB(String username, String eid) {
        this.username = username;
        this.eid = eid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }
}
