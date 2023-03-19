package com.anas.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anas.project2.Model.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivty extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String name;
    String uid;
    String email;
    String password;

    AppCompatButton login_btnLogin;
    EditText login_eEmail;
    EditText login_ePass;
    TextView login_txtSignUp;
    TextView login_txtForgotPass;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        login_btnLogin=findViewById(R.id.login_btnLogin);
        login_eEmail=findViewById(R.id.login_eEmail);
        login_ePass=findViewById(R.id.login_ePass);
        login_txtSignUp=findViewById(R.id.login_txtSignUp);
        login_txtForgotPass=findViewById(R.id.login_txtForgotPass);








        Spinner spinner = findViewById(R.id.login_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.details, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }


    public void login2(View v){


        name = getIntent().getStringExtra("name");
        uid = getIntent().getStringExtra("uid");
        email=login_eEmail.getText().toString().trim();
        password=login_ePass.getText().toString().trim();

        Session session = new Session(getApplicationContext());
        session.saveUser(name,uid,email,password);

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(LoginActivty.this,MainActivity.class);
                            i.putExtra("email",mAuth.getCurrentUser().getEmail());
                            startActivity(i);

                        } else {
                            login_eEmail.setText("");
                            login_ePass.setText("");
                            Toast.makeText(LoginActivty.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void forgotpass2(View v){
        startActivity(new Intent(LoginActivty.this,ForgotActivity.class));
    }

    public void signup2(View v){
        startActivity(new Intent(LoginActivty.this,SignUpActivity.class));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}