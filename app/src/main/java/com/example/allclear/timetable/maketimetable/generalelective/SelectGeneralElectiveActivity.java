package com.example.allclear.timetable.maketimetable.generalelective;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.allclear.R;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTablePostRequestDto;
import com.example.allclear.data.response.TimeTableGetResponseDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.databinding.ActivitySelectGeneralElectiveBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.schedule.AdapterSpinner;
import com.example.allclear.timetable.maketimetable.EssentialSubjectActivity;
import com.example.allclear.timetable.maketimetable.MakeTimeTableAdapter;
import com.example.allclear.timetable.maketimetable.SelectMajorBaseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectGeneralElectiveActivity extends AppCompatActivity {
    private ActivitySelectGeneralElectiveBinding binding;

    private Spinner spinner;
    private AdapterSpinner adapterSpinner;
    private SpinnerCustomBinding spinnerCustomBinding;

    long userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelectGeneralElectiveBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        initNextClickListener();
        initBackClickListener();
        getGeneralElectiveList();
        setGeneralSpinner();

    }

    private void initNextClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStepSixToServer(userId);
            }
        });
    }

    private void postStepSixToServer(long userId) {
        TimeTablePostRequestDto timeTablePostRequestDto = userSelectedId();

        ServicePool.timeTableService.postStepThreeToSeven(userId, timeTablePostRequestDto)
                .enqueue(new Callback<TimeTableResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableResponseDto> call, Response<TimeTableResponseDto> response) {
                        Intent intent = new Intent(SelectGeneralElectiveActivity.this, EssentialSubjectActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<TimeTableResponseDto> call, Throwable t) {
                        Toast.makeText(SelectGeneralElectiveActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
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

    private void getGeneralElectiveList() {
        ServicePool.timeTableService.getStepSix(userId)
                .enqueue(new Callback<TimeTableGetResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableGetResponseDto> call, Response<TimeTableGetResponseDto> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            TimeTableGetResponseDto responseBody = response.body();
                            TimeTableGetResponseDto.TimeTableResponseData data = responseBody.getData();

                            List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponents = data.getRequirementComponentResponseDtoList();
                            List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList = data.getSubjectResponseDtoList();

                            initSubjectAdapter(subjectResponseDtoList);
                            initArgumentAdapter(requirementComponents);

                        }

                    }

                    @Override
                    public void onFailure(Call<TimeTableGetResponseDto> call, Throwable t) {
                        Toast.makeText(SelectGeneralElectiveActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initSubjectAdapter(List<TimeTableGetResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        MakeTimeTableAdapter adapter = new MakeTimeTableAdapter(subjectResponseDtoList);
        binding.rvSelectGeneralElective.setAdapter(adapter);
    }

    private void initArgumentAdapter(List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponents) {
        SelectGeneralElectiveAdapter adapter = new SelectGeneralElectiveAdapter(requirementComponents);
        binding.rvSelectGeneralElective.setAdapter(adapter);
    }

    private void setGeneralSpinner() {
        // 교선 spinner는 논의 후 추가 작성하겠습니다.
        // 필터링 로직 필요

        spinner = binding.generalSpinner;

        ArrayList<String> general = new ArrayList<>();

    }

}