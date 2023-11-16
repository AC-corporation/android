package com.example.allclear;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.databinding.ActivityLoginBinding;

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
                if (!loginCheck()) return;
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
                if(emptyCheck()) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(emptyCheck()) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(emptyCheck()) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }
        });
        binding.loginPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(emptyCheck()) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(emptyCheck()) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(emptyCheck()) binding.loginBtn.setEnabled(false);
                else binding.loginBtn.setEnabled(true);
            }
        });
    }
    private boolean emptyCheck(){
        return binding.loginPasswordEt.getText().toString().equals("")||binding.loginStuedntidEt.getText().toString().equals("");
    }
    private boolean loginCheck(){
        if (binding.loginStuedntidEt.getText().toString().length() != 8){
            Toast.makeText(this, "학번은 8자리를 입력해야합니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else return true;
        //비활성화가 아닌 Toast 띄우기일경우
//        if (emptyCheck()){
//            Toast.makeText(this, "학번 또는 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else if(binding.loginStuedntidEt.getText().toString().length() != 8){
//            Toast.makeText(this, "학번은 8자리를 입력해야합니다.", Toast.LENGTH_SHORT).show();
//            return false;
//        } else return true;
    }
    //학번, 비밀번호를 백엔드에 전송후 토큰 받아오기
    private void loginRequest(){

    }
    private void login(){
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        //토큰을 받아올경우 넣어주기
        //intent.putExtra("token",);
        Log.i("id",binding.loginStuedntidEt.getText().toString());
        Log.i("password",binding.loginPasswordEt.getText().toString());
        finish();
        startActivity(intent);
    }

}