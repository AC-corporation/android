package com.example.allclear.data;

import com.example.allclear.data.service.EmailAuthRequestService;
import com.example.allclear.data.service.EmailIsValidRequestService;
import com.example.allclear.data.service.SignUpService;
import com.example.allclear.data.service.TestService;

public class ServicePool {
    public static final TestService testService = ApiClient.create(TestService.class);
    public static final SignUpService signUpService = ApiClient.create(SignUpService.class);
    public static final EmailAuthRequestService emailAuthRequestService = ApiClient.create(EmailAuthRequestService.class);
    public static final EmailIsValidRequestService emailIsValidRequestService = ApiClient.create(EmailIsValidRequestService.class);

}
