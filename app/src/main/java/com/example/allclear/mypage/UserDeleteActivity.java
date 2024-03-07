package com.example.allclear.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.auth.LoginActivity;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.UserDeleteRequestDto;
import com.example.allclear.data.response.UserDataResponseDto;
import com.example.allclear.databinding.ActivityUserDeleteBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDeleteActivity extends AppCompatActivity {
    private ActivityUserDeleteBinding binding;
    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;
    private String refreshToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDefaultData();
        clickListener();
    }
    private void initDefaultData(){
        preferenceUtil = MyApplication.getPreferences();
        accessToken = preferenceUtil.getAccessToken("FAIL");
        refreshToken = preferenceUtil.getRefreshToken("FAIL");
        userId = preferenceUtil.getUserId(-1L);
    }
    private void clickListener(){
        binding.ivUserDeleteBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.userDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.cbAgree.isChecked()) userDelete();
                else Toast.makeText(getApplicationContext(),"회원 탈퇴에 동의해주세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void userDelete(){
        UserDeleteRequestDto userDeleteRequestDto = new UserDeleteRequestDto();
        userDeleteRequestDto.init(binding.etCurrentPassword.getText().toString());
        ServicePool.userDataService.deleteUser("Bearer "+accessToken,userId,userDeleteRequestDto)
                .enqueue(new Callback<UserDataResponseDto>() {
                    @Override
                    public void onResponse(Call<UserDataResponseDto> call, Response<UserDataResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            Log.i("if",response.body().getCode().toString());
                            String statusCode = response.body().getCode();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            switch (statusCode) {
                                case "COMMON401":
                                case "204":
                                    Toast.makeText(getApplicationContext(),"서버 오류입니다 다시 로그인해주세요.",Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                    return;
                                case "4002":
                                    Toast.makeText(getApplicationContext(),"비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                case "OK":
                                    Toast.makeText(getApplicationContext(),"회원탈퇴에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                    finish();
                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }else{
                        }
                    }
                    @Override
                    public void onFailure(Call<UserDataResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        startActivity(intent);
        finish();
    }
}