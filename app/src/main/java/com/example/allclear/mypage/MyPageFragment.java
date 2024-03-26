package com.example.allclear.mypage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allclear.MyApplication;
import com.example.allclear.auth.LoginActivity;
import com.example.allclear.auth.UsaintUpdateActivity;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TokenRefreshRequestDto;
import com.example.allclear.data.response.TokenRefreshResponseDto;
import com.example.allclear.data.response.UserDataResponseDto;
import com.example.allclear.databinding.FragmentMyPageBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPageFragment extends Fragment {
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    static final String USER_ID = "User_Id";
    static final String DB = "allClear";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MyPageFragment() {
        // Required empty public constructor
    }

    public static MyPageFragment newInstance(String param1, String param2) {
        MyPageFragment fragment = new MyPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FragmentMyPageBinding binding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;
    private String refreshToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyPageBinding.inflate(inflater, container, false);
        preferenceUtil = MyApplication.getPreferences();
        initDefaultData();
        initUserData();
        clickListener();
        btnUpdateListener();

        return binding.getRoot();
    }
    private void initDefaultData(){
        preferenceUtil = MyApplication.getPreferences();
        accessToken = preferenceUtil.getAccessToken("FAIL");
        refreshToken = preferenceUtil.getRefreshToken("FAIL");
        userId = preferenceUtil.getUserId(-1L);
    }

    private void initUserData(){
        //access 토큰, 유저 아이디 로딩 실패시 예외 처리
        if(accessToken.equals("FAIL") || userId == -1L){
            Toast.makeText(getContext(),"로그인 정보를 가져오는대 실패했어요. 다시 로그인해 주세요",Toast.LENGTH_SHORT);
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
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
                                    initData(response.body().getData());
                                    return;
                                case "204":
                                    Toast.makeText(getContext(),"요청한 데이터가 존재하지 않습니다.",Toast.LENGTH_SHORT).show();
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
    private void initData(UserDataResponseDto.MemberResponseDto userData){
        binding.tvMyPageName.setText(userData.getMemberName());
        binding.tvSchool.setText(userData.getUsiversity());
        binding.tvMajor.setText(userData.getMajor());
        binding.tvEmail.setText(userData.getEmail());
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

    //정보 업데이트 버튼의 리스너
    private void btnUpdateListener() {
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UsaintUpdateActivity.class);
                startActivity(intent);
            }
        });
    }
    private void saveToken(TokenRefreshResponseDto.TokenRefreshDto tokenRefreshDto){
        // accessToken, refreshToken, userId를 저장합니다.
        preferenceUtil.setAccessToken(tokenRefreshDto.getAccessToken());
        preferenceUtil.setRefreshToken(tokenRefreshDto.getRefreshToken());

        // 저장된 토큰 확인
        accessToken = preferenceUtil.getAccessToken(null);
        refreshToken = preferenceUtil.getRefreshToken(null);

        Log.i("Saved access token", accessToken != null ? accessToken : "null");
        Log.i("Saved refresh token", refreshToken != null ? refreshToken : "null");
    }
    private void clickListener(){
        binding.cvMyPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다이얼로그
                Intent intent = new Intent(getContext(),PasswordChangeActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        binding.tvGradeMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MailWebViewActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        binding.tvGradeContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        binding.tvGradePersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tvGradeLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogout();
            }
        });
        binding.tvUserDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),UserDeleteActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    private void userLogout(){
        ServicePool.userDataService.logout("Bearer "+accessToken,userId)
                .enqueue(new Callback<UserDataResponseDto>() {
                    @Override
                    public void onResponse(Call<UserDataResponseDto> call, Response<UserDataResponseDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Log.i("if",response.toString());
                            Log.i("if",response.body().getMessage());
                            Log.i("if",response.body().getCode().toString());
                            String statusCode = response.body().getCode();
                            switch (statusCode) {
                                case "OK":
                                    //자동 로그인 헤제, 토큰 삭제
                                    preferenceUtil.clearLoginInfo();
                                    Toast.makeText(getContext(),"로그아웃에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(),LoginActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
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

                    }
                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}