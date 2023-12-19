package com.example.allclear.auth;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.R;
import com.example.allclear.databinding.ActivityEmailAuthBinding;

public class EmailAuthActivity extends AppCompatActivity {
    ActivityEmailAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailSendBtnListener();
        emailCodeButtonListener();
        signupBtnListener();
        backBtnListener();
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

    //인증 이메일 전송 버튼
    private void emailSendBtnListener(){
        binding.btnEmailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmailEmail.getText().toString();
                if (!isValidEmail(email)) {
                    Toast.makeText(getApplicationContext(), "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //버튼 비활성화 및 배경 색상 변경
                    binding.btnEmailSend.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.disabled_blue));
                    binding.btnEmailSend.setEnabled(false);
                    binding.btnEmailSend.setText("전송됨");
                    binding.etEmailEmail.setEnabled(false);
                    //이메일 전송 로직을 여기에 작성
                }
            }
        });
    }

    //인증코드 확인
    private void emailCodeButtonListener(){
        binding.btnEmailCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String originCode = "1111"; //일단 인증코드는 1111
                String yourCode = binding.etEmailCode.getText().toString(); //입력한 코드

                if(originCode.equals(yourCode)){
                    //인증확인 버튼 비활성화
                    binding.btnEmailCodeBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.disabled_blue));
                    binding.btnEmailCodeBtn.setEnabled(false);
                    binding.btnEmailCodeBtn.setText("확인됨");
                    //인증코드 입력 비활성화
                    binding.etEmailCode.setEnabled(false);
                    //회원가입 버튼 활성화
                    binding.btnSignupBtn.setEnabled(true);
                    binding.btnSignupBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue));
                    //아이콘 변경
                    binding.imgCheck.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_checked));

                }
                else{
                    Toast.makeText(v.getContext(),"인증코드가 일치 하지 않습니다! 개발용 인증코드: 1111",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //회원가입 완료 버튼
    private void signupBtnListener(){
        binding.btnSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                startActivity(intent);
            }
        });
    }

    //이메일 형식 검사
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
