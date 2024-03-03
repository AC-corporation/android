package com.example.allclear.auth;

import static android.app.PendingIntent.getActivity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.R;
import com.example.allclear.data.request.TokenRefreshRequestDto;
import com.example.allclear.data.response.TokenRefreshResponseDto;
import com.example.allclear.data.response.UserDataResponseDto;
import com.example.allclear.data.service.GradeAndCurriculumUpdateService;
import com.example.allclear.data.request.LoginRequestDto;
import com.example.allclear.data.response.LoginResponseDto;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.response.TestResponseDto;
import com.example.allclear.data.service.UpdateRequirementService;
import com.example.allclear.data.service.UpdateUserService;
import com.example.allclear.data.request.UsaintUpdateRequestDto;
import com.example.allclear.databinding.ActivityUsaintLoadingBinding;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.MyApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsaintLoadingActivity extends AppCompatActivity {

    private GradeAndCurriculumUpdateService gradeAndCurriculumUpdateService;
    private UpdateUserService updateUserService;
    private UpdateRequirementService updateRequirementService;
    private ActivityUsaintLoadingBinding binding;
    private UsaintUpdateRequestDto usaintUpdateRequestDto;
    private ProgressBar progressBar;
    private String email;
    private String password;
    private String usaintId;
    private String usaintPassword;
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String Mode;
    private PreferenceUtil preferenceUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsaintLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceUtil = MyApplication.getPreferences();

        //서비스 초기화
        gradeAndCurriculumUpdateService = ServicePool.gradeAndCurriculumUpdateService;
        updateUserService = ServicePool.updateUserService;
        updateRequirementService = ServicePool.updateRequirementService;

        //로딩바 초기화
        progressBar = binding.progressBar;

        //동기화 시작
        setAuthVar();
    }

    //로그인 관련 변수를 초기화하는 함수
    void setAuthVar(){
        Intent intent = getIntent();  //인텐트에서 데이터 가져오기
        Mode = intent.getStringExtra("Mode");
        usaintId = intent.getStringExtra("usaintId");
        usaintPassword = intent.getStringExtra("usaintPassword");

        //처음 회원가입한 경우
        if(Mode.equals("SIGN_UP")){
            email = intent.getStringExtra("email");
            password = intent.getStringExtra("password");

            loginRequest(); //로그인 요청 함수 호출
        }
        //로그인 상태에서 업데이트 하는 경우
        else if(Mode.equals("LOG_IN")){
            accessToken = preferenceUtil.getAccessToken(null);
            refreshToken = preferenceUtil.getRefreshToken(null);
            userId = preferenceUtil.getUserId(-1L);

            tokenRefresh();
        }
        else{
            Log.i("Error", "setAuthVar: Not intialized");
        }
    }

    private void tokenRefresh(){
        TokenRefreshRequestDto tokenRequestDto = new TokenRefreshRequestDto();
        tokenRequestDto.init(accessToken,refreshToken);
        ServicePool.tokenRefreshService.TokenRefresh(tokenRequestDto)
                .enqueue(new Callback<TokenRefreshResponseDto>() {
                    @Override
                    public void onResponse(Call<TokenRefreshResponseDto> call, Response<TokenRefreshResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            Log.i("if",response.body().getCode().toString());
                            String statusCode = response.body().getCode();
                            switch (statusCode) {
                                case "OK":
                                    Log.i("tokenRefresh","토큰 재발급 성공");
                                    saveToken(response.body().getData());
                                    initDefaultData();
                                    initUserData();

                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }else{
                        }
                    }
                    @Override
                    public void onFailure(Call<TokenRefreshResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");

                    }
                });
    }

    private void saveToken(TokenRefreshResponseDto.TokenRefreshDto tokenRefreshDto){
        // accessToken, refreshToken, userId를 저장합니다.
        preferenceUtil.setAccessToken(tokenRefreshDto.getAccessToken());
        preferenceUtil.setRefreshToken(tokenRefreshDto.getRefreshToken());

        // 저장된 토큰 확인
        accessToken = preferenceUtil.getAccessToken(null);
        refreshToken = preferenceUtil.getRefreshToken(null);

        Log.i("Saved access token", accessToken != null ? accessToken : "null");
        Log.i("Saved refresh token", refreshToken != null ? refreshToken : "null");
    }

    private void initDefaultData(){
        preferenceUtil = MyApplication.getPreferences();
        accessToken = preferenceUtil.getAccessToken("FAIL");
        refreshToken = preferenceUtil.getRefreshToken("FAIL");
        userId = preferenceUtil.getUserId(-1L);
    }

    private void initUserData(){
        //access 토큰, 유저 아이디 로딩 실패시 예외 처리
        if(accessToken.equals("FAIL") || userId == -1L){
            Toast.makeText(getApplicationContext(),"로그인 정보를 가져오는대 실패했어요. 다시 로그인해 주세요",Toast.LENGTH_SHORT);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        ServicePool.userDataService.getUserData("Bearer "+accessToken,userId)
                .enqueue(new Callback<UserDataResponseDto>() {
                    @Override
                    public void onResponse(Call<UserDataResponseDto> call, Response<UserDataResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            String statusCode = response.body().getCode();
                            Log.i(statusCode,statusCode);
                            switch (statusCode) {
                                case "OK":
                                    updateUser();
                                    return;
                                case "204":
                                    Toast.makeText(getApplicationContext(),"요청한 데이터가 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }else{
                            if(response.code() == 403){
                                tokenRefresh();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<UserDataResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");
                    }
                });
    }

    //백엔드와 통신하는 함수
    private void loginRequest() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.init(email,password);
        ServicePool.testService.Login(loginRequestDto)
                .enqueue(new Callback<LoginResponseDto>() {
                    @Override
                    public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            Log.i("if",response.body().getCode().toString());
                            String statusCode = response.body().getCode();
                            switch (statusCode) {
                                case "OK":
                                    login(response);
                                    return;
                                case "4004":
                                    Toast.makeText(getApplicationContext(),"존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                case "4002":
                                    Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다..",Toast.LENGTH_SHORT).show();
                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }else{
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");
                    }
                });
    }

    //로그인 후 토큰을 불러오는 함수
    private void login(Response<LoginResponseDto> response) {
        tokenSave(response.body().getData().getAccessToken(),response.body().getData().getRefreshToken(),response.body().getData().getMemberId());
    }

    //로그인 후 토큰을 저장하는 함수
    private void tokenSave(String accessToken,String refreshToken, Long memberId){
        // PreferenceUtil 인스턴스를 가져옵니다.
        PreferenceUtil preferenceUtil = MyApplication.getPreferences();

        // accessToken, refreshToken, userId를 저장합니다.
        preferenceUtil.setAccessToken(accessToken);
        preferenceUtil.setRefreshToken(refreshToken);
        preferenceUtil.setUserId(memberId);

        // 저장된 토큰 확인
        String savedAccessToken = preferenceUtil.getAccessToken(null);
        String savedRefreshToken = preferenceUtil.getRefreshToken(null);
        Long savedUserId = preferenceUtil.getUserId(-1L);

        Log.i("Saved access token", savedAccessToken != null ? savedAccessToken : "null");
        Log.i("Saved refresh token", savedRefreshToken != null ? savedRefreshToken : "null");
        Log.i("Saved user id", String.valueOf(savedUserId));

        userId = memberId;
        this.accessToken = accessToken;

        updateUser();
    }

    //학생 정보를 업데이트 하는 함수
    void updateUser(){
        binding.icStudent.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvStudent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경

        usaintUpdateRequestDto = new UsaintUpdateRequestDto(usaintId, usaintPassword);
        Call<TestResponseDto> call = updateUserService.updateUser("Bearer " + accessToken, userId, usaintUpdateRequestDto);
        serverCommunication(call, this::updateScore);
    }

    //성적을 업데이트하는 함수
    void updateScore(){
        binding.icGrade.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvGrade.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경

        Call<TestResponseDto> call = gradeAndCurriculumUpdateService.updateGradeAndCurriculum("Bearer " + accessToken, userId, usaintUpdateRequestDto);
        serverCommunication(call, this::updateRequirement);
    }

    //졸업 요건을 업데이트하는 함수
    void updateRequirement(){
        binding.icGraduation.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvGraduation.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경

        Call<TestResponseDto> call = updateRequirementService.updateRequirement("Bearer " + accessToken, userId, usaintUpdateRequestDto);
        serverCommunication(call, this::AfterSycn);
    }

    //동기화 완료 함수
    void AfterSycn(){
        progressBar.setVisibility(View.GONE); //로딩바 숨기기
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        startActivity(intent);
    }

    // 서버와 통신하는 함수
    void serverCommunication(Call<TestResponseDto> call, Runnable onSuccess) {
        call.enqueue(new Callback<TestResponseDto>() {
            @Override
            public void onResponse(Call<TestResponseDto> call, Response<TestResponseDto> response) {
                if (response.isSuccessful()) {
                    onSuccess.run();
                } else {
                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage().toString(), Toast.LENGTH_SHORT).show(); //동기화 실패시 예외 처리
                    } else {
                        // response.body()가 null인 경우에 대한 처리
                        Toast.makeText(getApplicationContext(), "서버로부터 유효한 응답을 받지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<TestResponseDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와의 통신이 중단되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}