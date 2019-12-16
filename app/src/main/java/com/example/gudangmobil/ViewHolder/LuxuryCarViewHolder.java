package com.example.gudangmobil.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gudangmobil.Models.LuxuryCar;
import com.example.gudangmobil.R;

public class LuxuryCarViewHolder extends RecyclerView.ViewHolder {

    public TextView tvNama;
    public TextView tvNopol;
    public TextView tvWarna;
    public ImageView ivGambar;

    public LuxuryCarViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNama = itemView.findViewById(R.id.tv_nama);
        tvNopol = itemView.findViewById(R.id.tv_nopol);
        tvWarna = itemView.findViewById(R.id.tv_warna);
        ivGambar = itemView.findViewById(R.id.iv_gambar);
    }

    public void bindToLuxuryCar(LuxuryCar luxuryCar){
        tvNama.setText(luxuryCar.getNama());
        tvNopol.setText(luxuryCar.getNopol());
        tvWarna.setText(String.valueOf(luxuryCar.getWarna()));
//        ivGambar.setImageResource(tanaman.gambar);
    }
}
