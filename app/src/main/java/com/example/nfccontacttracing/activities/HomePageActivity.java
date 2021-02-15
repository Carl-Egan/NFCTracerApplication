package com.example.nfccontacttracing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nfccontacttracing.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePageActivity extends AppCompatActivity {


    BottomNavigationView navigation;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        navigation = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Toast.makeText(HomePageActivity.this, "Already Home", Toast.LENGTH_SHORT).show();
                    return true;


                case R.id.profile:
                    Intent intent = new Intent(HomePageActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    break;

                case R.id.news:
                    Intent intent2 = new Intent(HomePageActivity.this, NewsActivity.class);
                    startActivity(intent2);
                    break;
            }
            return false;
        });

        floatingActionButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(HomePageActivity.this, NFCReader.class);
            startActivity(intent2);
        });

    }

}
