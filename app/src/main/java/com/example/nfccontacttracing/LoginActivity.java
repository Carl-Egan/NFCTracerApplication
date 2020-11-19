package com.example.nfccontacttracing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText eEmail;
    private EditText ePassword;
    private Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eEmail = findViewById(R.id.etEmailAddress);
        ePassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignUp);
        mFirebaseAuth = FirebaseAuth.getInstance();
        tvSignUp = findViewById(R.id.textView);

        mAuthStateListener = firebaseAuth -> {
            FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

            if(mFirebaseUser != null){
                Toast.makeText(LoginActivity.this, "You are logged in",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(i);
            }
            else{
                Toast.makeText(LoginActivity.this, "Please Log in",Toast.LENGTH_SHORT).show();

            }
        };
        btnSignIn.setOnClickListener(v -> {
            String inputEmail = eEmail.getText().toString();
            String inputPassword = ePassword.getText().toString();
            if (inputEmail.isEmpty()) {
                eEmail.setError("Please enter email id");
                eEmail.requestFocus();
            }
            else if(inputPassword.isEmpty()){
                ePassword.setError("Please enter your password");
                ePassword.requestFocus();
            }
            else if(inputEmail.isEmpty() && inputPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();

            }
            else if (!(inputEmail.isEmpty() && inputPassword.isEmpty())){
                mFirebaseAuth.signInWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(LoginActivity.this, task -> {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Error , Please Login Again",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Intent intToHome = new Intent(LoginActivity.this , HomePageActivity.class);
                        startActivity(intToHome);
                    }
                });
            }
            else{
                Toast.makeText(LoginActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();

            }
        });
        tvSignUp.setOnClickListener(v -> {
            Intent intSignUp = new Intent (LoginActivity.this, MainActivity.class);
            startActivity(intSignUp);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}