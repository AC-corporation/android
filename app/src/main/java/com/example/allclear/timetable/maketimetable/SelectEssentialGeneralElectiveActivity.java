package com.example.allclear.timetable.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.databinding.ActivitySelectEssentialGeneralElectiveBinding;

public class SelectEssentialGeneralElectiveActivity extends AppCompatActivity {

    private ActivitySelectEssentialGeneralElectiveBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectEssentialGeneralElectiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectEssentialGeneralElectiveActivity.this, SelectMajorActivity.class);
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