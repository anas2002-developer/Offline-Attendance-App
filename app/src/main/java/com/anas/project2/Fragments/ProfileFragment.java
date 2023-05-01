package com.anas.project2.Fragments;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anas.project2.Model.Session;
import com.anas.project2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    TextView Profile_txtName;
    TextView Profile_txtEmail;
    TextView Profile_txtUid;

    Button btnEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Profile_txtName = view.findViewById(R.id.Profile_txtName);
        Profile_txtEmail = view.findViewById(R.id.Profile_txtEmail);
        Profile_txtUid = view.findViewById(R.id.Profile_txtUid);
        btnEdit = view.findViewById(R.id.btnEdit);

        Session session = new Session(this.getActivity());

        loadDetails();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_profile);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.dialog_bg));
                }
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                EditText username = dialog.findViewById(R.id.username);
                EditText eid = dialog.findViewById(R.id.eid);


                FirebaseDatabase.getInstance().getReference().child("ATTENDIFY").child(FirebaseAuth.getInstance().getUid()).child("PROFILE").get()
                        .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {

                                if (task.isSuccessful()){

                                    if (task.getResult().exists()){
                                        DataSnapshot snapshot = task.getResult();
                                        String name = String.valueOf(snapshot.child("username").getValue());
                                        String age = String.valueOf(snapshot.child("eid").getValue());

                                        username.setText(name);
                                        eid.setText(age);

                                    }


                                }

                            }
                        });


                Button btnSave = dialog.findViewById(R.id.btnSave);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);

                btnSave.setOnClickListener(v1 -> {

                    String USERNAME = username.getText().toString();
                    String EID = eid.getText().toString();

                    if (USERNAME.equals("")){
                        Toast.makeText(getActivity(), "Blank Field!", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Map<String,Object> map = new HashMap<>();
                        map.put("username",USERNAME);
                        map.put("eid",EID);

                        FirebaseDatabase.getInstance().getReference().child("ATTENDIFY").child(FirebaseAuth.getInstance().getUid()).child("PROFILE")
                                .updateChildren(map);

                        Toast.makeText(getActivity(), "Details Saved, Please Restart App", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });


        return view;
    }

    private void loadDetails() {

        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference root = fdb.getReference();

        root.child("ATTENDIFY").child(FirebaseAuth.getInstance().getUid())
                .child("PROFILE").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){

                            if (task.getResult().exists()){
                                DataSnapshot snapshot = task.getResult();
                                String username = String.valueOf(snapshot.child("username").getValue());
                                String eid = String.valueOf(snapshot.child("eid").getValue());

                                Profile_txtName.setText(username);
                                Profile_txtUid.setText(eid);
                                Profile_txtEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


                            }


                        }
                        else {
                            Toast.makeText(getActivity(), "failure loading details", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}