package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelectEssentialGeneralElectiveBinding;
import com.example.allclear.databinding.ActivitySelectMajorBinding;

public class SelectMajorActivity extends AppCompatActivity {

    private ActivitySelectMajorBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectMajorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectMajorActivity.this,SelectGaneralElectiveActivity.class);
                startActivity(intent);
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}