package com.example.gudangmobil.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.gudangmobil.Models.LuxuryCar;
import com.example.gudangmobil.R;
import com.example.gudangmobil.ViewHolder.LuxuryCarViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class LuxuryCarActivity extends AppCompatActivity {

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<LuxuryCar, LuxuryCarViewHolder> mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_car);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Luxury");

        recyclerView = findViewById(R.id.list_luxuryCar);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        Query query = mDatabase;

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<LuxuryCar>()
                .setQuery(query, LuxuryCar.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<LuxuryCar, LuxuryCarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull LuxuryCarViewHolder tanamanViewHolder, int i, @NonNull LuxuryCar luxuryCar) {
                tanamanViewHolder.bindToLuxuryCar(luxuryCar);
                Glide.with(LuxuryCarActivity.this).load(luxuryCar.getGambar()).into(tanamanViewHolder.ivGambar);
            }

            @NonNull
            @Override
            public LuxuryCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new LuxuryCarViewHolder(inflater.inflate(R.layout.item_luxurycar, parent, false));
            }
        };

        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);

        if (mAdapter != null) {
            mAdapter.startListening();
        }

        System.out.println(mAdapter);
    }
}
