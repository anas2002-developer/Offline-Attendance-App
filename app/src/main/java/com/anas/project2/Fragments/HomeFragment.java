package com.anas.project2.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.anas.project2.Adapters.AdapterSecRV;
import com.anas.project2.Model.ModelSec;
import com.anas.project2.Model.ModelSecFB;
import com.anas.project2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class HomeFragment extends Fragment implements View.OnClickListener {


    TextView txtSec_title;
    TextView txtSec_desc;
    FloatingActionButton fabSec;
    RecyclerView vRV;

    LottieAnimationView lottie;

    AdapterSecRV adapterSecRV;

    Realm realm;
    RealmResults<ModelSec> results;
    RealmAsyncTask transaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Realm.init(getActivity());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .allowWritesOnUiThread(true)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(realmConfiguration);

        txtSec_title = view.findViewById(R.id.txtSec_title);
        txtSec_desc = view.findViewById(R.id.txtSec_desc);
        fabSec = view.findViewById(R.id.fabSec);
        vRV = view.findViewById(R.id.vRV);
        vRV.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));

        fabSec.setOnClickListener(this::onClick);

        results = realm.where(ModelSec.class)
                .findAll();

        adapterSecRV = new AdapterSecRV(results);
        vRV.setAdapter(adapterSecRV);


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabSec:
                CreateSec();
                break;
        }
    }

    private void CreateSec() {

        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_sec2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.dialog_bg));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        EditText eSec_name = dialog.findViewById(R.id.eSec_name);
        EditText eSub_name = dialog.findViewById(R.id.eSub_name);
        Button btnAddSec = dialog.findViewById(R.id.btnAddSec);
        Button btnCancelSec = dialog.findViewById(R.id.btnCancelSec);

        btnAddSec.setOnClickListener(v -> {

            String SecName = eSec_name.getText().toString();
            String SubName = eSub_name.getText().toString();

            if (SecName.equals("")) {
                Toast.makeText(getActivity(), "Blank Field!", Toast.LENGTH_SHORT).show();
            } else {
                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Creating Section..");
                progressDialog.show();

                transaction = realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int i = 0;
                        ModelSec model = realm.createObject(ModelSec.class);
                        String id = SecName +"_"+ SubName;
                        model.setSec_name(SecName);
                        model.setSub_name(SubName);
                        model.setId(id);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        realm.refresh();
                        realm.setAutoRefresh(true);

                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                });

                ModelSecFB model2 = new ModelSecFB(SecName, SubName);
                FirebaseDatabase.getInstance().getReference().child("ATTENDIFY")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("SECTIONS")
                        .child(SecName + "_" + SubName)
                        .setValue(model2);

                dialog.dismiss();
            }
        });

        btnCancelSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

}