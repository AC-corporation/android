package com.example.allclear.mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.auth.LoginActivity;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.ChangePasswordRequestDto;
import com.example.allclear.data.response.UserDataResponseDto;
import com.example.allclear.databinding.ActivityPasswordChangeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordChangeActivity extends AppCompatActivity {
    private ActivityPasswordChangeBinding binding;
    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;
    private String refreshToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDefaultData();
        clickListener();
        passwordMatchingCheck();
    }
    private void initDefaultData(){
        preferenceUtil = MyApplication.getPreferences();
        accessToken = preferenceUtil.getAccessToken("FAIL");
        refreshToken = preferenceUtil.getRefreshToken("FAIL");
        userId = preferenceUtil.getUserId(-1L);
    }
    private void clickListener(){
        binding.ivPasswordChangeBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.passwordChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("passwordChangeActivity", "btnClick");

                passwordChange();
            }
        });
    }
    private void passwordChange(){
        ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto();
        changePasswordRequestDto.init(binding.etCurrentPassword.getText().toString(),binding.etAfterPassword.getText().toString());
        Log.i(binding.etCurrentPassword.getText().toString(),binding.etAfterPassword.getText().toString());
        ServicePool.userDataService.changePassword("Bearer "+accessToken,userId,changePasswordRequestDto)
                .enqueue(new Callback<UserDataResponseDto>() {
                    @Override
                    public void onResponse(Call<UserDataResponseDto> call, Response<UserDataResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            Log.i("if",response.body().getCode().toString());
                            String statusCode = response.body().getCode();
                            switch (statusCode) {
                                case "OK":
                                    Toast.makeText(getApplicationContext(),"비밀번호 변경에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }else{
                        }
                    }
                    @Override
                    public void onFailure(Call<UserDataResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");

                    }
                });
    }
    private boolean isValidPassword(String password) {
        // 비밀번호 정규식 패턴
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$";
        // 비밀번호가 정규식 패턴에 맞는지 확인
        return password.matches(passwordPattern);
    }
    private void passwordMatchingCheck() {
        binding.etAfterPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.etAfterPassword.getText().toString();

                if (isValidPassword(password)) {
                    binding.passwordChangeBtn.setEnabled(true);
                    binding.ivAfterPassword.setImageResource(R.drawable.ic_checked);
                    binding.passwordChangeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)));
                } else {
                    binding.passwordChangeBtn.setEnabled(false);
                    binding.ivAfterPassword.setImageResource(R.drawable.ic_check);
                    binding.passwordChangeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.disabled_blue)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etAfterPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.etAfterPassword.getText().toString();
                String confirmPassword = binding.etAfterPasswordCheck.getText().toString();
                if (isValidPassword(password) && password.equals(confirmPassword)) {
                    binding.passwordChangeBtn.setEnabled(true);
                    binding.ivAfterPasswordCheck.setImageResource(R.drawable.ic_checked);
                    binding.passwordChangeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)));
                } else {
                    binding.passwordChangeBtn.setEnabled(false);
                    binding.ivAfterPasswordCheck.setImageResource(R.drawable.ic_check);
                    binding.passwordChangeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.disabled_blue)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        startActivity(intent);
        finish();
    }
}