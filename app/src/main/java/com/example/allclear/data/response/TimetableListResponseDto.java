package com.example.allclear.data.response;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimetableListResponseDto {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("timetableResponseDtoList")
        private List<TimetableResponseDto> timetableResponseDtoList;

        public List<TimetableResponseDto> getTimetableResponseDtoList() {
            return timetableResponseDtoList;
        }

        public static class TimetableResponseDto {
            @SerializedName("timetableId")
            private long timetableId;
            @SerializedName("tableName")
            private String tableName;
            @SerializedName("year")
            private int year;
            @SerializedName("semester")
            private int semester;
            @SerializedName("timetableSubjectResponseDtoList")
            private List<timetableSubjectResponseDto> timetableSubjectResponseDtoList;

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

            public List<timetableSubjectResponseDto> getTimetableSubjectResponseDtoList() {
                return timetableSubjectResponseDtoList;
            }

            public void setTimetableSubjectResponseDtoList(List<timetableSubjectResponseDto> timetableSubjectResponseDtoList) {
                this.timetableSubjectResponseDtoList = timetableSubjectResponseDtoList;
            }

            public static class timetableSubjectResponseDto {
                @SerializedName("timetableSubjectId")
                private long timetableSubjectId;
                @SerializedName("subjectId")
                private long subjectId;
                @SerializedName("subjectName")
                private String subjectName;
                @SerializedName("classInfoResponseDtoList")
                private List<classInfoResponseDto> classInfoResponseDtoList;


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

                public List<classInfoResponseDto> getClassInfoResponseDtoList() {
                    return classInfoResponseDtoList;
                }

                public void setClassInfoResponseDtoList(List<classInfoResponseDto> classInfoResponseDtoList) {
                    this.classInfoResponseDtoList = classInfoResponseDtoList;
                }

                public static class classInfoResponseDto {
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

                    public String getStartTime() {
                        return startTime;
                    }

                    public void setStartTime(String startTime) {
                        this.startTime = startTime;
                    }

                    public String getEndTime() {
                        return endTime;
                    }

                    public void setEndTime(String endTime) {
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
}