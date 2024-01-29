package com.example.allclear.data;

import com.example.allclear.data.service.GradeService;
import com.example.allclear.data.service.GraduationService;

public class ServicePool {
    public static final GraduationService  graduationService = ApiClient.create(GraduationService.class);

    public static final GradeService totalGradeService = ApiClient.create(GradeService.class);

    public static final GradeService semesterGradeService = ApiClient.create(GradeService.class);

}
