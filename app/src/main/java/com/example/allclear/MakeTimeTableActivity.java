package com.example.allclear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allclear.databinding.ActivityMakeTimeTableBinding;

public class MakeTimeTableActivity extends AppCompatActivity {
    private ActivityMakeTimeTableBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakeTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}