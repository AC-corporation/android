package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelfAddTwoBinding;

public class SelfAddTwoActivity extends AppCompatActivity {

    private ActivitySelfAddTwoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddTwoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
    }
}