package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelectMajorBaseBinding;
import com.example.allclear.databinding.ActivitySelectSemesterBinding;

public class SelectMajorBaseActivity extends AppCompatActivity {
    private ActivitySelectMajorBaseBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectMajorBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectMajorBaseActivity.this,SelectEssentialGeneralElectiveActivity.class);
                startActivity(intent);
            }
        });
    }
}