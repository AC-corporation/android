package com.example.allclear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        binding.loginBtn.setEnabled(false);
        editChanged();
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginRequest();
                login();
            }
        });
    }
    //학번, 비밀번호 비어있을때 버튼 비활성화
    private void editChanged(){
        binding.loginStuedntidEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.loginStuedntidEt.getText().toString().equals("")||binding.loginPasswordEt.getText().toString().equals("")) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.loginStuedntidEt.getText().toString().equals("")||binding.loginPasswordEt.getText().toString().equals("")) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(binding.loginStuedntidEt.getText().toString().equals("")||binding.loginPasswordEt.getText().toString().equals("")) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }
        });
        binding.loginPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.loginPasswordEt.getText().toString().equals("")||binding.loginStuedntidEt.getText().toString().equals("")) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(binding.loginPasswordEt.getText().toString().equals("")||binding.loginStuedntidEt.getText().toString().equals("")) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(binding.loginPasswordEt.getText().toString().equals("")||binding.loginStuedntidEt.getText().toString().equals("")) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }
        });
    }
    //학번, 비밀번호를 백엔드에 전송후 토큰 받아오기
    private void loginRequest(){

    }
    private void login(){
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        //토큰을 받아올경우 넣어주기
        //intent.putExtra("token",);
        Log.i("id",binding.loginStuedntidEt.getText().toString());
        Log.i("id",binding.loginPasswordEt.getText().toString());
        finish();
        startActivity(intent);
    }
}