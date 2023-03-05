package com.anas.project2.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anas.project2.R;

import io.realm.mongodb.sync.Progress;

public class AttendanceTrackerFragment extends Fragment {


    EditText AT_eSub;
    EditText AT_eLA;
    EditText AT_eTA;
    EditText AT_eCriteria;

    TextView AT_txtSub;
    TextView AT_txtLA;
    TextView AT_txtTA;
    TextView AT_txtBunk;
    TextView AT_txtPer;

    ProgressBar AT_progress;

    CardView AT_card;

    AppCompatButton AT_btnCalculate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendance_tracker, container, false);

        AT_eSub = view.findViewById(R.id.AT_eSub);
        AT_eLA = view.findViewById(R.id.AT_eLA);
        AT_eTA = view.findViewById(R.id.AT_eTA);
        AT_eCriteria = view.findViewById(R.id.AT_eCriteria);

        AT_txtSub = view.findViewById(R.id.AT_txtSub);
        AT_txtLA = view.findViewById(R.id.AT_txtLA);
        AT_txtTA = view.findViewById(R.id.AT_txtTA);
        AT_txtBunk = view.findViewById(R.id.AT_txtBunk);
        AT_txtPer = view.findViewById(R.id.AT_txtPer);

        AT_progress = view.findViewById(R.id.AT_progress);
        AT_btnCalculate = view.findViewById(R.id.AT_btnCalculate);
        AT_card = view.findViewById(R.id.AT_card);
        AT_card.setVisibility(View.GONE);

        AT_btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AT_card.setVisibility(View.VISIBLE);


                float LA = Integer.parseInt(AT_eLA.getText().toString().trim());
                float TA = Integer.parseInt(AT_eTA.getText().toString().trim());
                float Criteria = Integer.parseInt(AT_eCriteria.getText().toString().trim());

                AT_txtSub.setText(AT_eSub.getText().toString().trim());
                AT_txtLA.setText(String.format("%.0f",LA)+"    /");
                AT_txtTA.setText(String.format("%.0f",TA));


                double x = (TA*Criteria)/100;
                double bunk = LA - (Math.ceil(x));

                AT_txtBunk.setText("Can Skip : (" + String.format("%.0f",bunk) + ") lectures");

                float percent = (LA/TA)*100;
                AT_txtPer.setText(String.format("%.2f",percent)+"%");
                AT_progress.setProgress((int) percent);


            }
        });











        return view;
    }
}