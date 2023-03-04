package com.anas.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.anas.project2.Adapters.AdapterReportRV;
import com.anas.project2.Model.ModelReport;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReportsActivity extends AppCompatActivity {

    String sec_name;
    String sub_name;
    String room_id;

    RealmResults<ModelReport> results;
    RecyclerView vRV_Report;
    AdapterReportRV adapterReportRV;

    TextView txtReport_title;

    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        results = realm.where(ModelReport.class)
                .equalTo("room_id", room_id)
                .findAll();

        Toolbar toolbarReport = findViewById(R.id.toolbarReport);
        setSupportActionBar(toolbarReport);
//        toolbarReport.setTitle("Section");
//        toolbarReport.setSubtitle("Subject");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        vRV_Report=findViewById(R.id.vRV_Report);
        txtReport_title=findViewById(R.id.txtReport_title);

        sec_name = getIntent().getStringExtra("sec_name");
        sub_name = getIntent().getStringExtra("sub_name");
        room_id = getIntent().getStringExtra("room_id");




        vRV_Report.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);

        vRV_Report.setLayoutManager(gridLayoutManager);

        adapterReportRV = new AdapterReportRV(results, room_id);
        vRV_Report.setAdapter(adapterReportRV);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}