package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.R;
import com.example.allclear.data.GradeAndCurriculumUpdateService;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.TestResponseDto;
import com.example.allclear.data.UpdateRequirementService;
import com.example.allclear.data.UpdateUserService;
import com.example.allclear.data.UsaintUpdateRequestDto;
import com.example.allclear.databinding.ActivityUsaintLoadingBinding;
import com.example.allclear.databinding.ActivityUsaintLoginBinding;

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
    private long userId;  //변수 초기화 해주는 메서드 필요
    private String usaintId;
    private String usaintPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsaintLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gradeAndCurriculumUpdateService = ServicePool.gradeAndCurriculumUpdateService;
        updateUserService = ServicePool.updateUserService;
        updateRequirementService = ServicePool.updateRequirementService;


        Intent intent = getIntent(); //인텐트에서 데이터 가져오기
        usaintId = intent.getStringExtra("usaintId");
        usaintPassword = intent.getStringExtra("usaintPassword");

        progressBar = binding.progressBar; //로딩바 초기화

        updateUser();  //동기화 시작
    }

    // 서버와 통신하는 함수
    void serverCommunication(Call<TestResponseDto> call, Runnable onSuccess) {
        call.enqueue(new Callback<TestResponseDto>() {
            @Override
            public void onResponse(Call<TestResponseDto> call, Response<TestResponseDto> response) {
                if (response.isSuccessful()) {
                    onSuccess.run();
                } else {
                    signUpResultHandler(response.code()); //동기화 실패시 예외 처리
                }
            }
            @Override
            public void onFailure(Call<TestResponseDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와의 통신이 중단되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //학생정보를 업데이트하는 함수
    void updateUser(){
        usaintUpdateRequestDto = new UsaintUpdateRequestDto(usaintId, usaintPassword);
        Call<TestResponseDto> call = updateUserService.updateUser(userId, usaintUpdateRequestDto);
        serverCommunication(call, this::updateScore);
    }

    //성적을 업데이트하는 함수
    void updateScore(){
        binding.icStudent.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvStudent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경
        Call<TestResponseDto> call = gradeAndCurriculumUpdateService.updateGradeAndCurriculum(userId, usaintUpdateRequestDto);
        serverCommunication(call, this::updateRequirement);
    }

    //졸업 요건을 업데이트하는 함수
    void updateRequirement(){
        binding.icGrade.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvGrade.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경
        Call<TestResponseDto> call = updateRequirementService.updateRequirement(userId, usaintUpdateRequestDto);
        serverCommunication(call, this::AfterSycn);
    }

    //동기화 완료 함수
    void AfterSycn(){
        binding.icGraduation.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue)); //아이콘 색상 변경
        binding.tvGraduation.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)); //텍스트 색상 변경
        progressBar.setVisibility(View.GONE); //로딩바 숨기기
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        startActivity(intent);
    }

    //가입 실패를 처리하는 메서드
    private void signUpResultHandler(int code) {
        switch(code) {
            case 4101:
                Toast.makeText(getApplicationContext(), "유세인트 아이디 또는 비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case 4102:
                Toast.makeText(getApplicationContext(), "유세인트 서버를 이용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                break;
            case 4103:
                Toast.makeText(getApplicationContext(), "크롤링에 실패했습니다.", Toast.LENGTH_SHORT).show();
                break;
            case 4104:
                Toast.makeText(getApplicationContext(), "유세인트 데이터 저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                break;
            case 4005:
                Toast.makeText(getApplicationContext(), "이미 등록된 이메일입니다.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "동기화 중 오류 발생", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}