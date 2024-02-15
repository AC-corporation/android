package com.example.allclear.data;

public class ServicePool {
    public static final TestService testService = ApiClient.create(TestService.class);
    public static final SignUpService signUpService = ApiClient.create(SignUpService.class);
    public static final EmailAuthRequestService emailAuthRequestService = ApiClient.create(EmailAuthRequestService.class);
    public static final EmailIsValidRequestService emailIsValidRequestService = ApiClient.create(EmailIsValidRequestService.class);
    public static final UpdateUserService updateUserService = ApiClient.create(UpdateUserService.class);
    public static final UpdateRequirementService updateRequirementService = ApiClient.create(UpdateRequirementService.class);
    public static final GradeAndCurriculumUpdateService gradeAndCurriculumUpdateService = ApiClient.create(GradeAndCurriculumUpdateService.class);
}
