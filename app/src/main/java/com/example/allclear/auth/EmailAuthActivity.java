package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivityEmailAuthBinding;

public class EmailAuthActivity extends AppCompatActivity {
    ActivityEmailAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}