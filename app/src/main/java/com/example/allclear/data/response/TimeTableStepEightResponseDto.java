package com.example.allclear.data.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeTableStepEightResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private TimeTableData data;

    public static class TimeTableData {
        @SerializedName("currentPage")
        private int currentPage;
        @SerializedName("pageSize")
        private int pageSize; // 20
        @SerializedName("totalPage")
        private int totalPage;
        @SerializedName("totalElement")
        private int totalElement;
        @SerializedName("timetableList")
        private List<TimeTable> timetableList;

        public static class TimeTable {
            @SerializedName("timetableId")
            private long timetableId;
            @SerializedName("tableName")
            private String tableName;
            @SerializedName("year")
            private int year;
            @SerializedName("semester")
            private int semester;
            @SerializedName("timetableSubjectResponseDtoList")
            private List<TimetableSubject> timetableSubjectResponseDtoList;

            public static class TimetableSubject {
                @SerializedName("timetableSubjectId")
                private long timetableSubjectId;
                @SerializedName("subjectId")
                private long subjectId;
                @SerializedName("subjectName")
                private String subjectName;
                @SerializedName("classInfoResponseDtoList")
                private List<ClassInfo> classInfoResponseDtoList;

                public static class ClassInfo {
                    @SerializedName("professor")
                    private String professor;
                    @SerializedName("classDay")
                    private String classDay;
                    @SerializedName("startTime")
                    private String startTime;
                    @SerializedName("endTime")
                    private String endTime;
                    @SerializedName("classRoom")
                    private String classRoom;
                }
            }
        }
    }
}

