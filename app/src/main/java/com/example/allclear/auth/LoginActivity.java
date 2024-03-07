package com.example.allclear.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.allclear.MainPageActivity;
import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.request.LoginRequestDto;
import com.example.allclear.data.request.TokenRefreshRequestDto;
import com.example.allclear.data.response.LoginResponseDto;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.response.TestResponseDto;
import com.example.allclear.data.response.TokenRefreshResponseDto;
import com.example.allclear.data.response.UserDataResponseDto;
import com.example.allclear.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    static final String USER_ID = "User_Id";
    static final String DB = "allClear";
    PreferenceUtil preferenceUtil;
    private String accessToken;
    private String refreshToken;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AllClear);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLogin.setEnabled(false);
        preferenceUtil = MyApplication.getPreferences();
        checkAutoLogin();

        initLoginBtnListener();
        editChanged();
        setupTitleColor();
        signUpBtnListener();
    }

    private void checkAutoLogin() {
        PreferenceUtil preferenceUtil = MyApplication.getPreferences();
        if(preferenceUtil.getAutoLogin(false)) {
            initDefaultData();
            tokenRefresh();
        }
    }

    private void tokenRefresh(){
        TokenRefreshRequestDto tokenRequestDto = new TokenRefreshRequestDto();
        tokenRequestDto.init(accessToken,refreshToken);
        ServicePool.tokenRefreshService.TokenRefresh(tokenRequestDto)
                .enqueue(new Callback<TokenRefreshResponseDto>() {
                    @Override
                    public void onResponse(Call<TokenRefreshResponseDto> call, Response<TokenRefreshResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            Log.i("if",response.body().getCode().toString());
                            String statusCode = response.body().getCode();
                            switch (statusCode) {
                                case "OK":
                                    Log.i("tokenRefresh","토큰 재발급 성공");
                                    saveToken(response.body().getData());
                                    initDefaultData();
                                    initUserData();

                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }else{
                        }
                    }
                    @Override
                    public void onFailure(Call<TokenRefreshResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");

                    }
                });
    }

    private void saveToken(TokenRefreshResponseDto.TokenRefreshDto tokenRefreshDto){
        // accessToken, refreshToken, userId를 저장합니다.
        PreferenceUtil preferenceUtil = MyApplication.getPreferences();
        preferenceUtil.setAccessToken(tokenRefreshDto.getAccessToken());
        preferenceUtil.setRefreshToken(tokenRefreshDto.getRefreshToken());

        // 저장된 토큰 확인
        accessToken = preferenceUtil.getAccessToken(null);
        refreshToken = preferenceUtil.getRefreshToken(null);

        Log.i("Saved access token", accessToken != null ? accessToken : "null");
        Log.i("Saved refresh token", refreshToken != null ? refreshToken : "null");
    }

    private void initDefaultData(){
        accessToken = preferenceUtil.getAccessToken("FAIL");
        refreshToken = preferenceUtil.getRefreshToken("FAIL");
        userId = preferenceUtil.getUserId(-1L);
    }

    private void initUserData(){
        //access 토큰, 유저 아이디 로딩 실패시 예외 처리
        if(accessToken.equals("FAIL") || userId == -1L){
            Toast.makeText(getApplicationContext(),"로그인 정보를 가져오는대 실패했어요. 다시 로그인해 주세요",Toast.LENGTH_SHORT);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        ServicePool.userDataService.getUserData("Bearer "+accessToken,userId)
                .enqueue(new Callback<UserDataResponseDto>() {
                    @Override
                    public void onResponse(Call<UserDataResponseDto> call, Response<UserDataResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            String statusCode = response.body().getCode();
                            Log.i(statusCode,statusCode);
                            switch (statusCode) {
                                case "OK":
                                    Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
                                    finish();
                                    startActivity(intent);
                                    return;
                                case "204":
                                    Toast.makeText(getApplicationContext(),"요청한 데이터가 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }else{
                            if(response.code() == 403){
                                tokenRefresh();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<UserDataResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");
                    }
                });
    }


    private void initLoginBtnListener(){
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!loginCheck()) return;
                loginRequest();
            }
        });
    }

    //학번, 비밀번호 비어있을때 버튼 비활성화
    private void editChanged() {
        binding.etStudentId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setBtnEnabled();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setBtnEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBtnEnabled();
            }
        });
        binding.etUsaintPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setBtnEnabled();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setBtnEnabled();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setBtnEnabled();
            }
        });
    }

    //버튼 비활성화, 활성화 하는 함수
    private void setBtnEnabled() {
        if (emptyCheck()) binding.btnLogin.setEnabled(false);
        else binding.btnLogin.setEnabled(true);
    }

    //학번, 비밀번호 공백 체크하는 함수
    private boolean emptyCheck() {
        return binding.etUsaintPassword.getText().toString().equals("") || binding.etStudentId.getText().toString().equals("");
    }

    //이메일과 비밀번호가 비어있는지 체크하는 함수
    private boolean loginCheck() {
        if (binding.etStudentId.getText().toString().length() == 0 || binding.etUsaintPassword.getText().toString().length() == 0) {
            return false;
        }
        else return true;
    }

    //백엔드와 통신하는 함수
    private void loginRequest() {
        String email = binding.etStudentId.getText().toString();
        String password = binding.etUsaintPassword.getText().toString();
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        System.out.println(email);
        System.out.println(password);
        loginRequestDto.init(email,password);
        ServicePool.testService.Login(loginRequestDto)
                .enqueue(new Callback<LoginResponseDto>() {
                    @Override
                    public void onResponse(Call<LoginResponseDto> call, Response<LoginResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            Log.i("if",response.body().getCode().toString());
                            String statusCode = response.body().getCode();
                            switch (statusCode) {
                                case "OK":
                                    login(response);
                                    return;
                                case "4004":
                                    Toast.makeText(getApplicationContext(),"존재하지 않는 아이디입니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                case "4002":
                                    Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다..",Toast.LENGTH_SHORT).show();
                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }else{
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponseDto> call, Throwable t) {
                        System.out.println("서버 통신 실패");
                    }
                });
    }

    private void tokenSave(String accessToken, String refreshToken, Long memberId) {
        // PreferenceUtil 인스턴스를 가져옵니다.
        PreferenceUtil preferenceUtil = MyApplication.getPreferences();

        // accessToken, refreshToken, userId를 저장합니다.
        preferenceUtil.setAccessToken(accessToken);
        preferenceUtil.setRefreshToken(refreshToken);
        preferenceUtil.setUserId(memberId);

        // 저장된 토큰 확인
        String savedAccessToken = preferenceUtil.getAccessToken(null);
        String savedRefreshToken = preferenceUtil.getRefreshToken(null);
        Long savedUserId = preferenceUtil.getUserId(-1L);

        Log.i("Saved access token", savedAccessToken != null ? savedAccessToken : "null");
        Log.i("Saved refresh token", savedRefreshToken != null ? savedRefreshToken : "null");
        Log.i("Saved user id", String.valueOf(savedUserId));
    }


    //로그인 후 MainPageActivity로 넘어가는 함수
    private void login(Response<LoginResponseDto> response) {
        tokenSave(response.body().getData().getAccessToken(),response.body().getData().getRefreshToken(),response.body().getData().getMemberId());
        manage_auto_login();
        Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
        finish();
        startActivity(intent);
    }

    //자동 로그인을 활성화를 관리하는 함수
    private void manage_auto_login() {
        CheckBox cb = binding.cbAutoLogin;
        PreferenceUtil preferenceUtil = MyApplication.getPreferences();
        preferenceUtil.setAutoLogin(cb.isChecked());
    }

    // 올클 로고 색깔 바꾸는 함수
    private void setupTitleColor() {
        SpannableString spannableEventFirst = new SpannableString(binding.tvLoginLogo.getText());
        spannableEventFirst.setSpan(new ForegroundColorSpan(Color.parseColor("#00A7CB")),
                0,
                1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvLoginLogo.setText(spannableEventFirst);
        SpannableString spannableEventSecond = new SpannableString(binding.tvLoginLogo.getText());
        spannableEventSecond.setSpan(new ForegroundColorSpan(Color.parseColor("#006E93")),
                1,
                2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvLoginLogo.setText(spannableEventSecond);
    }

    // 회원가입 버튼 눌렀을 시 동작하는 함수
    private void signUpBtnListener(){
        binding.tvLoginSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmailAuthActivity.class);
                startActivity(intent);
            }
        });
    }

}