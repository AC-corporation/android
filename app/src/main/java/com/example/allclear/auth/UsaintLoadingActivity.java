package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.R;
import com.example.allclear.data.GradeAndCurriculumUpdateService;
import com.example.allclear.data.LoginRequestDto;
import com.example.allclear.data.LoginResponseDto;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.Utils;
import com.example.allclear.data.response.TestResponseDto;
import com.example.allclear.data.UpdateRequirementService;
import com.example.allclear.data.UpdateUserService;
import com.example.allclear.data.UsaintUpdateRequestDto;
import com.example.allclear.databinding.ActivityUsaintLoadingBinding;

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
    private Long MemberId;
    private String AccessToken;
    private SharedPreferences preferences;
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    static final String USER_ID = "User_Id";
    static final String DB = "allClear";
    Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsaintLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        usaintId = intent.getStringExtra("usaintId");
        usaintPassword = intent.getStringExtra("usaintPassword");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        loginRequest(); //로그인 요청 함수 호출
    }

    //백엔드와 통신하는 함수
    private void loginRequest() {
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        System.out.println(email);
        System.out.println(password);
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
        preferences = getSharedPreferences(DB, MODE_PRIVATE);
        Log.i("access",accessToken);
        Log.i("re",refreshToken);
        Log.i("memId", ""+memberId);
        MemberId = memberId;
        AccessToken = accessToken;
        preferences.edit().putString(ACCESS_TOKEN,accessToken).apply();
        preferences.edit().putString(REFRESH_TOKEN,refreshToken).apply();
        preferences.edit().putLong(USER_ID,memberId).apply();

        updateUser();
    }

    //학생 정보를 업데이트 하는 함수
    void updateUser(){
        binding.icStudent.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvStudent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경

        usaintUpdateRequestDto = new UsaintUpdateRequestDto(usaintId, usaintPassword);
        Call<TestResponseDto> call = updateUserService.updateUser("Bearer " + AccessToken , MemberId, usaintUpdateRequestDto);
        serverCommunication(call, this::updateScore);
    }

    //성적을 업데이트하는 함수
    void updateScore(){
        binding.icGrade.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvGrade.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경

        Call<TestResponseDto> call = gradeAndCurriculumUpdateService.updateGradeAndCurriculum("Bearer " + AccessToken , MemberId, usaintUpdateRequestDto);
        serverCommunication(call, this::updateRequirement);
    }

    //졸업 요건을 업데이트하는 함수
    void updateRequirement(){
        binding.icGraduation.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvGraduation.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경

        Call<TestResponseDto> call = updateRequirementService.updateRequirement("Bearer " + AccessToken , MemberId, usaintUpdateRequestDto);
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
                        signUpResultHandler(response.body().getCode()); //동기화 실패시 예외 처리
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

    //응답 실패를 처리하는 메서드
    private void signUpResultHandler(String code) {
        switch(code) {
            case "4101":
                Toast.makeText(getApplicationContext(), "유세인트 아이디 또는 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case "4102":
                Toast.makeText(getApplicationContext(), "유세인트 서버를 이용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                break;
            case "4103":
                Toast.makeText(getApplicationContext(), "크롤링에 실패했습니다.", Toast.LENGTH_SHORT).show();
                break;
            case "4104":
                Toast.makeText(getApplicationContext(), "유세인트 데이터 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                break;
            case "4005":
                Toast.makeText(getApplicationContext(), "이미 등록된 이메일입니다.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "알수 없는 오류 발생", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}