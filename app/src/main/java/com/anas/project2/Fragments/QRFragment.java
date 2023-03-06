package com.anas.project2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anas.project2.QRScanner;
import com.anas.project2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRFragment extends Fragment {


    EditText qr_eGenerate;
    FloatingActionButton qr_fabScan;
    FloatingActionButton qr_fabGenerate;
    ImageView qr_img;

    public static TextView txtScanned;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_q_r, container, false);


        qr_eGenerate = view.findViewById(R.id.qr_eGenerate);
        qr_fabGenerate = view.findViewById(R.id.qr_fabGenerate);
        qr_fabScan = view.findViewById(R.id.qr_fabScan);
        qr_img = view.findViewById(R.id.qr_img);
        txtScanned = view.findViewById(R.id.txtScanned);

        qr_fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QRScanner.class));
            }
        });

        qr_fabGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Gqr = qr_eGenerate.getText().toString().trim();
                MultiFormatWriter writer = new MultiFormatWriter();

                try {
                    BitMatrix matrix = writer.encode(Gqr, BarcodeFormat.QR_CODE,
                            260,320);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    qr_img.setImageBitmap(bitmap);
                    InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );
                    manager.hideSoftInputFromWindow(qr_eGenerate.getApplicationWindowToken(),
                            0);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }

}