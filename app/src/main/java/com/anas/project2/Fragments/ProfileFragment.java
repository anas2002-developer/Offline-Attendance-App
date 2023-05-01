package com.anas.project2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProfileFragment extends Fragment {

    TextView Profile_txtName;
    TextView Profile_txtEmail;
    TextView Profile_txtUid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Profile_txtName = view.findViewById(R.id.Profile_txtName);
        Profile_txtEmail = view.findViewById(R.id.Profile_txtEmail);
        Profile_txtUid = view.findViewById(R.id.Profile_txtUid);

        Session session = new Session(this.getActivity());

        Profile_txtName.setText(session.infoUser("SP_NAME"));

        Profile_txtUid.setText(session.infoUser("SP_UID"));





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