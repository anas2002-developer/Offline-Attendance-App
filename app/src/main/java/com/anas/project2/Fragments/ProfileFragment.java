package com.anas.project2.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anas.project2.Model.Session;
import com.anas.project2.R;

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
        Profile_txtEmail.setText(session.infoUser("SP_EMAIL"));
        Profile_txtUid.setText(session.infoUser("SP_UID"));





        return view;
    }
}