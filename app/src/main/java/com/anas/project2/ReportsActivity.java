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
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ReportsActivity extends AppCompatActivity {

    String sec_name;
    String sub_name;
    String room_id;

    RealmResults<ModelReport> results;
    RecyclerView vRV_Report;
    AdapterReportRV adapterReportRV;

    TextView txtReport_title;
    TextView txtReport_desc;

    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        sec_name = getIntent().getStringExtra("sec_name");
        sub_name = getIntent().getStringExtra("sub_name");
        room_id = getIntent().getStringExtra("room_id");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
        results = realm.where(ModelReport.class)
                .equalTo("room_id", room_id)
                .findAll();

        Toolbar toolbarReport = findViewById(R.id.toolbarReport);
        setSupportActionBar(toolbarReport);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        vRV_Report=findViewById(R.id.vRV_Report);
        txtReport_title=findViewById(R.id.txtReport_title);
        txtReport_desc=findViewById(R.id.txtReport_desc);

        txtReport_title.setText(sub_name);
        txtReport_desc.setText(sec_name);


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