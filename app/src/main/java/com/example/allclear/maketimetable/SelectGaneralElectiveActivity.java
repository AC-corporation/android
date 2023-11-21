package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.R;
-import com.example.allclear.databinding.ActivitySelectGaneralElectiveBinding;

public class SelectGaneralElectiveActivity extends AppCompatActivity {
    private ActivitySelectGaneralElectiveBinding binding;

    private ActivitySelectGaneralElectiveBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelectGaneralElectiveBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectGaneralElectiveActivity.this,SelfAddOneActivity.class);
                startActivity(intent);
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