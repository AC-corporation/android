package com.example.allclear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.allclear.databinding.ActivityEditTimeTableBinding;
import com.example.allclear.databinding.ActivityMainPageBinding;
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
            "10:30",              // endTime format: "HH:mm"
            "#73fcae68",          // backgroundColor (optional)
            "#000000"             // textColor (optional)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        scheduleList.add(schedule);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(day);
        binding.table.updateSchedules(scheduleList);
    }

}