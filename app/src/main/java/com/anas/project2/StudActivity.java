package com.anas.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anas.project2.Adapters.AdapterStudRV;
import com.anas.project2.Model.ModelReportAttendance;
import com.anas.project2.Model.ModelReport;
import com.anas.project2.Model.ModelStud;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class StudActivity extends AppCompatActivity implements View.OnClickListener{

    String sec_name;
    String sub_name;
    String room_id;

    TextView txtStud_title;
    TextView txtStud_desc;
    TextView txtTotalStuds;
    RecyclerView vRV_Stud;
    FloatingActionButton fabStud;

    Button btnReport;
    Button btnExcel;

    AdapterStudRV adapterStudRV;

    Realm realm;
    RealmResults<ModelStud> results;
    RealmAsyncTask transaction;
    RealmChangeListener realmChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud);

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
        results = realm.where(ModelStud.class)
                .findAll();

        txtStud_title = findViewById(R.id.txtStud_title);
        txtStud_desc = findViewById(R.id.txtStud_desc);
        txtTotalStuds = findViewById(R.id.txtTotalStuds);

        fabStud=findViewById(R.id.fabStud);
        btnReport=findViewById(R.id.btnReport);
        btnExcel=findViewById(R.id.btnExcel);
//        btnExcel.setVisibility(View.GONE);
        vRV_Stud = findViewById(R.id.vRV_Stud);
        vRV_Stud.setLayoutManager(new LinearLayoutManager(StudActivity.this));

        txtStud_title.setText(sub_name);
        txtStud_desc.setText(sec_name);

        Toolbar toolbarStud = findViewById(R.id.toolbarStud);
        setSupportActionBar(toolbarStud);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RealmChange();
