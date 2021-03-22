package com.example.nfccontacttracing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nfccontacttracing.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private TextInputEditText eEmail;
    private TextInputEditText ePassword;
    private TextInputEditText ePhoneNum;
    private TextInputEditText eName;
    private Button btnSignUp;
    private Button btnSignIn;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore fStore;
    String userID;



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
        fStore = FirebaseFirestore.getInstance();

        btnSignUp.setOnClickListener(v -> {

            String inputPhone = ePhoneNum.getText().toString();
            String inputName = eName.getText().toString();
            String inputEmail = eEmail.getText().toString();
            String inputPassword = ePassword.getText().toString();

            if(mFirebaseAuth.getCurrentUser() != null){
                startActivity(new Intent (getApplicationContext(), HomePageActivity.class));
                finish();
            }

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
                mFirebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(SignUpActivity.this, task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();

                    } else {
                        userID = mFirebaseAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("users").document(userID);
                        Map<String , Object> user = new HashMap<>();
                        user.put("name",inputName);
                        user.put("email",inputEmail);
                        user.put("phone",inputPhone);
                        documentReference.set(user).addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: user Profile is created for " + userID ));
                        startActivity(new Intent(SignUpActivity.this, HomePageActivity.class));
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