package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelectEssentialGeneralElectiveBinding;
import com.example.allclear.databinding.ActivitySelectGaneralElectiveBinding;

public class SelectGaneralElectiveActivity extends AppCompatActivity {

    private ActivitySelectGaneralElectiveBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectGaneralElectiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}