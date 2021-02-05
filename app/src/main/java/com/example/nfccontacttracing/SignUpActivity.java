package com.example.nfccontacttracing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

   private TextInputEditText eEmail;
   private TextInputEditText ePassword;
   private TextInputEditText ePhoneNum;
   private TextInputEditText eName;
   private Button btnSignUp;
   private Button btnSignIn;
   FirebaseAuth mFirebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        eEmail = findViewById(R.id.etEmailAddress);
        eName = findViewById(R.id.etName);
        ePhoneNum = findViewById(R.id.etPhoneNum);
        ePassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        mFirebaseAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(v -> {

            String inputPhone = ePhoneNum.getText().toString();
            String inputName = eName.getText().toString();
            String inputEmail = eEmail.getText().toString();
            String inputPassword = ePassword.getText().toString();

            if (inputEmail.isEmpty()) {
                eEmail.setError("Please Enter An Email");
                eEmail.requestFocus();
            }
            else if(inputPassword.isEmpty()){
                ePassword.setError("Please Enter A Password");
                ePassword.requestFocus();
            }
            else if(inputName.isEmpty()){
                ePassword.setError("Please Enter Your Name");
                ePassword.requestFocus();
            }
            else if(inputPhone.isEmpty()){
                ePassword.setError("Please Enter Your Phone Number");
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
        btnSignIn.setOnClickListener(v -> {
            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(i);
        });


    }
    }




