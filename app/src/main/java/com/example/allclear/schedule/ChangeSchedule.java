package com.example.allclear.schedule;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.allclear.R;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//ArrayList<Schedule>을 ArrayList<ScheduleEntity>로 바꾸는 싱글턴 패턴 클래스
public class ChangeSchedule {
    private static ChangeSchedule instance = new ChangeSchedule();

    private ChangeSchedule() {
    }

    public static ChangeSchedule getInstance() {
        return instance;
    }

    public ArrayList<ScheduleEntity> Change_scheduleEntity(Context context, ArrayList<Schedule> scheduleDataList) {
        ArrayList<ScheduleEntity> scheduleEntityList = new ArrayList<>();
        int size;
        Long subjectid;
        String subtext;
        String professor;
        String place;
        int selectedDay;
        String starttime;
        String endtime;
        String color;
        ScheduleEntity added_schedule;
        int[] colorResIds = new int[]{
                R.color.light_blue, R.color.dark_blue, R.color.first_blue,
                R.color.second_blue, R.color.third_blue, R.color.last_blue, R.color.disabled_blue
        };

            HashMap<String, Integer> subjectColorMap = new HashMap<>();  // 과목 이름과 색상 리소스 ID를 매핑
            int colorIndex = 0;  // 사용할 색상의 인덱스

            for (Schedule schedule : scheduleDataList) {
                if (!subjectColorMap.containsKey(schedule.getSubjectName())) {
                    // 새로운 과목 이름이면 색상을 할당
                    subjectColorMap.put(schedule.getSubjectName(), colorResIds[colorIndex % colorResIds.length]);
                    colorIndex++;  // 다음 과목에는 다음 색상을 사용
                }
                // Schedule 객체에 색상 리소스 ID를 설정
                schedule.setBackgroundColor(String.valueOf(subjectColorMap.get(schedule.getSubjectName())));
            }

            if (scheduleDataList != null) {
                size = scheduleDataList.size();
                for (int i = 0; i < size; i++) {
                    subjectid = scheduleDataList.get(i).getSubjectId();
                    subtext = scheduleDataList.get(i).getSubjectName();
                    professor = scheduleDataList.get(i).getProfessor();
                    place = scheduleDataList.get(i).getClassRoom();
                    selectedDay = scheduleDataList.get(i).getClassDay();
                    starttime = scheduleDataList.get(i).getStartTime();
                    endtime = scheduleDataList.get(i).getEndTime();
                    color = scheduleDataList.get(i).getBackgroundColor();
                    //색상 리소스 ID를 실제 색상 코드로 변환
                    int colorResourceId = Integer.parseInt(scheduleDataList.get(i).getBackgroundColor());
                    // ContextCompat를 사용하여 색상 리소스 ID로부터 실제 색상 값을 가져옴.
                    String actualColorCode = String.format("#%06X", (0xFFFFFF & ContextCompat.getColor(context, colorResourceId)));

                    added_schedule = new ScheduleEntity(
                            0, // originId
                            subtext,          // scheduleName
                            place,    // roomInfo
                            selectedDay,  // ScheduleDay object (MONDAY ~ SUNDAY)
                            starttime,               // startTime format: "HH:mm"
                            endtime,              // endTime format: "HH:mm"
                            actualColorCode,          // backgroundColor (optional)
                            "#000000"             // textColor (optional)
                    );
                    scheduleEntityList.add(added_schedule);
                }
            }
            return scheduleEntityList;
        }
    }

