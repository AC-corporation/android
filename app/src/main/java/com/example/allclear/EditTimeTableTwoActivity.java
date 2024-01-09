package com.example.allclear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.databinding.ActivityEditTimeTableTwoBinding;
import com.example.allclear.timetable.EditTimeTableActivity;
import com.islandparadise14.mintable.model.ScheduleDay;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class EditTimeTableTwoActivity extends AppCompatActivity {
private ActivityEditTimeTableTwoBinding binding;
    private String[] day = {"Mon", "Tue", "Wen", "Thu", "Fri"};
    public ArrayList<ScheduleEntity> scheduleList = new ArrayList<>();
    ScheduleEntity schedule = new ScheduleEntity(
            32,                  // originId
            "Database",          // scheduleName
            "IT Building 301",    // roomInfo
            ScheduleDay.TUESDAY,  // ScheduleDay object (MONDAY ~ SUNDAY)
            "8:20",               // startTime format: "HH:mm"
            "17:30",              // endTime format: "HH:mm"
            "#73fcae68",          // backgroundColor (optional)
            "#000000"             // textColor (optional)
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditTimeTableTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        scheduleList.add(schedule);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.tvSelfadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableTwoActivity.this,SelfAddTwoEditActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(day);
        binding.table.updateSchedules(scheduleList);
    }

}