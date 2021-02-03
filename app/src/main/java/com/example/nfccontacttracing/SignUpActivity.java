package com.example.nfccontacttracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

   private EditText eEmail;
   private EditText ePassword;
   private Button btnSignUp;
   TextView tvSignIn;
   FirebaseAuth mFirebaseAuth;


    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eEmail = findViewById(R.id.etEmailAddress);
        ePassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        mFirebaseAuth = FirebaseAuth.getInstance();
        tvSignIn = findViewById(R.id.textView);

        btnSignUp.setOnClickListener(v -> {

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
            Toast.makeText(SignUpActivity.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();

            }
            else if (!(inputEmail.isEmpty() && inputPassword.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();

                            } else {
                                startActivity(new Intent(SignUpActivity.this, HomePageActivity.class));
                            }
                        }
                    });
                }
           else{
                Toast.makeText(SignUpActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();

             }
            });
        tvSignIn.setOnClickListener(v -> {
            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(i);
        });


    }
    }




