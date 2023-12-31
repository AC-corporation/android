package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signUpBtnNextListener();
        backBtnListener();
        passwordMatchingCheck();
    }

    //유세인트 로그인으로 가는 버튼 눌렀을 시 동작하는 함수
    //유세인트 로그인이 아직 구현이 안되서 메인으로 가게 해놓음
    private void signUpBtnNextListener(){
        binding.btnSignupNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), UsaintLoginActivity.class);
                    startActivity(intent);
            }
        });
    }

    //입력한 비밀번호가 일치하는지 체크하는 함수
    private void passwordMatchingCheck() {
        binding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.etPassword.getText().toString();
                String confirmPassword = binding.etPasswordCheck.getText().toString();

                if (!password.isEmpty() && password.equals(confirmPassword)) {
                    binding.btnSignupNext.setEnabled(true);
                    binding.imgCheck.setImageResource(R.drawable.ic_checked);
                    binding.btnSignupNext.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)));
                } else {
                    binding.btnSignupNext.setEnabled(false);
                    binding.imgCheck.setImageResource(R.drawable.ic_check);
                    binding.btnSignupNext.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.disabled_blue)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.etPasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.etPassword.getText().toString();
                String confirmPassword = binding.etPasswordCheck.getText().toString();
                if (!password.isEmpty() && password.equals(confirmPassword)) {
                    binding.btnSignupNext.setEnabled(true);
                    binding.imgCheck.setImageResource(R.drawable.ic_checked);
                    binding.btnSignupNext.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.first_blue)));
                } else {
                    binding.btnSignupNext.setEnabled(false);
                    binding.imgCheck.setImageResource(R.drawable.ic_check);
                    binding.btnSignupNext.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.disabled_blue)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    // 뒤로가기 버튼
    private void backBtnListener(){
        binding.ivSignupBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}