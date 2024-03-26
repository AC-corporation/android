package com.example.allclear.timetable.maketimetable.selfadd.subject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.databinding.ActivitySelfAddSubjectOneBinding;
import com.example.allclear.timetable.maketimetable.essential.EssentialSubjectActivity;

public class SelfAddSubjectOneActivity extends AppCompatActivity {

    private ActivitySelfAddSubjectOneBinding binding;

    String selectedYear;
    String selectedSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddSubjectOneBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getSemesterData();
        initNextBtnClickListener();
        initBackBtnClickListener();
    }


    private void getSemesterData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectedYear = intent.getStringExtra("selectedYear");
            selectedSemester = intent.getStringExtra("selectedSemester");
        }
    }


    private void initNextBtnClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfAddSubjectOneActivity.this, EssentialSubjectActivity.class);
                intent.putExtra("selectedYear", selectedYear);
                intent.putExtra("selectedSemester", selectedSemester);
                startActivity(intent);
            }
        });
    }

    private void initBackBtnClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
