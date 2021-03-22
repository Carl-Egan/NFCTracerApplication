package com.example.nfccontacttracing.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nfccontacttracing.R;
import com.example.nfccontacttracing.models.History;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HistoryActivity extends AppCompatActivity {

    BottomNavigationView navigation;
    FloatingActionButton floatingActionButton;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView location_list;
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        firebaseFirestore = FirebaseFirestore.getInstance();
        navigation = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);
        location_list = findViewById(R.id.location_list);

        Query query = firebaseFirestore.collection("history");

        FirestoreRecyclerOptions<History> options = new FirestoreRecyclerOptions.Builder<History>()
                .setQuery(query,History.class)
                .build();

       adapter = new FirestoreRecyclerAdapter<History, HistroyViewHolder>(options) {
            @NonNull
            @Override
            public HistroyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_single,parent,false);
                return new HistroyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull HistroyViewHolder holder, int i, @NonNull History history) {
                holder.list_location.setText(history.getLocation());
                holder.list_timestamp.setText(history.getTimestamp());
                holder.list_description.setText(history.getDescription());
            }
        };

        location_list.setHasFixedSize(true);
        location_list.setLayoutManager(new LinearLayoutManager(this));
        location_list.setAdapter(adapter);

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent intent1 = new Intent(HistoryActivity.this, HomePageActivity.class);
                    startActivity(intent1);
                    break;

                case R.id.news:
                    Intent intent2 = new Intent(HistoryActivity.this, NewsActivity.class);
                    startActivity(intent2);
                    break;

                case R.id.history:
                    Toast.makeText(HistoryActivity.this, "Already On History Page", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.profile:
                    Intent intent4 = new Intent(HistoryActivity.this, ProfileActivity.class);
                    startActivity(intent4);
                    break;


            }
            return false;
        });

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, NFCReader.class);
            startActivity(intent);
        });
    }

    private class HistroyViewHolder extends RecyclerView.ViewHolder {
        private TextView list_location;
        private TextView list_timestamp;
        private TextView list_description;
        public HistroyViewHolder (@NonNull View itemView){
            super(itemView);
            list_description = itemView.findViewById(R.id.description);
            list_location = itemView.findViewById(R.id.location);
            list_timestamp = itemView.findViewById(R.id.timestamp);
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }
}
