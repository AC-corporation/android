package com.example.allclear.timetable.maketimetable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.allclear.databinding.ActivitySelectMajorBinding;
import com.example.allclear.timetable.maketimetable.generalelective.SelectGeneralElectiveActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMajorActivity extends AppCompatActivity {

    private ActivitySelectMajorBinding binding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    String selectedYear;
    String selectedSemester;
    String timeTableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectMajorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getUserData();
        getSemesterData();
        initNextClickListener();
        initBackClickListener();
        getMajorList();

    }

    private void getUserData() {
        preferenceUtil = MyApplication.getPreferences();
        userId = preferenceUtil.getUserId(-1L);
        accessToken = preferenceUtil.getAccessToken("FAIL");
    }

    private void getSemesterData() {
        Intent intent = getIntent();
        if (intent != null) {
            selectedYear = intent.getStringExtra("selectedYear");
            selectedSemester = intent.getStringExtra("selectedSemester");
            timeTableName = intent.getStringExtra("timeTableName");
        }
    }

    private void initNextClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStepFiveToServer(userId);
            }
        });
    }

    private void postStepFiveToServer(long userId) {
        // 선택한 과목 서버로 보내기
        TimeTablePostRequestDto timeTablePostRequestDto = userSelectedId();

        ServicePool.timeTableService.postStepThreeToSeven("Bearer " + accessToken, userId, timeTablePostRequestDto)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableResponseDto> call, @NonNull Response<TimeTableResponseDto> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(SelectMajorActivity.this, SelectGeneralElectiveActivity.class);
                            intent.putExtra("selectedYear", selectedYear);
                            intent.putExtra("selectedSemester", selectedSemester);
                            intent.putExtra("timeTableName", timeTableName);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(SelectMajorActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
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

    private void getMajorList() {
        ServicePool.timeTableService.getStepFive("Bearer " + accessToken, userId)
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
                        Toast.makeText(SelectMajorActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initAdapter(List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        MakeTimeTableAdapter adapter = new MakeTimeTableAdapter(subjectResponseDtoList);
        binding.rvSelectMajor.setAdapter(adapter);
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