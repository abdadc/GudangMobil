package com.example.gudangmobil.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.gudangmobil.Models.Car;
import com.example.gudangmobil.R;
import com.example.gudangmobil.ViewHolder.CarViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CarActivity extends AppCompatActivity {

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Car, CarViewHolder> mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    String jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxury_car);

        Intent intent = getIntent();
        jenis = intent.getStringExtra("jenis");

        mDatabase = FirebaseDatabase.getInstance().getReference().child(jenis);

        recyclerView = findViewById(R.id.list_luxuryCar);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        Query query = mDatabase;

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Car>()
                .setQuery(query, Car.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Car, CarViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CarViewHolder carViewHolder, int i, @NonNull final Car car) {
                carViewHolder.bindToLuxuryCar(car);
                Glide.with(CarActivity.this).load(car.getGambar()).into(carViewHolder.ivGambar);
                carViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent move = new Intent(CarActivity.this, EditCarActivity.class);
                        move.putExtra("nama", car.getNama());
                        move.putExtra("jenis", jenis);
                        startActivity(move);
                    }
                });
            }

            @NonNull
            @Override
            public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new CarViewHolder(inflater.inflate(R.layout.item_luxurycar, parent, false));
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
