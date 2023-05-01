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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anas.project2.Model.ModelProfileFB;
import com.anas.project2.Model.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    AppCompatButton signup_btnSignUp;
    EditText signup_eName;
    EditText signup_eUid;
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
        signup_eName=findViewById(R.id.signup_eName);
        signup_eUid =findViewById(R.id.signup_eUid);
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

        //saving user details
        String name=signup_eName.getText().toString().trim();
        String eid= signup_eUid.getText().toString().trim();
        String email=signup_eEmail.getText().toString().trim();
        String password=signup_ePass.getText().toString().trim();


        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            ModelProfileFB model = new ModelProfileFB(name,eid);
                            FirebaseDatabase.getInstance().getReference().child("ATTENDIFY")
                                            .child(FirebaseAuth.getInstance().getUid())
                                                    .child("PROFILE")
                                                            .setValue(model);

                            Toast.makeText(SignUpActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUpActivity.this,LoginActivty.class);
//                            i.putExtra("name",name);
//                            i.putExtra("uid",uid);
//                            i.putExtra("email",email);
//                            i.putExtra("password",password);
                            startActivity(i);

                        } else {
                            signup_eEmail.setText("");
                            signup_ePass.setText("");
                            Toast.makeText(SignUpActivity.this, "Internet Issue/already registered", Toast.LENGTH_SHORT).show();
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