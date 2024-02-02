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
    public ArrayList<ScheduleEntity> Change_scheduleEntity(ArrayList<Schedule> ScheduleList){
        ArrayList<ScheduleEntity> scheduleList = new ArrayList<>();
        int size;
        String subtext;
        String professor;
        String place;
        int selectedDay;
        String starttime;
        String endtime;
        ScheduleEntity added_schedule;

        if(ScheduleList!=null){
             size=ScheduleList.size();
            for(int i=0;i<size;i++){
                subtext=ScheduleList.get(i).getScheduleName();
                professor=ScheduleList.get(i).getProfessor();
                place=ScheduleList.get(i).getRoomInfo();
                selectedDay=ScheduleList.get(i).getScheduleDay();
                starttime=ScheduleList.get(i).getStartTime();
                endtime=ScheduleList.get(i).getEndTime();
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
                scheduleList.add(added_schedule);
            }
        }
        return scheduleList;
    }
}
