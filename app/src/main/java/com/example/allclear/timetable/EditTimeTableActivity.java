package com.example.allclear.timetable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.ChangeSchedule;
import com.example.allclear.EditTimeTableTwoActivity;
import com.example.allclear.MainPageActivity;
import com.example.allclear.Schedule;
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
    private ArrayList<Schedule> ScheduleList=new ArrayList<Schedule>();
    public ArrayList<ScheduleEntity> scheduleList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
                intent.putExtra("schedulelist",ScheduleList);
                startActivityForResult(intent,1);
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditTimeTableActivity.this, MainPageActivity.class);
                intent.putExtra("schedulelist",ScheduleList);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            ScheduleList=(ArrayList<Schedule>)data.getSerializableExtra("schedulelist");
            scheduleList= ChangeSchedule.getInstance().Change_scheduleEntity(ScheduleList);
        }
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableActivity.this, EditTimeTableTwoActivity.class);
                intent.putExtra("schedulelist",ScheduleList);
                startActivityForResult(intent,1);
            }
        });

    }
}