//        adapterStudRV = new AdapterStudRV(results);
//        vRV_Stud.setAdapter(adapterStudRV);


        fabStud.setOnClickListener(this::onClick);
        btnExcel.setOnClickListener(this::onClick);
        btnReport.setOnClickListener(this::onClick);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.fabStud:
                CreateStudent();
                break;
            case R.id.btnReport:
                ViewReports();
                break;
            case R.id.btnExcel:
                CheckAllTick();
                break;
        }
    }

    private void CheckAllTick() {
        long count = realm.where(ModelStud.class)
                .equalTo("room_id", room_id)
                .count();
        final String size, size2;
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(StudActivity.this);
        size = String.valueOf(preferences.getAll().size());
        size2 = String.valueOf(count);

        if (size.equals(size2)){
            SubmitAttendance();
        }else {
            Toast.makeText(StudActivity.this, "Mark All Students", Toast.LENGTH_SHORT).show();
        }

    }

    private void SubmitAttendance() {

        
            final ProgressDialog progressDialog = new ProgressDialog(StudActivity.this);
            progressDialog.setMessage("Submitting, Please wait..");
            progressDialog.show();
            final String date = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
            final RealmResults<ModelReportAttendance> list_students ;

            list_students = realm.where(ModelReportAttendance.class)
                    .equalTo("date_room_id", date+room_id)
                    .sort("stud_name", Sort.ASCENDING)
                    .findAllAsync();

            final RealmList<ModelReportAttendance> list = new RealmList<>();
            list.addAll(list_students);

            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            final String dateOnly = String.valueOf(calendar.get(Calendar.DATE));
            @SuppressLint("SimpleDateFormat")
            final String monthOnly = new SimpleDateFormat("MMM").format(calendar.getTime());

            try {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        ModelReport model = realm.createObject(ModelReport.class);
                        model.setRoom_id(room_id);
                        model.setAttendance_list(list);
                        model.setDate(date);
                        model.setDateOnly(dateOnly);
                        model.setMonthOnly(monthOnly);
                        model.setDate_room_id(date+room_id);
                        model.setSec_name(sec_name);
                        model.setSub_name(sub_name);

                    }
                });
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(StudActivity.this, "Attendance Submitted", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(StudActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }



    }

    private void ViewReports() {
        Intent intent = new Intent(StudActivity.this, ReportsActivity.class);
        intent.putExtra("sec_name", sec_name);
        intent.putExtra("sub_name", sub_name);
        intent.putExtra("room_id", room_id);
        startActivity(intent);
    }

    private void CreateStudent() {

        Dialog dialog = new Dialog(StudActivity.this);
        dialog.setContentView(R.layout.dialoag_stud2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(StudActivity.this.getDrawable(R.drawable.dialog_bg));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        EditText eStud_name = dialog.findViewById(R.id.eStud_name);
        EditText eStud_uid = dialog.findViewById(R.id.eStud_uid);
        Button btnAddStud = dialog.findViewById(R.id.btnAddStud);
        Button btnCancelStud = dialog.findViewById(R.id.btnCancelStud);

        btnAddStud.setOnClickListener(v -> {

            String StudName = eStud_name.getText().toString();
            String StudUid = eStud_uid.getText().toString();

            if (StudName.equals("")){
                Toast.makeText(StudActivity.this, "Blank Field!", Toast.LENGTH_SHORT).show();
            }
            else {
                final ProgressDialog progressDialog = new ProgressDialog(StudActivity.this);
                progressDialog.setMessage("Creating Student..");
                progressDialog.show();

                transaction = realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int i=0;
                        ModelStud model = realm.createObject(ModelStud.class);
                        String id = StudName+StudUid;
                        model.setId(id);
                        model.setStud_name(StudName);
                        model.setStud_uid(StudUid);
                        model.setRoom_id(room_id);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        realm.refresh();
                        realm.setAutoRefresh(true);
                        Toast.makeText(StudActivity.this, "Successfully created", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        progressDialog.dismiss();
                        Toast.makeText(StudActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.dismiss();
            }
        });

        btnCancelStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Toast.makeText(StudActivity.this, "Create student", Toast.LENGTH_SHORT).show();
    }


    public void RealmChange(){

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);
        final String date = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                long count = realm.where(ModelStud.class)
                        .equalTo("room_id", room_id)
                        .count();
//
                txtTotalStuds.setText("Total Students : " + count);
//
//                long reports_size = realm.where(ModelReport.class)
//                        .equalTo("date_and_classID", date+room_id)
//                        .count();
//                if (!(reports_size==0)){
//                    layout_attendance_taken.setVisibility(View.VISIBLE);
//                    submit_btn.setVisibility(View.GONE);
//                }else {
//                    layout_attendance_taken.setVisibility(View.GONE);
//                    submit_btn.setVisibility(View.VISIBLE);
//
//                    if (!(count==0)){
//                        submit_btn.setVisibility(View.VISIBLE);
//                        place_holder.setVisibility(View.GONE);
//                    }else if (count==0) {
//                        submit_btn.setVisibility(View.GONE);
//                        place_holder.setVisibility(View.VISIBLE);
//                    }

//                }

            }
        };
        realm.addChangeListener(realmChangeListener);
        RealmResults<ModelStud> results ;
        results = realm.where(ModelStud.class)
                .equalTo("room_id", room_id)
                .sort("stud_name", Sort.ASCENDING)
                .findAllAsync();


        long count = realm.where(ModelStud.class)
                .equalTo("room_id", room_id)
                .count();
//        long reports_size = realm.where(ModelReport.class)
//                .equalTo("date_room_id", date+room_id)
//                .count();
//
//
//        if (!(reports_size==0)){
//            layout_attendance_taken.setVisibility(View.VISIBLE);
//            submit_btn.setVisibility(View.GONE);
//        }else if (reports_size==0) {
//
//            layout_attendance_taken.setVisibility(View.GONE);
//            submit_btn.setVisibility(View.VISIBLE);
//
//            if (!(count==0)){
//                submit_btn.setVisibility(View.VISIBLE);
//                place_holder.setVisibility(View.GONE);
//            }else if (count==0){
//                submit_btn.setVisibility(View.GONE);
//                place_holder.setVisibility(View.VISIBLE);
//            }
//        }


        txtTotalStuds.setText("Total Students : " + count);

//        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new StudentsListAdapter( students,ClassDetail_Activity.this, date+room_ID, extraClick);
//        mRecyclerview.setAdapter(mAdapter);

        adapterStudRV = new AdapterStudRV(results,date+room_id);
        vRV_Stud.setAdapter(adapterStudRV);

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
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        super.onDestroy();
    }
}