package com.anas.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.anas.project2.Model.Session;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class SliderActivity extends AppCompatActivity {

    ImageSlider slider2;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Session session=new Session(getApplicationContext());

        slider2 = findViewById(R.id.slider2);
        skip = findViewById(R.id.skip);

        ArrayList<SlideModel> arrSlides = new ArrayList<>();

        arrSlides.add(new SlideModel(R.drawable.img, ScaleTypes.FIT));
        arrSlides.add(new SlideModel(R.drawable.img, ScaleTypes.FIT));
        arrSlides.add(new SlideModel(R.drawable.img, ScaleTypes.FIT));
        arrSlides.add(new SlideModel(R.drawable.img, ScaleTypes.FIT));

        slider2.setImageList(arrSlides, ScaleTypes.FIT);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SliderActivity.this, LoginActivty.class));
            }
        });
    }
}
