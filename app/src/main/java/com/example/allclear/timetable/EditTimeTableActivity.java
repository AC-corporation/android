package com.example.allclear.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.EditTimeTableTwoActivity;
import com.example.allclear.databinding.ActivityEditTimeTableBinding;
import com.example.allclear.databinding.ActivityMainPageBinding;
import com.example.allclear.timetable.maketimetable.SelectGaneralElectiveActivity;
import com.example.allclear.timetable.maketimetable.SelectMajorActivity;
import com.islandparadise14.mintable.model.ScheduleDay;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class EditTimeTableActivity extends AppCompatActivity {
    private ActivityEditTimeTableBinding binding;

    private String[] day = {"Mon", "Tue", "Wen", "Thu", "Fri"};
    private ArrayList<ScheduleEntity> scheduleList = new ArrayList<>();
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
        binding = ActivityEditTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        scheduleList.add(schedule);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableActivity.this, EditTimeTableTwoActivity.class);
                intent.putExtra("schedulelist",scheduleList);
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