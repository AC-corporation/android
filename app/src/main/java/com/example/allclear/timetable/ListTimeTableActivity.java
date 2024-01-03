package com.example.allclear.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.allclear.databinding.ActivityListTimeTableBinding;
import com.example.allclear.databinding.ActivityMainPageBinding;

public class ListTimeTableActivity extends AppCompatActivity {
    private ActivityListTimeTableBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.listTimetableBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}