package com.anas.project2.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.anas.project2.LoginActivty;

public class Session {

    Context context;
    SharedPreferences sp;

    private final static String SP_NAME = "Attendify";
    private SharedPreferences.Editor editor;

    public Session(Context context) {
        this.context = context;
        sp=context.getSharedPreferences("Attendify",Context.MODE_PRIVATE);
        editor=sp.edit();
    }

    public void saveUser(String name, String contact, String email, String pass){

        editor.putString("SP_NAME",name);
        editor.putString("SP_CONTACT",contact);
        editor.putString("SP_EMAIL",email);
        editor.putString("SP_PASS",pass);
        editor.putBoolean("SP_LOGGED_IN",true);
        editor.commit();

    }

    public boolean checkUser(){
        return sp.contains("SP_LOGGED_IN");
    }

    public void logoutUser(){

        editor.clear();
        editor.commit();

        context.startActivity(new Intent(context, LoginActivty.class));

    }

    public String infoUser(String key){
        return sp.getString(key,null);
    }




}
