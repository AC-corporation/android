package com.example.allclear.timetable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.databinding.ActivityEditTimeTableTwoBinding;
import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.schedule.Schedule;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class EditTimeTableTwoActivity extends AppCompatActivity {
private ActivityEditTimeTableTwoBinding binding;
    private String[] day = {"Mon", "Tue", "Wen", "Thu", "Fri"};
    String subtext;
    String professor;
    String place;
    int selectedDay;
    String starttime;
    String endtime;
    ScheduleEntity added_schedule;

    private ArrayList<Schedule> scheduleDataList=new ArrayList<Schedule>();

    private ArrayList<ScheduleEntity> scheduleEntityList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditTimeTableTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //스케줄데이터를 전달받아 타임테이블에 보여지는 요소로 전환
        Intent intent=getIntent();
        if(intent != null && intent.hasExtra("schedulelist")){
            scheduleDataList= (ArrayList<Schedule>) intent.getSerializableExtra("schedulelist");}
        scheduleEntityList=ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //SelfAddTwoEditActivity로 ScheduleList전달
        binding.tvSelfadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableTwoActivity.this,SelfAddTwoEditActivity.class);
                intent.putExtra("schedulelist",scheduleDataList);
                startActivityForResult(intent,10);
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(day);
        binding.table.updateSchedules(scheduleEntityList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //SelfAddTwoEditActivity에서 액티비티가 전환되었을 때
        if (requestCode == 10 && resultCode == RESULT_OK) {
            //사용자가 직접추가한 스케줄데이터를 ScheduleList에 추가
            Schedule schedule= (Schedule) data.getSerializableExtra("schedule");
            scheduleDataList.add(schedule);
            scheduleEntityList=ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
        }
        //갱신된 ScheduleList를 EditTimeTableActivity로 전달
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditTimeTableTwoActivity.this, EditTimeTableActivity.class);
                intent.putExtra("schedulelist",scheduleDataList);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}