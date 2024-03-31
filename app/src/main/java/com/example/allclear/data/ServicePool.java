package com.example.allclear.data;

import com.example.allclear.data.service.GetGradeService;
import com.example.allclear.data.service.EmailAuthRequestService;
import com.example.allclear.data.service.EmailIsValidRequestService;
import com.example.allclear.data.service.GradeAndCurriculumUpdateService;
import com.example.allclear.data.service.GraduationService;
import com.example.allclear.data.service.SignUpService;
import com.example.allclear.data.service.SubjectService;
import com.example.allclear.data.service.TestService;
import com.example.allclear.data.service.TimeTableService;
import com.example.allclear.data.service.TimeTableUpdateRequestService;
import com.example.allclear.data.service.TokenRefreshService;
import com.example.allclear.data.service.UpdateRequirementService;
import com.example.allclear.data.service.UpdateUserService;
import com.example.allclear.data.service.UserDataService;


public class ServicePool {
    public static final TestService testService = ApiClient.create(TestService.class);
    public static final SignUpService signUpService = ApiClient.create(SignUpService.class);
    public static final EmailAuthRequestService emailAuthRequestService = ApiClient.create(EmailAuthRequestService.class);
    public static final EmailIsValidRequestService emailIsValidRequestService = ApiClient.create(EmailIsValidRequestService.class);
    public static final GetGradeService getGradeService = ApiClient.create(GetGradeService.class);
    public static final UpdateUserService updateUserService = ApiClient.create(UpdateUserService.class);
    public static final UpdateRequirementService updateRequirementService = ApiClient.create(UpdateRequirementService.class);
    public static final GradeAndCurriculumUpdateService gradeAndCurriculumUpdateService = ApiClient.create(GradeAndCurriculumUpdateService.class);

    public static final TimeTableService timeTableService = ApiClient.create(TimeTableService.class);
    public static final TimeTableUpdateRequestService timeTableUpdateRequestService=ApiClient.create(TimeTableUpdateRequestService.class);

    public static final UserDataService userDataService = ApiClient.create(UserDataService.class);
    public static final TokenRefreshService tokenRefreshService = ApiClient.create(TokenRefreshService.class);

    public static final GraduationService graduationService = ApiClient.create(GraduationService.class);
    public static final SubjectService subjectService = ApiClient.create(SubjectService.class);
}
