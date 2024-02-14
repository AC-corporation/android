package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.LoginRequestDto;
import com.example.allclear.data.LoginResponseDto;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.TestResponseDto;
import com.example.allclear.data.Utils;
import com.example.allclear.databinding.ActivityLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
//    private PreferenceUtil preferences = MyApplication.getPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AllClear);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setEnabled(false);


//        checkServer();

        initLoginBtnListener();
        editChanged();
        setupTitleColor();
        signUpBtnListener();

    }

    private void checkServer(){
        // 서버 통신
        ServicePool.testService.getListFromServer()
                .enqueue(new Callback<TestResponseDto>() {
                    @Override
                    public void onResponse(Call<TestResponseDto> call, Response<TestResponseDto> response) {
                        Toast.makeText(LoginActivity.this, "서버 통신 성공", Toast.LENGTH_SHORT).show();
                        Log.i("test",response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<TestResponseDto> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "서버 통신 실패", Toast.LENGTH_SHORT).show();
                        Log.i("test",call.toString());
                        Log.i("test",t.getMessage().toString());
                    }
                });
    }

    private void initLoginBtnListener(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!loginCheck()) return;
                loginRequest();
            }
        });
    }

    //학번, 비밀번호 비어있을때 버튼 비활성화
    private void editChanged() {
        binding.etLoginEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setBtnEnabled();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setBtnEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBtnEnabled();
            }
        });
        binding.etLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setBtnEnabled();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setBtnEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBtnEnabled();
            }
        });
    }

    //버튼 비활성화, 활성화 하는 함수
    private void setBtnEnabled() {
        if (emptyCheck()) binding.btnLogin.setEnabled(false);
        else binding.btnLogin.setEnabled(true);
    }

    //학번, 비밀번호 공백 체크하는 함수
    private boolean emptyCheck() {
        return binding.etLoginPassword.getText().toString().equals("") || binding.etLoginEmail.getText().toString().equals("");
    }

    //이메일과 비밀번호가 비어있는지 체크하는 함수
    private boolean loginCheck() {
        if (binding.etLoginEmail.getText().toString().length() == 0 || binding.etLoginPassword.getText().toString().length() == 0) {
            return false;
        }
        else return true;
    }

    //백엔드와 통신하는 함수
    private void loginRequest() {
        String email = binding.etLoginEmail.getText().toString();
        String password = binding.etLoginPassword.getText().toString();
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

    private void tokenSave(String accessToken,String refreshToken, Long memberId){
//        preferences.setAccessToken(accessToken);
//        preferences.setRefreshToken(refreshToken);
//        Utils.setAccessToken(accessToken);
//        Utils.setRefreshToken(refreshToken);
        SharedPreferences preferences = getSharedPreferences("allClear", MODE_PRIVATE);
        Log.i("access",accessToken);
        Log.i("re",refreshToken);
        preferences.edit().putString("Access_Token",accessToken);
        preferences.edit().putString("Refresh_Token",refreshToken);
        preferences.edit().putLong("User_Id",memberId);
    }
    //로그인 후 MainPageActivity로 넘어가는 함수
    private void login(Response<LoginResponseDto> response) {
        tokenSave(response.body().getData().getAccessToken(),response.body().getData().getRefreshToken(),response.body().getData().getMemberId());
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        finish();
        startActivity(intent);
    }

    // 올클 로고 색깔 바꾸는 함수
    private void setupTitleColor() {
        SpannableString spannableEventFirst = new SpannableString(binding.tvLoginLogo.getText());
        spannableEventFirst.setSpan(new ForegroundColorSpan(Color.parseColor("#00A7CB")),
                0,
                1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvLoginLogo.setText(spannableEventFirst);
        SpannableString spannableEventSecond = new SpannableString(binding.tvLoginLogo.getText());
        spannableEventSecond.setSpan(new ForegroundColorSpan(Color.parseColor("#006E93")),
                1,
                2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvLoginLogo.setText(spannableEventSecond);
    }

    // 회원가입 버튼 눌렀을 시 동작하는 함수
    private void signUpBtnListener(){
        binding.tvLoginSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmailAuthActivity.class);
                startActivity(intent);
            }
        });
    }

}