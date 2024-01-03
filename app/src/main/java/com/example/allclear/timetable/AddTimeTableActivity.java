package com.example.allclear.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allclear.databinding.ActivityAddTimeTableBinding;
import com.example.allclear.databinding.ActivityMainPageBinding;

public class AddTimeTableActivity extends AppCompatActivity {
    private ActivityAddTimeTableBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}