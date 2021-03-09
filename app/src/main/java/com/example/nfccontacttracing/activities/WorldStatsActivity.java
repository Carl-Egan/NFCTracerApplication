package com.example.nfccontacttracing.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nfccontacttracing.R;

import org.json.JSONException;

import java.text.NumberFormat;
import java.util.Objects;

public class WorldStatsActivity extends AppCompatActivity {

    String totalCasesWorldWide;
    String newCasesWorldWide;
    String totalActiveWorldWide;
    String totalRecoveredWorldWide;
    String newRecoveredWorldWide;
    String totalDeathsWorldWide;
    String newDeathsWorldWide;
    String testsWorldWide;

    TextView textView_confirmed, textView_confirmed_new, textView_totalActive, textView_totalRecovered, textView_totalRecovered_new, textView_death, textView_death_new, textView_tests;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;
    public static int confirmation = 0;
    public static boolean isRefreshed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_statistics);

        textView_confirmed = findViewById(R.id.worldwide_confirmed_txt);
        textView_confirmed_new = findViewById(R.id.worldwide_new_confirmed_txt);
        textView_totalActive = findViewById(R.id.worldwide_active);
        textView_totalRecovered = findViewById(R.id.world_recovered_textView);
        textView_totalRecovered_new = findViewById(R.id.worldwide_new_recovered);
        textView_death = findViewById(R.id.worldwide_death);
        textView_death_new = findViewById(R.id.worldwide_new_deaths);
        textView_tests = findViewById(R.id.worldwide_tests);
        swipeRefreshLayout = findViewById(R.id.main_refreshLayout);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("World COVID-19 Stats");

        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.blue));
        showProgressDialog();
        fetchData();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            isRefreshed = true;
            fetchData();
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(WorldStatsActivity.this, "Data refreshed!", Toast.LENGTH_SHORT).show();
        });
    }

    public void fetchData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://corona.lmao.ninja/v2/all";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiUrl, null, response -> {
            try {
                if (isRefreshed){
                    totalCasesWorldWide = response.getString("cases");
                    newCasesWorldWide = response.getString("todayCases");
                    totalActiveWorldWide = response.getString("active");
                    totalRecoveredWorldWide = response.getString("recovered");
                    newRecoveredWorldWide = response.getString("todayRecovered");
                    totalDeathsWorldWide = response.getString("deaths");
                    newDeathsWorldWide = response.getString("todayDeaths");
                    testsWorldWide = response.getString("tests");
                    textView_confirmed.setText(totalCasesWorldWide);

                    Runnable progressRunnable = () -> {
                        progressDialog.cancel();

                        int confirmedInt = Integer.parseInt(totalCasesWorldWide);
                        totalCasesWorldWide = NumberFormat.getInstance().format(confirmedInt);
                        textView_confirmed.setText(totalCasesWorldWide);

                        int newCasesInt = Integer.parseInt(newCasesWorldWide);
                        newCasesWorldWide = NumberFormat.getInstance().format(newCasesInt);
                        textView_confirmed_new.setText("+" + newCasesWorldWide);

                        int totalActiveInt = Integer.parseInt(totalActiveWorldWide);
                        totalActiveWorldWide = NumberFormat.getInstance().format(totalActiveInt);
                        textView_totalActive.setText(totalActiveWorldWide);

                        int totalRecoveredInt = Integer.parseInt(totalRecoveredWorldWide);
                        totalRecoveredWorldWide = NumberFormat.getInstance().format(totalRecoveredInt);
                        textView_totalRecovered.setText(totalRecoveredWorldWide);

                        int totalRecoveredNewInt = Integer.parseInt(newRecoveredWorldWide);
                        newRecoveredWorldWide = NumberFormat.getInstance().format(totalRecoveredNewInt);
                        textView_totalRecovered_new.setText("+" + newRecoveredWorldWide);

                        int totalDeceasedInt = Integer.parseInt(totalDeathsWorldWide);
                        totalDeathsWorldWide = NumberFormat.getInstance().format(totalDeceasedInt);
                        textView_death.setText(totalDeathsWorldWide);

                        int totalDeceasedNewInt = Integer.parseInt(newDeathsWorldWide);
                        newDeathsWorldWide = NumberFormat.getInstance().format(totalDeceasedNewInt);
                        textView_death_new.setText("+" + newDeathsWorldWide);

                        int testsInt = Integer.parseInt(testsWorldWide);
                        testsWorldWide = NumberFormat.getInstance().format(testsInt);
                        textView_tests.setText(testsWorldWide);

                    };
                    Handler pdCanceller = new Handler();
                    pdCanceller.postDelayed(progressRunnable, 0);
                } else {
                    totalCasesWorldWide = response.getString("cases");
                    newCasesWorldWide = response.getString("todayCases");
                    totalActiveWorldWide = response.getString("active");
                    totalRecoveredWorldWide = response.getString("recovered");
                    newRecoveredWorldWide = response.getString("todayRecovered");
                    totalDeathsWorldWide = response.getString("deaths");
                    newDeathsWorldWide = response.getString("todayDeaths");
                    testsWorldWide = response.getString("tests");
                    textView_confirmed.setText(totalCasesWorldWide);

                    if (!testsWorldWide.isEmpty()){
                        Runnable progressRunnable = () -> {
                            progressDialog.cancel();

                            int confirmedInt = Integer.parseInt(totalCasesWorldWide);
                            totalCasesWorldWide = NumberFormat.getInstance().format(confirmedInt);
                            textView_confirmed.setText(totalCasesWorldWide);

                            int newCasesInt = Integer.parseInt(newCasesWorldWide);
                            newCasesWorldWide = NumberFormat.getInstance().format(newCasesInt);
                            textView_confirmed_new.setText("+" + newCasesWorldWide);

                            int totalActiveInt = Integer.parseInt(totalActiveWorldWide);
                            totalActiveWorldWide = NumberFormat.getInstance().format(totalActiveInt);
                            textView_totalActive.setText(totalActiveWorldWide);

                            int totalRecoveredInt = Integer.parseInt(totalRecoveredWorldWide);
                            totalRecoveredWorldWide = NumberFormat.getInstance().format(totalRecoveredInt);
                            textView_totalRecovered.setText(totalRecoveredWorldWide);

                            int totalRecoveredNewInt = Integer.parseInt(newRecoveredWorldWide);
                            newRecoveredWorldWide = NumberFormat.getInstance().format(totalRecoveredNewInt);
                            textView_totalRecovered_new.setText("+" + newRecoveredWorldWide);

                            int totalDeceasedInt = Integer.parseInt(totalDeathsWorldWide);
                            totalDeathsWorldWide = NumberFormat.getInstance().format(totalDeceasedInt);
                            textView_death.setText(totalDeathsWorldWide);

                            int totalDeceasedNewInt = Integer.parseInt(newDeathsWorldWide);
                            newDeathsWorldWide = NumberFormat.getInstance().format(totalDeceasedNewInt);
                            textView_death_new.setText("+" + newDeathsWorldWide);

                            int testsInt = Integer.parseInt(testsWorldWide);
                            testsWorldWide = NumberFormat.getInstance().format(testsInt);
                            textView_tests.setText(testsWorldWide);

                        };
                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable, 0);
                        confirmation = 1;
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        requestQueue.add(jsonObjectRequest);

    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(WorldStatsActivity.this);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (confirmation != 1) {
                    progressDialog.cancel();
                    Toast.makeText(WorldStatsActivity.this, "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}