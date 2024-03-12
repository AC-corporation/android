package com.example.allclear.timetable.maketimetable.selfadd.subject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.databinding.ActivitySelfAddSubjectOneBinding;
import com.example.allclear.timetable.maketimetable.essential.EssentialSubjectActivity;

public class SelfAddSubjectOneActivity extends AppCompatActivity {

    private ActivitySelfAddSubjectOneBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddSubjectOneBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initNextBtnClickListener();
    }

    private void initNextBtnClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfAddSubjectOneActivity.this, EssentialSubjectActivity.class);
                startActivity(intent);
            }
        });
    }
}
