package com.example.allclear.data.response;
import com.google.gson.annotations.SerializedName;

import java.time.LocalTime;
import java.util.List;

public class TimeTableListResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<TimeTableResponseDto> data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TimeTableResponseDto> getData() {
        return data;
    }

    public void setData(List<TimeTableResponseDto> data) {
        this.data = data;
    }

    public static class TimeTableResponseDto {
        @SerializedName("timetableId")
        private long timetableId;
        @SerializedName("tableName")
        private String tableName;
        @SerializedName("year")
        private int year;
        @SerializedName("semester")
        private int semester;
        @SerializedName("timetableSubjectResponseDtoList")
        private List<TimeTableSubjectResponseDto> timetableSubjectResponseDtoList;

        public long getTimetableId() {
            return timetableId;
        }

        public void setTimetableId(long timetableId) {
            this.timetableId = timetableId;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getSemester() {
            return semester;
        }

        public void setSemester(int semester) {
            this.semester = semester;
        }

        public List<TimeTableSubjectResponseDto> getTimetableSubjectResponseDtoList() {
            return timetableSubjectResponseDtoList;
        }

        public void setTimetableSubjectResponseDtoList(List<TimeTableSubjectResponseDto> timetableSubjectResponseDtoList) {
            this.timetableSubjectResponseDtoList = timetableSubjectResponseDtoList;
        }

        public static class TimeTableSubjectResponseDto {
            @SerializedName("timetableSubjectId")
            private long timetableSubjectId;
            @SerializedName("subjectId")
            private long subjectId;
            @SerializedName("subjectName")
            private String subjectName;
            @SerializedName("classInfoResponseDtoList")
            private List<ClassInfoResponseDto> classInfoResponseDtoList;


            public long getTimetableSubjectId() {
                return timetableSubjectId;
            }

            public void setTimetableSubjectId(long timetableSubjectId) {
                this.timetableSubjectId = timetableSubjectId;
            }

            public long getSubjectId() {
                return subjectId;
            }

            public void setSubjectId(long subjectId) {
                this.subjectId = subjectId;
            }

            public String getSubjectName() {
                return subjectName;
            }

            public void setSubjectName(String subjectName) {
                this.subjectName = subjectName;
            }

            public List<ClassInfoResponseDto> getClassInfoResponseDtoList() {
                return classInfoResponseDtoList;
            }

            public void setClassInfoResponseDtoList(List<ClassInfoResponseDto> classInfoResponseDtoList) {
                this.classInfoResponseDtoList = classInfoResponseDtoList;
            }

            public static class ClassInfoResponseDto {
                @SerializedName("professor")
                private String professor;
                @SerializedName("classDay")
                private String classDay;
                @SerializedName("startTime")
                private LocalTime startTime;
                @SerializedName("endTime")
                private LocalTime endTime;
                @SerializedName("classRoom")
                private String classRoom;

                public String getProfessor() {
                    return professor;
                }

                public void setProfessor(String professor) {
                    this.professor = professor;
                }

                public String getClassDay() {
                    return classDay;
                }

                public void setClassDay(String classDay) {
                    this.classDay = classDay;
                }

                public LocalTime getStartTime() {
                    return startTime;
                }

                public void setStartTime(LocalTime startTime) {
                    this.startTime = startTime;
                }

                public LocalTime getEndTime() {
                    return endTime;
                }

                public void setEndTime(LocalTime endTime) {
                    this.endTime = endTime;
                }

                public String getClassRoom() {
                    return classRoom;
                }

                public void setClassRoom(String classRoom) {
                    this.classRoom = classRoom;
                }
            }
        }
    }
}
