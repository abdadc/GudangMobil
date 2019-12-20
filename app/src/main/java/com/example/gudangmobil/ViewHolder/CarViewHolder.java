package com.example.gudangmobil.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gudangmobil.Models.Car;
import com.example.gudangmobil.R;

public class CarViewHolder extends RecyclerView.ViewHolder {

    public TextView tvNama;
    public TextView tvNopol;
    public TextView tvWarna;
    public ImageView ivGambar;

    public CarViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNama = itemView.findViewById(R.id.tv_nama);
        tvNopol = itemView.findViewById(R.id.tv_nopol);
        tvWarna = itemView.findViewById(R.id.tv_warna);
        ivGambar = itemView.findViewById(R.id.iv_gambar);
    }

    public void bindToLuxuryCar(Car car){
        tvNama.setText(car.getNama());
        tvNopol.setText(car.getNopol());
        tvWarna.setText(String.valueOf(car.getWarna()));
//        ivGambar.setImageResource(tanaman.gambar);
    }
}
