package com.example.allclear.data;

import com.example.allclear.data.request.EmailAuthRequestService;
import com.example.allclear.data.request.EmailIsValidRequestService;
import com.example.allclear.data.service.GradeService;
import com.example.allclear.data.service.GraduationService;
import com.example.allclear.data.service.SignUpService;

public class ServicePool {
    public static final GraduationService  graduationService = ApiClient.create(GraduationService.class);

    public static final GradeService totalGradeService = ApiClient.create(GradeService.class);

    public static final GradeService semesterGradeService = ApiClient.create(GradeService.class);

    public static final SignUpService signUpService = ApiClient.create(SignUpService.class);

    public static final EmailAuthRequestService emailAuthRequestService = ApiClient.create(EmailAuthRequestService.class);

    public static final EmailIsValidRequestService emailIsValidRequestService = ApiClient.create(EmailIsValidRequestService.class);

}
