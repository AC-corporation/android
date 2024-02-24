package com.example.allclear.graduation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.auth.LoginActivity;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TokenRefreshRequestDto;
import com.example.allclear.data.response.GraduationDto;
import com.example.allclear.data.response.TokenRefreshResponseDto;
import com.example.allclear.databinding.FragmentGraduationBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraduationFragment extends Fragment {
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    static final String USER_ID = "User_Id";
    static final String DB = "allClear";

    private int totalItemCount = 0;
    private int totalCompleteCount = 0;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;
    private String refreshToken;
    ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> requirementComponentDtoList;

    public GraduationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private FragmentGraduationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraduationBinding.inflate(inflater, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(DB, MODE_PRIVATE);
        Log.i(ACCESS_TOKEN, preferences.getString(ACCESS_TOKEN, ""));
        Log.i(REFRESH_TOKEN, preferences.getString(REFRESH_TOKEN, ""));
        Log.i(USER_ID, String.valueOf(preferences.getLong(USER_ID, 0)));

        checkServer(preferences.getString(ACCESS_TOKEN, ""), preferences.getLong(USER_ID, 0));

        return binding.getRoot();
    }

    private void checkServer(String accessToken, Long userId) {
        ServicePool.graduationService.getListFromServer("Bearer " + accessToken, userId)
                .enqueue(new Callback<GraduationDto>() {
                    @Override
                    public void onResponse(Call<GraduationDto> call, Response<GraduationDto> response) {

                        if (response.isSuccessful()) {

                            GraduationDto graduationDto = response.body();

                            if (graduationDto != null && graduationDto.data != null) {
                                ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> totalList = new ArrayList<>();
                                ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> generalList = new ArrayList<>();
                                ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> majorList = new ArrayList<>();

                                for (GraduationDto.RequirementResponseDto.RequirementComponentDto dto : graduationDto.data.getRequirementComponentList()) {
                                    if (dto.getRequirementCategory().contains(getString(R.string.graduation_total))) {
                                        totalList.add(dto);
                                    } else if (dto.getRequirementCategory().contains(getString(R.string.graduation_general))) {
                                        generalList.add(dto);
                                    } else if (dto.getRequirementCategory().contains(getString(R.string.graduation_major))) {
                                        majorList.add(dto);
                                    }
                                }

                                totalCompleteCount += initTotalAdapter(totalList);
                                totalCompleteCount += initGeneralAdapter(generalList);
                                totalCompleteCount += initMajorAdapter(majorList);

                                setGraduationCriteria();

                            }
                        } else {
                            if (response.code() == 403) {
                                tokenRefresh();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GraduationDto> call, Throwable t) {
                        Toast.makeText(requireActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private int initTotalAdapter(ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> totalList) {
        GraduationAdapter graduationAdapter = new GraduationAdapter(totalList);
        binding.rvGraduationTotal.setAdapter(graduationAdapter);
        binding.rvGraduationTotal.setLayoutManager(new LinearLayoutManager(requireActivity()));

        int complete = 0;

        for (int i = 0; i < graduationAdapter.getItemCount(); i++) {
            GraduationDto.RequirementResponseDto.RequirementComponentDto item = graduationAdapter.getItem(i);
            if (item.getRequirementCriteria() == item.getRequirementComplete()) {
                complete++;
            }
        }

        totalItemCount += graduationAdapter.getItemCount();

        return complete;
    }

    private int initGeneralAdapter(ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> generalList) {
        GraduationAdapter graduationAdapter = new GraduationAdapter(generalList);
        binding.rvGraduationGeneral.setAdapter(graduationAdapter);
        binding.rvGraduationGeneral.setLayoutManager(new LinearLayoutManager((requireActivity())));

        int complete = 0;

        for (int i = 0; i < graduationAdapter.getItemCount(); i++) {
            GraduationDto.RequirementResponseDto.RequirementComponentDto item = graduationAdapter.getItem(i);
            if (item.getRequirementCriteria() == item.getRequirementComplete()) {
                complete++;
            }
        }

        totalItemCount += graduationAdapter.getItemCount();

        return complete;
    }

    private int initMajorAdapter(ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> majorList) {
        GraduationAdapter graduationAdapter = new GraduationAdapter(majorList);
        binding.rvGraduationMajor.setAdapter(graduationAdapter);
        binding.rvGraduationMajor.setLayoutManager(new LinearLayoutManager(requireActivity()));

        int complete = 0;

        for (int i = 0; i < graduationAdapter.getItemCount(); i++) {
            GraduationDto.RequirementResponseDto.RequirementComponentDto item = graduationAdapter.getItem(i);
            if (item.getRequirementCriteria() == item.getRequirementComplete()) {
                complete++;
            }
        }

        totalItemCount += graduationAdapter.getItemCount();

        return complete;
    }

    private void setGraduationCriteria() {
        String graduationCriteria = getResources().getString(R.string.graduation_criteria, totalCompleteCount, totalItemCount);
        binding.tvGraduationCurr.setText(graduationCriteria);
    }

    private void tokenRefresh() {
        TokenRefreshRequestDto tokenRequestDto = new TokenRefreshRequestDto();
        tokenRequestDto.init(accessToken, refreshToken);
        ServicePool.tokenRefreshService.TokenRefresh(tokenRequestDto)
                .enqueue(new Callback<TokenRefreshResponseDto>() {
                    @Override
                    public void onResponse(Call<TokenRefreshResponseDto> call, Response<TokenRefreshResponseDto> response) {
                        if (response.isSuccessful()) {
                            String statusCode = response.body().getCode();
                            switch (statusCode) {
                                case "OK":
                                    Log.i("tokenRefresh", "토큰 재발급 성공");
                                    saveToken(response.body().getData());
                                    initDefaultData();
                                    initUserData();
                                    return;
                                default:
                                    // 기타 상황에 대한 처리
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenRefreshResponseDto> call, Throwable t) {
                        Toast.makeText(requireActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveToken(TokenRefreshResponseDto.TokenRefreshDto tokenRefreshDto) {
        // accessToken, refreshToken, userId를 저장합니다.
        preferenceUtil.setAccessToken(tokenRefreshDto.getAccessToken());
        preferenceUtil.setRefreshToken(tokenRefreshDto.getRefreshToken());

        // 저장된 토큰 확인
        accessToken = preferenceUtil.getAccessToken(null);
        refreshToken = preferenceUtil.getRefreshToken(null);

    }

    private void initDefaultData() {
        preferenceUtil = MyApplication.getPreferences();
        accessToken = preferenceUtil.getAccessToken("FAIL");
        refreshToken = preferenceUtil.getRefreshToken("FAIL");
        userId = preferenceUtil.getUserId(-1L);
    }

    private void initUserData() {
        if (accessToken.equals("FAIL") || userId == -1L) {
            Toast.makeText(getContext(), "로그인 정보를 가져오는대 실패했어요. 다시 로그인해 주세요", Toast.LENGTH_SHORT);
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}