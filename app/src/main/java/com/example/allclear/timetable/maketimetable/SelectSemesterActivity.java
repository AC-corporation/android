package com.example.allclear.timetable.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.example.allclear.databinding.ActivitySelectSemesterBinding;
import com.example.allclear.timetable.TimeTableFragment;

public class SelectSemesterActivity extends AppCompatActivity {

    private ActivitySelectSemesterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectSemesterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBackClickListener();
        initNextBtnClickListener();

    }

    private void initBackClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initNextBtnClickListener(){
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.cbEmptyTimeTable.isChecked()){
                    Intent intent = new Intent(SelectSemesterActivity.this, TimeTableFragment.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SelectSemesterActivity.this, SelfAddOneActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}