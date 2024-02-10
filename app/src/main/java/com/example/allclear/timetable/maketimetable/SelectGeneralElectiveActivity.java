package com.example.allclear.timetable.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.example.allclear.databinding.ActivitySelectGeneralElectiveBinding;

public class SelectGeneralElectiveActivity extends AppCompatActivity {
    private ActivitySelectGeneralElectiveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelectGeneralElectiveBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(SelectGeneralElectiveActivity.this, EssentialSubjectActivity.class);
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