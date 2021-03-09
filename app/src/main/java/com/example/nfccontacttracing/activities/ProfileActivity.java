package com.example.nfccontacttracing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nfccontacttracing.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    Button btnLogout , btnUpdate;
    BottomNavigationView navigation;
    FloatingActionButton floatingActionButton;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String userId;
    ImageView dropdown;

    TextView fullName , email , phone;
    TextInputEditText edit_name, edit_number, edit_email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dropdown = findViewById(R.id.dropdown);
        btnUpdate = findViewById(R.id.update);
        navigation = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);

        phone= findViewById(R.id.profilePhoneNum);
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        edit_name = findViewById(R.id.edit_name);
        edit_number = findViewById(R.id.edit_number);
        edit_email = findViewById(R.id.edit_email);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (documentSnapshot, e) -> {
            //Displaying info for user
            phone.setText(documentSnapshot.getString("phone"));
            fullName.setText(documentSnapshot.getString("name"));
            email.setText(documentSnapshot.getString("email"));

            //Displaying info for amendment
            edit_number.setText(documentSnapshot.getString("phone"));
            edit_name.setText(documentSnapshot.getString("name"));
            edit_email.setText(documentSnapshot.getString("email"));


        });


        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    Intent intent = new Intent(ProfileActivity.this , HomePageActivity.class);
                    startActivity(intent);
                    break;

                case R.id.profile:
                    Toast.makeText(ProfileActivity.this,"Already On Profile",Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.news:
                    Intent intent2 = new Intent(ProfileActivity.this, NewsActivity.class);
                    startActivity(intent2);
                    break;
            }
            return false;
        });

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, NFCReader.class);
            startActivity(intent);
        });


        dropdown.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intToMain = new Intent(ProfileActivity.this, SignUpActivity.class);
            startActivity(intToMain);
        });

        btnUpdate.setOnClickListener(v -> {

        });
    }
}
