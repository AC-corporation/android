package com.example.allclear.data.gradeData;

import java.util.List;

public class GradeResponseDto {
    boolean isSuccess;
    String code;
    String message;
    public Data data;

    public static class Data {
        double totalCredit;
        String averageGrade;
        public List<SemesterGradeDto> semesterGradeDtoList;

        public static class SemesterGradeDto {
            long semesterGradeId;
            public String semesterAverageGrade;
            List<SemesterSubjectDto> semesterSubjectDtoList;

            static class SemesterSubjectDto {
                String semesterSubjectName;
                String semesterSubjectScore;
            }
        }

        public double getTotalCredit() {
            return totalCredit;
        }

        public String getAverageGrade() {
            return averageGrade;
        }
    }
}

