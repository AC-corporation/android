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
import com.example.allclear.databinding.ActivitySelectEssentialGeneralElectiveBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.schedule.AdapterSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectEssentialGeneralElectiveActivity extends AppCompatActivity {

    private ActivitySelectEssentialGeneralElectiveBinding binding;

    private Spinner spinner;
    private AdapterSpinner adapterSpinner;
    private SpinnerCustomBinding spinnerCustomBinding;
    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectEssentialGeneralElectiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserData();
        initNextClickListener();
        initBackClickListener();
        getEssentialGeneralList();
        setYearSpinner();

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
                postStepFourToServer(userId);
            }
        });
    }

    private void postStepFourToServer(long userId) {
        TimeTablePostRequestDto timeTablePostRequestDto = userSelectedId();

        ServicePool.timeTableService.postStepThreeToSeven("Bearer " + accessToken, userId, timeTablePostRequestDto)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableResponseDto> call, Response<TimeTableResponseDto> response) {
                        Intent intent = new Intent(SelectEssentialGeneralElectiveActivity.this, SelectMajorActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(SelectEssentialGeneralElectiveActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private TimeTablePostRequestDto userSelectedId() {
        List<Long> selectIds = MakeTimeTableAdapter.getSelectedSubjectIds();
        TimeTablePostRequestDto timeTablePostRequestDto = new TimeTablePostRequestDto();
        timeTablePostRequestDto.setSubjectIdList(selectIds);
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

    private void getEssentialGeneralList() {
        ServicePool.timeTableService.getStepFour("Bearer " + accessToken, userId)
                .enqueue(new Callback<TimeTableGetResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableGetResponseDto> call, @NonNull Response<TimeTableGetResponseDto> response) {

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
                        Toast.makeText(SelectEssentialGeneralElectiveActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initAdapter(List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        MakeTimeTableAdapter adapter = new MakeTimeTableAdapter(subjectResponseDtoList);
        binding.rvSelectGeneralBase.setAdapter(adapter);
    }

    private void setRequirementComponent(List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponents) {
        if (requirementComponents.size() == 1) {
            TimeTableGetResponseDto.RequirementComponentResponseDto componentOne = requirementComponents.get(0);
            String textOne = String.format(getString(R.string.criteria), componentOne.getRequirementComplete(), componentOne.getRequirementCriteria());
            binding.tvComponentResultOne.setText(textOne);
            binding.tvComponentOne.setText(componentOne.getRequirementArgument());
        }
    }


    private void setYearSpinner() {
        // 선택한 spinner 학년으로 리사이클러뷰 필터링 하는 로직 필요
        spinner = binding.yearSpinner;

        ArrayList<String> years = new ArrayList<>();

        years.add(getString(R.string.first_year));
        years.add(getString(R.string.second_year));
        years.add(getString(R.string.third_year));
        years.add(getString(R.string.fourth_year));

        adapterSpinner = new AdapterSpinner(this, years);
        spinner.setAdapter(adapterSpinner);
        spinnerCustomBinding = SpinnerCustomBinding.inflate(getLayoutInflater());

        downArrowClickListener();
    }

    private void downArrowClickListener() {
        ImageButton downArrow = spinnerCustomBinding.ibDownArrow1;
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
    }

}