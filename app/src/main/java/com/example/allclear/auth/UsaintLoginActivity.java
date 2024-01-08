package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.allclear.MainPageActivity;
import com.example.allclear.data.ApiClient;
import com.example.allclear.data.User;
import com.example.allclear.data.UserAPI;
import com.example.allclear.databinding.ActivityUsaintLoginBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsaintLoginActivity extends AppCompatActivity {
    private ActivityUsaintLoginBinding binding;
    private String email;
    private String password;
    private UserAPI userAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsaintLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrofit 객체를 ApiClient 클래스를 통해 가져옵니다.
        userAPI = ApiClient.create(UserAPI.class);

        //인텐트에서 데이터 가져오기
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        logInBtnListener();
    }

    //로그인 버튼을 클릭했을때 리스너
    private void logInBtnListener(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAgreement()){
                    signUpRequest();
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

    //서버에 회원가입 요청
    private void signUpRequest(){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUsaintId(binding.etLoginEmail.getText().toString());
        user.setUsaintPassword(binding.etLoginPassword.getText().toString());

        Call<Void> call = userAPI.signUp(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와의 통신이 중단되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
