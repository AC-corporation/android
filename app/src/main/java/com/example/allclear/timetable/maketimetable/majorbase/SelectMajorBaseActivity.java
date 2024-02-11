package com.example.allclear.timetable.maketimetable.majorbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.allclear.R;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.response.TimeTableThreeResponseDto;
import com.example.allclear.databinding.ActivitySelectMajorBaseBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.schedule.AdapterSpinner;
import com.example.allclear.timetable.maketimetable.SelectEssentialGeneralElectiveActivity;
import com.example.allclear.timetable.maketimetable.SelectSemesterActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectMajorBaseActivity extends AppCompatActivity {
    private ActivitySelectMajorBaseBinding binding;
    private Spinner spinner;
    private AdapterSpinner adapterSpinner;
    private SpinnerCustomBinding spinnerCustomBinding;

    private long userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectMajorBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNextClickListener();
        initBackClickListener();
        getMajorBaseList();
        setYearSpinner();

    }

    private void initNextClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectMajorBaseActivity.this, SelectEssentialGeneralElectiveActivity.class);
                startActivity(intent);
            }
        });
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
        ServicePool.timeTableService.getStepThree(userId)
                .enqueue(new Callback<TimeTableThreeResponseDto>() {
                    @Override
                    public void onResponse(Call<TimeTableThreeResponseDto> call, Response<TimeTableThreeResponseDto> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            TimeTableThreeResponseDto responseBody = response.body();
                            TimeTableThreeResponseDto.TimeTableThreeResponseData data = responseBody.getData();

                            List<TimeTableThreeResponseDto.RequirementComponentResponseDto> requirementComponents = data.getRequirementComponentResponseDtoList();
                            List<TimeTableThreeResponseDto.SubjectResponseDto> subjectResponseDtoList = data.getSubjectResponseDtoList();

                            initAdapter(subjectResponseDtoList);
                            setRequirementComponent(requirementComponents);
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableThreeResponseDto> call, Throwable t) {
                        Toast.makeText(SelectMajorBaseActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initAdapter(List<TimeTableThreeResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        SelectMajorBaseAdapter adapter = new SelectMajorBaseAdapter(subjectResponseDtoList);
        binding.rvSelectMajorBase.setAdapter(adapter);
    }

    private void setRequirementComponent(List<TimeTableThreeResponseDto.RequirementComponentResponseDto> requirementComponents) {
        // List의 개수에 따라서 넣어주는 값이 달라집니다.
        if (requirementComponents.size() > 0) {
            TimeTableThreeResponseDto.RequirementComponentResponseDto componentOne = requirementComponents.get(0);
            String textOne = String.format(getString(R.string.criteria), componentOne.getRequirementComplete(), componentOne.getRequirementCriteria());
            binding.tvComponentResultOne.setText(textOne);
            binding.tvComponentOne.setText(componentOne.getRequirementArgument());
        }

        if (requirementComponents.size() > 1) {
            TimeTableThreeResponseDto.RequirementComponentResponseDto componentTwo = requirementComponents.get(1);
            String textTwo = String.format(getString(R.string.criteria), componentTwo.getRequirementComplete(), componentTwo.getRequirementCriteria());
            binding.tvComponentResultTwo.setText(textTwo);
            binding.tvComponentTwo.setText(componentTwo.getRequirementArgument());
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