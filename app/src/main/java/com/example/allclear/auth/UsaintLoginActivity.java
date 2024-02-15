package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.allclear.MainPageActivity;
import com.example.allclear.data.ApiClient;
import com.example.allclear.data.MemberSignupRequestDto;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.SignUpService;
import com.example.allclear.data.TestResponseDto;
import com.example.allclear.databinding.ActivityUsaintLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsaintLoginActivity extends AppCompatActivity {
    private ActivityUsaintLoginBinding binding;
    private String email;
    private String password;
    private String usaintId;
    private String usaintPassword;
    private SignUpService signUpService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsaintLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrofit 객체를 ApiClient 클래스를 통해 가져옵니다.
        signUpService = ServicePool.signUpService;

        //인텐트에서 데이터 가져오기
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        logInBtnListener();
    }

    //로그인 버튼을 클릭했을때 리스너
    private void logInBtnListener(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAgreement()){
                    signUpRequest();
                }
                else{
                    Toast.makeText(v.getContext(),"로그인하려면 약관에 동의해 주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //약관에 동의했는지 검사하는 메서드
    private boolean checkAgreement(){
        return binding.cbSignupAgreement.isChecked();
    }

    //서버에 회원가입 요청하는 메서드
    private void signUpRequest() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("회원가입 중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        usaintId = binding.etStudentId.getText().toString();
        usaintPassword = binding.etUsaintPassword.getText().toString();

        MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto();
        memberSignupRequestDto.setEmail(email);
        memberSignupRequestDto.setPassword(password);
        memberSignupRequestDto.setUsaintId(usaintId);
        memberSignupRequestDto.setUsaintPassword(usaintPassword);

        Call<TestResponseDto> call = signUpService.signUp(memberSignupRequestDto);
        call.enqueue(new Callback<TestResponseDto>() {
            @Override
            public void onResponse(Call<TestResponseDto> call, Response<TestResponseDto> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body().getIsSuccess()) {
                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), UsaintLoadingActivity.class);
                    intent.putExtra("usaintId",usaintId);
                    intent.putExtra("usaintPassword",usaintPassword);
                    startActivity(intent);
                } else {
                    signUpResultHandler(response.body().getCode()); //가입 실패시 예외 처리
                }
            }
            @Override
            public void onFailure(Call<TestResponseDto> call, Throwable t) {
                progressDialog.dismiss();
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
