package com.example.nfccontacttracing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nfccontacttracing.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePageActivity extends AppCompatActivity {


    BottomNavigationView navigation;
    FloatingActionButton floatingActionButton;
    Button stats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        stats = findViewById(R.id.stats);

        navigation = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Toast.makeText(HomePageActivity.this, "Already Home", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.news:
                    Intent intent2 = new Intent(HomePageActivity.this, NewsActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.history:
                    Intent intent3 = new Intent(HomePageActivity.this, HistoryActivity.class);
                    startActivity(intent3);
                    break;

                case R.id.profile:
                    Intent intent4 = new Intent(HomePageActivity.this, ProfileActivity.class);
                    startActivity(intent4);
                    break;
            }
            return false;
        });

        stats.setOnClickListener(v ->{
            Intent intent2 = new Intent(HomePageActivity.this, IrelandStatsActivity.class);
            startActivity(intent2);
        });

        floatingActionButton.setOnClickListener(v -> {
            Intent intent2 = new Intent(HomePageActivity.this, NFCReader.class);
            startActivity(intent2);
        });

    }

}
