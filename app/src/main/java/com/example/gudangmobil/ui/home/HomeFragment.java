package com.example.gudangmobil.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gudangmobil.Activities.AddCarActivity;
import com.example.gudangmobil.Activities.LuxuryCarActivity;
import com.example.gudangmobil.MainMenuActivity;
import com.example.gudangmobil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        MainMenuActivity mainmenu = (MainMenuActivity)getActivity();

        ImageView btnLux = (ImageView) root.findViewById(R.id.buttonLuxury);
        ImageView btnJdm = (ImageView) root.findViewById(R.id.buttonJdm);
        ImageView btnBsc = (ImageView) root.findViewById(R.id.buttonBasic);

        FloatingActionButton add = (FloatingActionButton) root.findViewById(R.id.float_add);

        btnLux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LuxuryCarActivity.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCarActivity.class);
                startActivity(intent);
            }
        });


//        final TextView textView = root.findViewById(R.id.text_home);



//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}