package com.anas.project2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anas.project2.LoginActivty;
import com.anas.project2.R;
import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment implements View.OnClickListener{


    AppCompatButton btnLogout;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        btnLogout=view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btnLogout:
                logout();
                break;
        }
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivty.class));
    }
}