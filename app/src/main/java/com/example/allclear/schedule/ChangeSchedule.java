package com.example.allclear.schedule;

import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;
//ArrayList<Schedule>을 ArrayList<ScheduleEntity>로 바꾸는 싱글턴 패턴 클래스
public class ChangeSchedule {
    private static ChangeSchedule instance = new ChangeSchedule();

    private ChangeSchedule() {}

    public static ChangeSchedule getInstance() {
        return instance;
    }
    public ArrayList<ScheduleEntity> Change_scheduleEntity(ArrayList<Schedule> scheduleDataList){
        ArrayList<ScheduleEntity> scheduleEntityList = new ArrayList<>();
        int size;
        String subtext;
        String professor;
        String place;
        int selectedDay;
        String starttime;
        String endtime;
        ScheduleEntity added_schedule;

        if(scheduleDataList!=null){
             size=scheduleDataList.size();
            for(int i=0;i<size;i++){
                subtext=scheduleDataList.get(i).getScheduleName();
                professor=scheduleDataList.get(i).getProfessor();
                place=scheduleDataList.get(i).getRoomInfo();
                selectedDay=scheduleDataList.get(i).getScheduleDay();
                starttime=scheduleDataList.get(i).getStartTime();
                endtime=scheduleDataList.get(i).getEndTime();
//색상을 랜덤으로 부여하는 기능 필요
                added_schedule = new ScheduleEntity(
                        32,                  // originId
                        subtext,          // scheduleName
                        place,    // roomInfo
                        selectedDay,  // ScheduleDay object (MONDAY ~ SUNDAY)
                        starttime,               // startTime format: "HH:mm"
                        endtime,              // endTime format: "HH:mm"
                        "#73fcae68",          // backgroundColor (optional)
                        "#000000"             // textColor (optional)
                );
                scheduleEntityList.add(added_schedule);
            }
        }
        return scheduleEntityList;
    }
}
