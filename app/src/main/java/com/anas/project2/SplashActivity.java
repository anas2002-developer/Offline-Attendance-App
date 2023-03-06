package com.anas.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.anas.project2.Model.Session;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Session session=new Session(getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (session.checkUser()){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }
                else {
                    startActivity(new Intent(SplashActivity.this,LoginActivty.class));
                }

            }
        },4000);



    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}