package com.example.allclear.data;

public class ServicePool {
    public static final TestService testService = ApiClient.create(TestService.class);
    public static final SignUpService signUpService = ApiClient.create(SignUpService.class);

}
