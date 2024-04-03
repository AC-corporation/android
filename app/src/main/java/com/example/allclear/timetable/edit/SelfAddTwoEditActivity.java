package com.example.allclear.timetable.edit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.databinding.ActivitySelfAddTwoEditBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.schedule.AdapterSpinner;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.timetable.DataModel_timeplace;
import com.example.allclear.timetable.addplacetimeadapter;

import java.util.ArrayList;
import java.util.List;

public class SelfAddTwoEditActivity extends AppCompatActivity {
    private ActivitySelfAddTwoEditBinding binding;
    String subtext;
    String professor;
    int size;
    int count;
    ArrayList<Schedule> scheduleDataList;
    private List<DataModel_timeplace> timeplaceList = new ArrayList<>();
    addplacetimeadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddTwoEditBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        scheduleDataList = (ArrayList<Schedule>) intent.getSerializableExtra("schedulelist");
        if (scheduleDataList != null) {
            size = scheduleDataList.size();
        } else if (scheduleDataList == null) {
            size = 0;
        }
        timeplaceList.add(new DataModel_timeplace("09:00","10:00","",""));
        displayDataInRecyclerView(timeplaceList);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.tvAddPlaceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem();
            }
        });

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtext=binding.etSubTextOne.getText().toString();
                professor=binding.etProfessorName.getText().toString();
                count=timeplaceList.size();
                if (subtext.isEmpty())
                    Toast.makeText(SelfAddTwoEditActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                else {
                    checkempty();
                }
            }
        });
    }

    public void checkconflict(){
       if(hasOverlappingSchedules(timeplaceList)){
           Toast.makeText(SelfAddTwoEditActivity.this, "압력한 시간 중 겹치는 시간이 존재합니다", Toast.LENGTH_SHORT).show();
       }
       else{
           if(size==0){
               //스케줄데이터를 EditTimeTableTwoActivity로 전달하는 함수
               addschedule();
           }
           else {
               for(int j=0;j<count;j++){
                   for(int i=0;i<size;i++){
                       if(getday(timeplaceList.get(j).getDay())==scheduleDataList.get(i).getClassDay()){
                           int addstart=timeToMinutes(timeplaceList.get(j).getStarttime());
                           int addend=timeToMinutes(timeplaceList.get(j).getEndtime());
                           int liststart=timeToMinutes(scheduleDataList.get(i).getStartTime());
                           int listend=timeToMinutes(scheduleDataList.get(i).getEndTime());
                           if((addstart<listend)&&(addend>liststart)){
                               Toast.makeText(SelfAddTwoEditActivity.this, "시간이 겹치는 일정이 존재합니다", Toast.LENGTH_SHORT).show();
                               return;
                           }
                       }
                   }
               }
               addschedule();
           }

       }
    }
    public static boolean hasOverlappingSchedules(List<DataModel_timeplace> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                DataModel_timeplace first = list.get(i);
                DataModel_timeplace second = list.get(j);
                // 같은 요일인지 확인
                if (first.getDay().equals(second.getDay())) {
                    //시간이 겹치는지 확인
                    int firststart=timeToMinutes(first.getStarttime());
                    int firstend=timeToMinutes(first.getEndtime());
                    int secondstart=timeToMinutes(second.getStarttime());
                    int secondend=timeToMinutes(second.getEndtime());
                    if((firststart<secondend)&&(firstend>secondstart)){
                        return true;
                    }
                }
            }
        }
        // 겹치는 일정 없음
        return false;
    }
    public void checkempty(){
        for(int i=0;i<count;i++){
            if(timeplaceList.get(i).getStarttime().isEmpty()) {
                Toast.makeText(SelfAddTwoEditActivity.this, "시작시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(timeplaceList.get(i).getEndtime().isEmpty()) {
                Toast.makeText(SelfAddTwoEditActivity.this, "종료시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(timeplaceList.get(i).getStarttime().equals(timeplaceList.get(i).getEndtime())) {
                Toast.makeText(SelfAddTwoEditActivity.this, "시작시간과 종료시간을 다르게 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        checkconflict();
    }

    //선택요일을 정수로 변환해주는 함수
    protected int getday(String day) {
        if (day.equals("월요일"))
            return 0;
        else if (day.equals("화요일"))
            return 1;
        else if (day.equals("수요일"))
            return 2;
        else if (day.equals("목요일"))
            return 3;
        else if (day.equals("금요일"))
            return 4;
        else if (day.equals("토요일"))
            return 5;
        else return 6;
    }

    //시간문자열을 숫자로 변환해주는 함수
    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    void addschedule() {
        for(int i=0;i<count;i++){
            Schedule schedule = new Schedule();
            schedule.setSubjectId(null);
            schedule.setSubjectName(subtext);
            schedule.setProfessor(professor);
            schedule.setClassDay(getday(timeplaceList.get(i).getDay()));
            schedule.setStartTime(timeplaceList.get(i).getStarttime());
            schedule.setEndTime(timeplaceList.get(i).getEndtime());
            schedule.setClassRoom(timeplaceList.get(i).getPlace());
            scheduleDataList.add(schedule);
        }
        Intent intent = new Intent(SelfAddTwoEditActivity.this, com.example.allclear.timetable.edit.EditTimeTableTwoActivity.class);
        intent.putExtra("addedschedulelist", scheduleDataList);
        setResult(RESULT_OK, intent);
        finish();
    }
    private void displayDataInRecyclerView(List<DataModel_timeplace> timeplaceList) {
        RecyclerView recyclerView = binding.rvAddpalcetime;
        adapter = new addplacetimeadapter(this,timeplaceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    private void addNewItem() {
        timeplaceList.add(new DataModel_timeplace("09:00", "10:00","",""));
        adapter.notifyDataSetChanged();
    }
}