package com.example.allclear.timetable.maketimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTablePostRequestDto;
import com.example.allclear.data.response.TimeTableGetResponseDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.databinding.ActivitySelectMajorBaseBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.schedule.AdapterSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMajorBaseActivity extends AppCompatActivity {
    private ActivitySelectMajorBaseBinding binding;
    private Spinner spinner;
    private AdapterSpinner adapterSpinner;
    private SpinnerCustomBinding spinnerCustomBinding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectMajorBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserData();
        initNextClickListener();
        initBackClickListener();
        getMajorBaseList();

    }

    private void getUserData() {
        preferenceUtil = MyApplication.getPreferences();
        userId = preferenceUtil.getUserId(-1L);
        accessToken = preferenceUtil.getAccessToken("FAIL");
    }

    private void initNextClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStepThreeToServer(userId);
            }
        });
    }

    private void postStepThreeToServer(long userId) {
        // 선택한 과목 서버로 보내기
        TimeTablePostRequestDto timeTablePostRequestDto = userSelectedId();

        ServicePool.timeTableService.postStepThreeToSeven("Bearer " + accessToken, userId, timeTablePostRequestDto)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableResponseDto> call, @NonNull Response<TimeTableResponseDto> response) {

                        if (response.isSuccessful()) {
                            Intent intent = new Intent(SelectMajorBaseActivity.this, SelectEssentialGeneralElectiveActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(SelectMajorBaseActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private TimeTablePostRequestDto userSelectedId() {
        List<Long> selectedIds = MakeTimeTableAdapter.getSelectedSubjectIds();
        TimeTablePostRequestDto timeTablePostRequestDto = new TimeTablePostRequestDto();
        timeTablePostRequestDto.setSubjectIdList(selectedIds);
        return timeTablePostRequestDto;
    }


    private void initBackClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getMajorBaseList() {
        ServicePool.timeTableService.getStepThree("Bearer " + accessToken, userId)
                .enqueue(new Callback<TimeTableGetResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableGetResponseDto> call, @NonNull Response<TimeTableGetResponseDto> response) {

                        // 서버로부터 리스트 받아서 연결
                        if (response.isSuccessful() && response.body() != null) {
                            TimeTableGetResponseDto responseBody = response.body();
                            TimeTableGetResponseDto.TimeTableResponseData data = responseBody.getData();

                            List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponents = data.getRequirementComponentResponseDtoList();
                            List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList = data.getSubjectResponseDtoList();

                            initAdapter(subjectResponseDtoList);
                            setRequirementComponent(requirementComponents);
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableGetResponseDto> call, Throwable t) {
                        Toast.makeText(SelectMajorBaseActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initAdapter(List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        MakeTimeTableAdapter adapter = new MakeTimeTableAdapter(subjectResponseDtoList);
        binding.rvSelectMajorBase.setAdapter(adapter);
    }

    private void setRequirementComponent(List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponents) {
        TimeTableGetResponseDto.RequirementComponentResponseDto componentOne = requirementComponents.get(0);
        String textOne = String.format(getString(R.string.criteria), componentOne.getRequirementComplete(), componentOne.getRequirementCriteria());
        binding.tvComponentResultOne.setText(textOne);
        binding.tvComponentOne.setText(componentOne.getRequirementArgument());

        if (requirementComponents.size() > 1) {
            TimeTableGetResponseDto.RequirementComponentResponseDto componentTwo = requirementComponents.get(1);
            String textTwo = String.format(getString(R.string.criteria), componentTwo.getRequirementComplete(), componentTwo.getRequirementCriteria());
            binding.tvComponentResultTwo.setText(textTwo);
            binding.tvComponentTwo.setText(componentTwo.getRequirementArgument());
        }
    }

}