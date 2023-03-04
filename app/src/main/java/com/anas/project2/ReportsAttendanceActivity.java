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
import io.realm.RealmResults;
import io.realm.Sort;

public class ReportsAttendanceActivity extends AppCompatActivity {

    String room_id;
    String sec_name;
    String sub_name;
    String date;

    AdapterReportAttendanceRV adapterReportAttendanceRV;
    RecyclerView vRV_ReportAttendance;

    TextView txtReportAttendance_title;

    Realm realm;
    RealmResults<ModelReportAttendance> results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_attendance);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Realm.init(this);
        realm = Realm.getDefaultInstance();
        results = realm.where(ModelReportAttendance.class)
                .equalTo("date_room_id", room_id)
                .sort("stud_name", Sort.ASCENDING)
                .findAllAsync();

        room_id = getIntent().getStringExtra("room_id");
        sec_name = getIntent().getStringExtra("sec_name");
        sub_name = getIntent().getStringExtra("sub_name");
        date = getIntent().getStringExtra("date");

        Toolbar toolbarReportAttendance = findViewById(R.id.toolbarReportAttendance);
        setSupportActionBar(toolbarReportAttendance);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        vRV_ReportAttendance = findViewById(R.id.vRV_ReportAttendance);
        txtReportAttendance_title = findViewById(R.id.txtReportAttendance_title);


        vRV_ReportAttendance.setHasFixedSize(true);
        vRV_ReportAttendance.setLayoutManager(new LinearLayoutManager(this));

        adapterReportAttendanceRV = new AdapterReportAttendanceRV(results,room_id);
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