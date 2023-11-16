package com.example.allclear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allclear.databinding.ActivityListTimeTableBinding;
import com.example.allclear.databinding.ActivityMainPageBinding;

public class ListTimeTableActivity extends AppCompatActivity {
    private ActivityListTimeTableBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}