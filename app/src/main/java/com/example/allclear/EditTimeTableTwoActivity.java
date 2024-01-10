package com.example.allclear;

import androidx.annotation.Nullable;
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
    String subtext;
    String professor;
    String place;
    String selectedDay;
    String starttime;
    String endtime;
    ScheduleEntity added_schedule;
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
                startActivityForResult(intent,10);
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(day);
        binding.table.updateSchedules(scheduleList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            subtext=data.getStringExtra("subtext");
            professor=data.getStringExtra("professor");
            place=data.getStringExtra("place");
            selectedDay=data.getStringExtra("dayspinner");
            starttime=data.getStringExtra("starttime");
            endtime=data.getStringExtra("endtime");
            added_schedule = new ScheduleEntity(
                    32,                  // originId
                    subtext,          // scheduleName
                    place,    // roomInfo
                    ScheduleDay.WEDNESDAY,  // ScheduleDay object (MONDAY ~ SUNDAY)
                    starttime,               // startTime format: "HH:mm"
                    endtime,              // endTime format: "HH:mm"
                    "#73fcae68",          // backgroundColor (optional)
                    "#000000"             // textColor (optional)
            );
            scheduleList.add(added_schedule);
        }
    }

}