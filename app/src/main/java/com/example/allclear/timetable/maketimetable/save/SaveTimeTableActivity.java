package com.example.allclear.timetable.maketimetable.save;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.allclear.MainPageActivity;
import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableSaveRequestDto;
import com.example.allclear.data.response.TimeTableSaveResponseDto;
import com.example.allclear.data.response.TimeTableStepEightResponseDto;
import com.example.allclear.databinding.ActivitySaveTimeTableBinding;
import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.schedule.Schedule;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveTimeTableActivity extends AppCompatActivity {
    private ActivitySaveTimeTableBinding binding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    private String[] stringDay;
    private ArrayList<Schedule> scheduleDataList = new ArrayList<Schedule>();
    private ArrayList<ScheduleEntity> scheduleEntityList = new ArrayList<>();

    String subtext;
    String professor;
    String startTime;
    String endTime;
    String place;
    long timetableId;

    int day;
    int size;

    String selectedYear;
    String selectedSemester;
    String timeTableName;
    int indicatorCount;
    private ViewPager2 mPager;
    private MainAdapter pagerAdapter;
    private List<Long> num_page = new ArrayList<>();
    private CircleIndicator3 mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySaveTimeTableBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initViewPager();
        getUserData();
        getSemesterData();
        setTimeTableName();
        initBackBtnClickListener();
        getTimeTableGenerator();
        initSaveBtnClickListener();
    }

    private void initViewPager() {
        //ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new MainAdapter(this, num_page);
        //   mPager.setAdapter(pagerAdapter);

        for (int i = 0; i < pagerAdapter.getItemCount(); i++) {
            pagerAdapter.setFragmentData(i, stringDay, scheduleEntityList);
        }

        // ViewPager2에 어댑터 설정
        mPager.setAdapter(pagerAdapter);

        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page.size(), 0); // num_page의 크기로 인디케이터 생성
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(0); // 초기 위치 설정
        //mPager.setOffscreenPageLimit(num_page.size()); // 프래그먼트 미리 로드
        mPager.setOffscreenPageLimit(10);


        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position);
            }
        });
    }

    private void getUserData() {
        preferenceUtil = MyApplication.getPreferences();
        userId = preferenceUtil.getUserId(-1L);
        accessToken = preferenceUtil.getAccessToken("FAIL");
    }

    private void getSemesterData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectedYear = intent.getStringExtra("selectedYear");
            selectedSemester = intent.getStringExtra("selectedSemester");
            timeTableName = intent.getStringExtra("timeTableName");
        }
    }

    private void setTimeTableName() {
        binding.tvSubTitle.setText(timeTableName);
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
        ServicePool.timeTableService.getStepEight("Bearer " + accessToken, userId).enqueue(new Callback<TimeTableStepEightResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<TimeTableStepEightResponseDto> call, @NonNull Response<TimeTableStepEightResponseDto> response) {
                if (response.isSuccessful()) {
                    TimeTableStepEightResponseDto responseBody = response.body();
                    assert responseBody != null;
                    List<TimeTableStepEightResponseDto.TimeTableData.TimeTable> timeTable = responseBody.getTimeTableData().getTimetableResponseDto();
                    setTimeTable(timeTable);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TimeTableStepEightResponseDto> call, @NonNull Throwable t) {
                Toast.makeText(SaveTimeTableActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTimeTable(List<TimeTableStepEightResponseDto.TimeTableData.TimeTable> timeTable) {
        if (timeTable == null) {
            Toast.makeText(this, "생성된 추천 시간표가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
        } else {
            for (TimeTableStepEightResponseDto.TimeTableData.TimeTable entry : timeTable) {
                List<TimeTableStepEightResponseDto.TimeTableData.TimeTable.timetableSubjectResponseDtoList> subjects = entry.getTimetableSubjectResponseDtoList();

                timetableId = entry.getTimetableId();

                num_page.add(timetableId);

                for (TimeTableStepEightResponseDto.TimeTableData.TimeTable.timetableSubjectResponseDtoList subject : subjects) {
                    List<TimeTableStepEightResponseDto.TimeTableData.TimeTable.timetableSubjectResponseDtoList.ClassInfo> classInfoList = subject.getClassInfoResponseDtoList();

                    for (TimeTableStepEightResponseDto.TimeTableData.TimeTable.timetableSubjectResponseDtoList.ClassInfo classInfo : classInfoList) {
                        subtext = subject.getSubjectName();
                        professor = classInfo.getProfessor();
                        day = makeDayToInt(classInfo.getClassDay());
                        startTime = classInfo.getStartTime();
                        endTime = classInfo.getEndTime();
                        place = classInfo.getClassRoom();

                        checkConflict();
                    }
                }
            }
            showTimeTable();
        }
    }

    private void showTimeTable() {
        // 여기서 stringDay와 scheduleEntityList를 한 번만 설정
        pagerAdapter.setStringDay(stringDay);
        pagerAdapter.setScheduleEntityList(scheduleEntityList);
        pagerAdapter.notifyDataSetChanged(); // 변경 내용을 어댑터에 알림
    }


    private int makeDayToInt(String day) {
        if (Objects.equals(day, getString(R.string.monday)))
            return 0;
        else if (Objects.equals(day, getString(R.string.tuesday)))
            return 1;
        else if (Objects.equals(day, getString(R.string.wednesday)))
            return 2;
        else if (Objects.equals(day, getString(R.string.thursday)))
            return 3;
        else if (Objects.equals(day, getString(R.string.friday)))
            return 4;
        else
            return 5;
    }

    private void checkConflict() {
        if (size == 0) {
            addSchedule();
        } else {
            for (int i = 0; i < size; i++) {
                if (day == scheduleDataList.get(i).getClassDay()) {
                    int addStart = timeToMinutes(startTime);
                    int addEnd = timeToMinutes(endTime);
                    int listStart = timeToMinutes(scheduleDataList.get(i).getStartTime());
                    int listEnd = timeToMinutes(scheduleDataList.get(i).getEndTime());
                    if ((addStart < listEnd) && (addEnd > listStart)) {
                        Toast.makeText(SaveTimeTableActivity.this, "시간이 겹치는 일정이 존재합니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
            addSchedule();
        }
    }

    private static int timeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    void addSchedule() {
        Schedule schedule = new Schedule();
        schedule.setSubjectId(32L);
        schedule.setSubjectName(subtext);
        schedule.setProfessor(professor);
        schedule.setClassDay(day);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setClassRoom(place);

        showTimeTable(schedule);
    }

    private void showTimeTable(Schedule newSchedule) {
        scheduleDataList.add(newSchedule);
        scheduleEntityList = ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);

        // 토요일, 일요일 유무에 따라 day 변경
        stringDay = new String[]{getString(R.string.Mon), getString(R.string.Tue), getString(R.string.Wen), getString(R.string.Thu), getString(R.string.Fri)};
        int size = scheduleDataList.size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (5 == scheduleDataList.get(i).getClassDay()) {
                    stringDay = new String[]{getString(R.string.Mon), getString(R.string.Tue), getString(R.string.Wen), getString(R.string.Thu), getString(R.string.Fri), getString(R.string.Sat)};
                }
                if (6 == scheduleDataList.get(i).getClassDay()) {
                    stringDay = new String[]{getString(R.string.Mon), getString(R.string.Tue), getString(R.string.Wen), getString(R.string.Thu), getString(R.string.Fri), getString(R.string.Sat), getString(R.string.Sun)};
                }
            }
        }
    }

    private void initSaveBtnClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSaveTimeTable(accessToken, userId);
            }
        });
    }

    private void postSaveTimeTable(String accessToken, Long userId) {
        TimeTableSaveRequestDto timeTableSaveRequestDto = new TimeTableSaveRequestDto();

        timeTableSaveRequestDto.setTimetableGeneratorTimetableId(timetableId);

        ServicePool.timeTableService.postSaveTimTable("Bearer " + accessToken, userId, timeTableSaveRequestDto)
                .enqueue(new Callback<TimeTableSaveResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableSaveResponseDto> call, @NonNull Response<TimeTableSaveResponseDto> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SaveTimeTableActivity.this, R.string.timetable_save, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SaveTimeTableActivity.this, MainPageActivity.class);
                            intent.putExtra("scheduleList", scheduleDataList);
                            setResult(RESULT_OK, intent);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TimeTableSaveResponseDto> call, Throwable t) {
                        Toast.makeText(SaveTimeTableActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}