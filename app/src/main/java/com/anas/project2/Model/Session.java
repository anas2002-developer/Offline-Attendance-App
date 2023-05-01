package com.anas.project2.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.anas.project2.LoginActivty;

public class Session {

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private final  String SP_SNAME = "Attendify";


    public Session(Context context) {
        this.context = context;
        sp=context.getSharedPreferences(SP_SNAME,Context.MODE_PRIVATE);
        editor=sp.edit();
    }

    public void saveUser(String email, String pass){

//        editor.putString("SP_NAME",name);
//        editor.putString("SP_UIDp'",uid);
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
