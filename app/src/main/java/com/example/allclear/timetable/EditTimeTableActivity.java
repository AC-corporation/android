package com.example.allclear.timetable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.MainPageActivity;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.databinding.ActivityEditTimeTableBinding;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class EditTimeTableActivity extends AppCompatActivity {
    private ActivityEditTimeTableBinding binding;

    private String[] day;

    private ArrayList<Schedule> scheduleDataList=new ArrayList<Schedule>();
    public ArrayList<ScheduleEntity> scheduleEntityList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //스케줄데이터를 전달받아 타임테이블에 보여지는 요소로 전환
        Intent intent=getIntent();
        if(intent != null && intent.hasExtra("schedulelist")){
            scheduleDataList=(ArrayList<Schedule>)intent.getSerializableExtra("schedulelist");
            scheduleEntityList= ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
            day=new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
            int size=scheduleDataList.size();
            if(size!=0){
                for(int i=0;i<size;i++){
                    if(5==scheduleDataList.get(i).getScheduleDay()){
                        day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};}
                    if(6==scheduleDataList.get(i).getScheduleDay()){
                        day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat","Sun"};}
                }
            }
        }
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
                intent.putExtra("schedulelist",scheduleDataList);
                startActivityForResult(intent,1);
            }
        });
        //TimeTableFragment로 ScheduleList전달
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditTimeTableActivity.this, MainPageActivity.class);
                intent.putExtra("schedulelist",scheduleDataList);
                startActivity(intent);

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
        //EditTimeTableTwoActivity에서 액티비티가 전환되었을 때 ScheduleList 갱신
        if (requestCode == 1 && resultCode == RESULT_OK){
            scheduleDataList=(ArrayList<Schedule>)data.getSerializableExtra("schedulelist");
            scheduleEntityList= ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
            day=new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
            int size=scheduleDataList.size();
            if(size!=0){
                for(int i=0;i<size;i++){
                    if(5==scheduleDataList.get(i).getScheduleDay()){
                        day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};}
                    if(6==scheduleDataList.get(i).getScheduleDay()){
                        day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat","Sun"};}
                }
            }
        }
        //EditTimeTableTwoActivity에 갱신된 ScheduleList 전달
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableActivity.this, EditTimeTableTwoActivity.class);
                intent.putExtra("schedulelist",scheduleDataList);
                startActivityForResult(intent,1);
            }
        });

    }
}