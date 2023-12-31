package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.databinding.ActivityUsaintLoginBinding;

public class UsaintLoginActivity extends AppCompatActivity {
    private ActivityUsaintLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsaintLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        logInBtnListener();
    }

    //로그인 버튼을 클릭했을때 리스너
    //약관에 동의했는지 검사
    private void logInBtnListener(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.cbSignupAgreement.isChecked()){
                    Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(v.getContext(),"로그인하려면 약관에 동의해 주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}