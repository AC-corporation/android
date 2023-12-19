package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    }

    // 이메일 인증으로 가는 버튼 눌렀을 시 동작하는 함수
    private void signUpBtnNextListener(){
        binding.btnSignupNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.cbSignupAgreement.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), EmailAuthActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(v.getContext(),"개인정보 이용에 관한 약관에 동의해 주세요",Toast.LENGTH_SHORT).show();
                    return;
                };
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