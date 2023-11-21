package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelfAddTwoBinding;

public class SelfAddTwoActivity extends AppCompatActivity {

    private ActivitySelfAddTwoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddTwoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}