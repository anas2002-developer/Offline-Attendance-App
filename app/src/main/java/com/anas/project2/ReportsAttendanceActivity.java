package com.anas.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.anas.project2.Adapters.AdapterReportAttendanceRV;
import com.anas.project2.Model.ModelReportAttendance;
import com.anas.project2.Model.ModelReport;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class ReportsAttendanceActivity extends AppCompatActivity {

    String date_room_id;
    String sec_name;
    String sub_name;
    String date;

    AdapterReportAttendanceRV adapterReportAttendanceRV;
    RecyclerView vRV_ReportAttendance;

    TextView txtRA_title;
    TextView txtRA_desc;
    TextView txtRA_desc2;

    Realm realm;
    RealmResults<ModelReportAttendance> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_attendance);

        date_room_id = getIntent().getStringExtra("date_room_id");
        sec_name = getIntent().getStringExtra("sec_name");
        sub_name = getIntent().getStringExtra("sub_name");
        date = getIntent().getStringExtra("date");


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
        results = realm.where(ModelReportAttendance.class)
                .equalTo("date_room_id", date_room_id)
                .sort("stud_name", Sort.ASCENDING)
                .findAllAsync();


        Toolbar toolbarReportAttendance = findViewById(R.id.toolbarReportAttendance);
        setSupportActionBar(toolbarReportAttendance);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        vRV_ReportAttendance = findViewById(R.id.vRV_ReportAttendance);
        txtRA_title = findViewById(R.id.txtRA_title);
        txtRA_desc = findViewById(R.id.txtRA_desc);
        txtRA_desc2 = findViewById(R.id.txtRA_desc2);

        txtRA_title.setText(sub_name);
        txtRA_desc.setText(sec_name);
        txtRA_desc2.setText(date);


        vRV_ReportAttendance.setLayoutManager(new LinearLayoutManager(this));

        adapterReportAttendanceRV = new AdapterReportAttendanceRV(results,date_room_id,sec_name+"_"+sub_name,date);
        vRV_ReportAttendance.setAdapter(adapterReportAttendanceRV);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}