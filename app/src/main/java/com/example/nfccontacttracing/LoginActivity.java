package com.example.nfccontacttracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText eEmail;
    private TextInputEditText ePassword;
    private Button btnSignIn;
    private Button btnSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eEmail = findViewById(R.id.etEmailAddress);
        ePassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        mFirebaseAuth = FirebaseAuth.getInstance();
        btnSignUp = findViewById(R.id.btnSignUp);

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
        btnSignUp.setOnClickListener(v -> {
            Intent intSignUp = new Intent (LoginActivity.this, SignUpActivity.class);
            startActivity(intSignUp);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}