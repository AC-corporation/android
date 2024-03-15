package com.example.allclear.timetable.maketimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.response.TimeTableStepEightResponseDto;
import com.example.allclear.databinding.ActivitySaveTimeTableBinding;
import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.timetable.TimeTableFragment;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveTimeTableActivity extends AppCompatActivity {
    private ActivitySaveTimeTableBinding binding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    private String[] Stringday;
    private ArrayList<Schedule> scheduleDataList = new ArrayList<Schedule>();

    private ArrayList<ScheduleEntity> scheduleEntityList = new ArrayList<>();

    String subtext;
    String professor;
    String start_time;
    String end_time;
    String place;
    int day;
    int size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySaveTimeTableBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getUserData();
        initBackBtnClickListener();
        getTimeTableGenerator();
        showTimeTable();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // 포커스 걸기
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(Stringday);
        binding.table.updateSchedules(scheduleEntityList);
    }


    private void getUserData() {
        preferenceUtil = MyApplication.getPreferences();
        userId = preferenceUtil.getUserId(-1L);
        accessToken = preferenceUtil.getAccessToken("FAIL");
    }

    private void initBackBtnClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getTimeTableGenerator() {
        ServicePool.timeTableService.getStepEight("Bearer " + accessToken, userId)
                .enqueue(new Callback<TimeTableStepEightResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableStepEightResponseDto> call, Response<TimeTableStepEightResponseDto> response) {
                        Toast.makeText(SaveTimeTableActivity.this, "일단 성공~~", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<TimeTableStepEightResponseDto> call, Throwable t) {
                        Toast.makeText(SaveTimeTableActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showTimeTable() {
        //스케줄데이터를 전달받아 타임테이블에 보여지는 요소로 전환
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("schedulelist")) {
            scheduleDataList = (ArrayList<Schedule>) intent.getSerializableExtra("schedulelist");
            scheduleEntityList = ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
            Stringday = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
            int size = scheduleDataList.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    if (5 == scheduleDataList.get(i).getClassDay()) {
                        Stringday = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};
                    }
                    if (6 == scheduleDataList.get(i).getClassDay()) {
                        Stringday = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun"};
                    }
                }
            }
        }

    }

    //시간문자열을 숫자로 변환해주는 함수
    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    void addSchedule() {
        Schedule schedule = new Schedule();
        schedule.setSubjectId(32);
        schedule.setSubjectName(subtext);
        schedule.setProfessor(professor);
        schedule.setClassDay(day);
        schedule.setStartTime(start_time);
        schedule.setEndTime(end_time);
        schedule.setClassRoom(place);
        Intent intent = new Intent(SaveTimeTableActivity.this, TimeTableFragment.class);
        intent.putExtra("schedule", schedule);
        setResult(RESULT_OK, intent);
        finish();
    }
}