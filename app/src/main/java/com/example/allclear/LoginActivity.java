package com.example.allclear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.allclear.databinding.ActivityLoginBinding;
import com.google.android.material.navigation.NavigationBarView;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AllClear);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btnEnabled();
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
                login();
            }
        });
    }
    //학번, 비밀번호 비어있을때 버튼 비활성화
    private void btnEnabled(){
        if(binding.loginStuedntidEt.toString().equals("") || binding.loginPasswordEt.toString().equals(""))binding.loginBtn.setEnabled(false);
        else binding.loginBtn.setEnabled(true);
    }
    //학번, 비밀번호를 백엔드에 전송후 토큰 받아오기
    private void loginRequest(){

    }
    private void login(){
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        //토큰을 받아올경우 넣어주기
        //intent.putExtra("token",);
        finish();
        startActivity(intent);
    }
}