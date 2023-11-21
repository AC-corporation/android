package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelfAddOneBinding;

public class SelfAddOneActivity extends AppCompatActivity {

    private ActivitySelfAddOneBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddOneBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}