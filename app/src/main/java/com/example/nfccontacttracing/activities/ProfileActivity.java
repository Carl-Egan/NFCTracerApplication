package com.example.nfccontacttracing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nfccontacttracing.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    Button btnLogout;
    BottomNavigationView navigation;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.logout);
        navigation = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);


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


        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intToMain = new Intent(ProfileActivity.this, SignUpActivity.class);
            startActivity(intToMain);
        });
    }
}
