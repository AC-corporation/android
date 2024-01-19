package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.allclear.R;
import com.example.allclear.data.LoginRequestDto;
import com.example.allclear.data.LoginResponseDto;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.TestResponseDto;
import com.example.allclear.data.TestService;
import com.example.allclear.databinding.ActivityLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AllClear);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setEnabled(false);

        checkServer();

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
//                login();
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
        String email = binding.etLoginEmail.toString();
        String password = binding.etLoginPassword.toString();
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.init(email,password);
        ServicePool.testService.Login(loginRequestDto)
                .enqueue(new Callback<LoginResponseDto>() {
                    @Override
                    public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("Login",response.toString());
                            Log.i("Login",response.body().getMessage());
                        }else{
                            Log.i("Login", String.valueOf(response.code()));
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");
                        Log.i("Login",t.getMessage().toString());
                    }
                });
    }

    //로그인 후 MainPageActivity로 넘어가는 함수
    private void login() {
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        //토큰을 받아올경우 넣어주기
        //intent.putExtra("token",);
        Log.i("id", binding.etLoginEmail.getText().toString());
        Log.i("password", binding.etLoginPassword.getText().toString());
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