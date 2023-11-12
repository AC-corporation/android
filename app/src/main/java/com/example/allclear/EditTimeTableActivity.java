package com.example.allclear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allclear.databinding.ActivityEditTimeTableBinding;
import com.example.allclear.databinding.ActivityMainPageBinding;

public class EditTimeTableActivity extends AppCompatActivity {
    private ActivityEditTimeTableBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}