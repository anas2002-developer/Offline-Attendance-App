package com.anas.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AppCompatButton signup_btnSignUp;
    EditText signup_eEmail;
    EditText signup_ePass;
    TextView signup_txtLogin;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        signup_btnSignUp=findViewById(R.id.signup_btnSignUp);
        signup_eEmail=findViewById(R.id.signup_eEmail);
        signup_ePass=findViewById(R.id.signup_ePass);
        signup_txtLogin=findViewById(R.id.signup_txtLogin);



        Spinner spinner = findViewById(R.id.signup_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.details, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    public void  login(View v){

        startActivity(new Intent(SignUpActivity.this,LoginActivty.class));

    }

    public void signup(View v){

        String email = signup_eEmail.getText().toString().trim();
        String password = signup_ePass.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signup_eEmail.setText("");
                            signup_ePass.setText("");
                            Toast.makeText(SignUpActivity.this, "Registred", Toast.LENGTH_SHORT).show();
                        } else {
                            signup_eEmail.setText("");
                            signup_ePass.setText("");
                            Toast.makeText(SignUpActivity.this, "Internet Issue", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}