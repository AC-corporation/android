package com.example.allclear.timetable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.MainPageActivity;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.databinding.ActivityEditTimeTableBinding;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class EditTimeTableActivity extends AppCompatActivity {
    private ActivityEditTimeTableBinding binding;

    private String[] day = {"Mon", "Tue", "Wen", "Thu", "Fri"};
    //대문자는 Schedule(스케줄데이터),소문자는 ScheduleEntity(타임테이블에 보여지는 요소)
    private ArrayList<Schedule> ScheduleList=new ArrayList<Schedule>();
    public ArrayList<ScheduleEntity> scheduleList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //스케줄데이터를 전달받아 타임테이블에 보여지는 요소로 전환
        Intent intent=getIntent();
        if(intent != null && intent.hasExtra("schedulelist")){
        ScheduleList=(ArrayList<Schedule>)intent.getSerializableExtra("schedulelist");
        scheduleList= ChangeSchedule.getInstance().Change_scheduleEntity(ScheduleList);}
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //EditTimeTableTwoActivity로 ScheduleList전달
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableActivity.this, EditTimeTableTwoActivity.class);
                intent.putExtra("schedulelist",ScheduleList);
                startActivityForResult(intent,1);
            }
        });
        //TimeTableFragment로 ScheduleList전달
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
        //EditTimeTableTwoActivity에서 액티비티가 전환되었을 때 ScheduleList 갱신
        if (requestCode == 1 && resultCode == RESULT_OK){
            ScheduleList=(ArrayList<Schedule>)data.getSerializableExtra("schedulelist");
            scheduleList= ChangeSchedule.getInstance().Change_scheduleEntity(ScheduleList);
        }
        //EditTimeTableTwoActivity에 갱신된 ScheduleList 전달
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