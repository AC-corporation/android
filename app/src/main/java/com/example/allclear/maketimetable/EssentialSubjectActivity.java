package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivityEssentialSubjectBinding;

public class EssentialSubjectActivity extends AppCompatActivity {
    private ActivityEssentialSubjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEssentialSubjectBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EssentialSubjectActivity.this, SaveTimeTableActivity.class);
                startActivity(intent);
            }
        });
    }
}