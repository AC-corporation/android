package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivityUsaintUpdateBinding;

public class UsaintUpdateActivity extends AppCompatActivity {

    ActivityUsaintUpdateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsaintUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        logInBtnListener();
    }


    //로그인 버튼을 클릭했을때 리스너
    private void logInBtnListener(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAgreement()){
                    String usaintId = binding.etStudentId.getText().toString();
                    String usaintPassword = binding.etUsaintPassword.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), UsaintLoadingActivity.class);
                    intent.putExtra("usaintId",usaintId);
                    intent.putExtra("usaintPassword",usaintPassword);
                    intent.putExtra("Mode","LOG_IN");
                    startActivity(intent);
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

}