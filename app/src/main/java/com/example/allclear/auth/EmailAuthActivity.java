package com.example.allclear.auth;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.R;
import com.example.allclear.data.EmailAuthRequestDto;
import com.example.allclear.data.EmailAuthRequestService;
import com.example.allclear.data.EmailIsValidRequestDto;
import com.example.allclear.data.EmailIsValidRequestService;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.TestResponseDto;
import com.example.allclear.databinding.ActivityEmailAuthBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailAuthActivity extends AppCompatActivity {
    ActivityEmailAuthBinding binding;
    private CountDownTimer countDownTimer;
    private EmailAuthRequestService emailAuthRequestService;
    private EmailIsValidRequestService emailIsValidRequestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        emailAuthRequestService = ServicePool.emailAuthRequestService;
        emailIsValidRequestService = ServicePool.emailIsValidRequestService;

        emailSendBtnListener();
        emailCodeButtonListener();
        signupBtnListener();
        backBtnListener();
        resendBtnListener();
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
                    //이메일 전송 로직
                    email_auth_request(email);
                    //타이머 로직, 3분의 타이머를 설정합니다. (180초)
                    startTimer();
                }
            }
        });
    }

    //인증 코드 확인 리스너
    private void emailCodeButtonListener(){
        binding.btnEmailCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etEmailEmail.getText().toString(); //입력한 이메일
                String yourCode = binding.etEmailCode.getText().toString(); //입력한 코드

                //인증코드 일치검사: if(is_email_auth_code_match(email, yourCode))
                //개발을 위해 임시로 true로 해놓음
                if(true){
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
                    // 인증 코드가 일치하면 타이머를 중지합니다.
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    //타이머, 인증안내 문구 숨기기
                    binding.tvAuthCodeInfo.setVisibility(View.INVISIBLE);
                    binding.tvTimer.setVisibility(View.INVISIBLE);
                }
                //인증코드 불일치
                else{
                    Toast.makeText(v.getContext(),"인증코드가 일치 하지 않습니다!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //회원가입 완료 버튼
    private void signupBtnListener(){
        binding.btnSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                //이메일을 인텐트로 전달
                intent.putExtra("email",binding.etEmailEmail.getText().toString());
                startActivity(intent);
            }
        });
    }

    //이메일 형식 검사 메서드
    //숭실 이메일 경우 : true
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@soongsil\\.ac\\.kr";
        return email.matches(emailPattern);
    }

    //타이머 시작 메서드
    private void startTimer() {
        binding.tvAuthCodeInfo.setText("인증코드를 전송했습니다!");
        binding.tvAuthCodeInfo.setVisibility(View.VISIBLE);
        binding.tvTimer.setVisibility(View.VISIBLE);

        // CountDownTimer 객체를 생성하고 시작
        countDownTimer = new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                // 남은 시간(초)를 계산하고 타이머 TextView를 업데이트합니다.
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                binding.tvTimer.setText(String.format("%d분 %02d초", minutes, seconds));
            }

            public void onFinish() {
                // 타이머가 끝나면 '시간이 만료되었습니다' 메시지를 표시
                binding.tvAuthCodeInfo.setText("시간이 만료되었습니다.");
                binding.tvTimer.setVisibility(View.INVISIBLE);
                //타이머 끝나면 코드 확인버튼 비활성화
                binding.btnEmailCodeBtn.setEnabled(false);
                binding.btnEmailCodeBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.disabled_blue));
                //재전송 버튼 활성화
                binding.btnResend.setEnabled(true);
                binding.btnResend.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    //재전송 버튼 리스너
    private void resendBtnListener(){
        binding.btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //재전송 버튼 비활성화
                binding.btnResend.setEnabled(false);
                binding.btnResend.setVisibility(View.INVISIBLE);
                //전송 로직
                String email = binding.etEmailEmail.getText().toString();
                email_auth_request(email);
                //타이머 시작
                startTimer();
                //확인버튼 활성화
                binding.btnEmailCodeBtn.setEnabled(true);
                binding.btnEmailCodeBtn.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.first_blue));
            }
        });
    }

    //이메일 인증코드를 발송하는 메서드
    private void email_auth_request(String email){
        EmailAuthRequestDto emailAuthRequestDto = new EmailAuthRequestDto();
        emailAuthRequestDto.setEmail(email);

        Call<List<TestResponseDto>> call = emailAuthRequestService.emailAuth(emailAuthRequestDto);

        call.enqueue(new Callback<List<TestResponseDto>>() {
            @Override
            public void onResponse(Call<List<TestResponseDto>> call, Response<List<TestResponseDto>> response) {

            }

            @Override
            public void onFailure(Call<List<TestResponseDto>> call, Throwable t) {

            }
        });
    }

    //이메일 인증번호가 맞는지 확인 함수
    private boolean is_email_auth_code_match(String email, String code){
        EmailIsValidRequestDto emailIsValidRequestDto = new EmailIsValidRequestDto();
        emailIsValidRequestDto.setEmail(email);
        emailIsValidRequestDto.setCode(code);

        Call<List<TestResponseDto>> call = emailIsValidRequestService.emailIsValid(emailIsValidRequestDto);

        call.enqueue(new Callback<List<TestResponseDto>>() {
            @Override
            public void onResponse(Call<List<TestResponseDto>> call, Response<List<TestResponseDto>> response) {

            }

            @Override
            public void onFailure(Call<List<TestResponseDto>> call, Throwable t) {

            }
        });
        return false;
    }

}
