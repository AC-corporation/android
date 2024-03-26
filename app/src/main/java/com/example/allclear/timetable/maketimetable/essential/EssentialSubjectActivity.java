package com.example.allclear.timetable.maketimetable.essential;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableEssentialRequestDto;
import com.example.allclear.data.response.TimeTableEssentialResponseDto;
import com.example.allclear.data.response.TimeTableResponseDto;
import com.example.allclear.databinding.ActivityEssentialSubjectBinding;
import com.example.allclear.databinding.DialogAddConditionBinding;
import com.example.allclear.timetable.maketimetable.SaveTimeTableActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EssentialSubjectActivity extends AppCompatActivity {
    private ActivityEssentialSubjectBinding binding;

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;

    private int maxBaseCredit = 0;
    private int maxMajorCredit = 0;

    private boolean checkMaxBaseCredit;
    private boolean isMaxBaseValid;
    private boolean checkMaxMajorCredit;
    private boolean isMaxMajorValid;

    String selectedYear;
    String selectedSemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityEssentialSubjectBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getUserData();
        getSemesterData();
        initNextClickListener();
        initBackClickListener();
        getEssentialSubject();
        initDialogListener();
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
        }
    }


    private void initNextClickListener() {
        binding.btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStepSevenToServer(userId);
            }
        });
    }

    private void postStepSevenToServer(long userId) {
        TimeTableEssentialRequestDto timeTableEssentialRequestDto = userIdSelected();

        timeTableEssentialRequestDto.setMinCredit(0);
        timeTableEssentialRequestDto.setMinMajorCredit(0);

        timeTableEssentialRequestDto.setMaxCredit(maxBaseCredit);
        timeTableEssentialRequestDto.setMaxMajorCredit(maxMajorCredit);

        if (timeTableEssentialRequestDto.timetableGeneratorSubjectIdList.size() == 0) {
            Toast.makeText(this, "한 과목 이상 선택해주세요.", Toast.LENGTH_SHORT).show();
        } else if (maxBaseCredit == 0 && maxMajorCredit == 0) {
            Toast.makeText(this, "최대 학점을 설정해주세요", Toast.LENGTH_SHORT).show();
        } else {
            ServicePool.timeTableService.postStepSeven("Bearer " + accessToken, userId, timeTableEssentialRequestDto)
                    .enqueue(new Callback<TimeTableResponseDto>() {
                        @Override
                        public void onResponse(@NonNull Call<TimeTableResponseDto> call, @NonNull Response<TimeTableResponseDto> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(EssentialSubjectActivity.this, SaveTimeTableActivity.class);
                                intent.putExtra("selectedYear", selectedYear);
                                intent.putExtra("selectedSemester", selectedSemester);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<TimeTableResponseDto> call, @NonNull Throwable t) {
                            Toast.makeText(EssentialSubjectActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private TimeTableEssentialRequestDto userIdSelected() {
        List<Long> selectedIds = EssentialSubjectAdapter.getSelectedSubjectIds();
        TimeTableEssentialRequestDto timeTableEssentialRequestDto = new TimeTableEssentialRequestDto();
        timeTableEssentialRequestDto.setTimetableGeneratorSubjectIdList(selectedIds);
        return timeTableEssentialRequestDto;
    }

    private void initBackClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getEssentialSubject() {
        ServicePool.timeTableService.getStepSeven("Bearer " + accessToken, userId)
                .enqueue(new Callback<TimeTableEssentialResponseDto>() {
                    @Override
                    public void onResponse(@NonNull Call<TimeTableEssentialResponseDto> call, @NonNull Response<TimeTableEssentialResponseDto> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            TimeTableEssentialResponseDto responseBody = response.body();
                            TimeTableEssentialResponseDto.TimeTableResponseData data = responseBody.getData();

                            List<TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList> subjectResponseDtoList = data.getTimetableGeneratorSubjectResponseDtoList();

                            initAdapter(subjectResponseDtoList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TimeTableEssentialResponseDto> call, @NonNull Throwable t) {
                        Toast.makeText(EssentialSubjectActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initAdapter(List<TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList> subjectResponseDtoList) {
        EssentialSubjectAdapter adapter = new EssentialSubjectAdapter(subjectResponseDtoList);
        binding.rvEssentialSubject.setAdapter(adapter);
    }

    private void initDialogListener() {
        binding.btnAddCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreditDialog(v);
            }
        });
    }

    private void showCreditDialog(View v) {
        DialogAddConditionBinding dialogBinding = DialogAddConditionBinding.inflate(LayoutInflater.from(v.getContext()));
        View dialogView = dialogBinding.getRoot();

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        dialogBinding.btnSaveCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    maxBaseCredit = Integer.parseInt(dialogBinding.etBaseCredit.getText().toString());
                    checkMaxBaseCredit = true;

                    maxMajorCredit = Integer.parseInt(dialogBinding.etMajorCredit.getText().toString());
                    checkMaxMajorCredit = true;

                    if (maxBaseCredit > 22 || maxMajorCredit > 22) {
                        Toast.makeText(EssentialSubjectActivity.this, R.string.essential_max_error, Toast.LENGTH_SHORT).show();
                        isMaxBaseValid = maxBaseCredit <= 22;
                        isMaxMajorValid = false;
                    } else {
                        isMaxBaseValid = true;
                        isMaxMajorValid = true;
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(EssentialSubjectActivity.this, R.string.essential_input_error, Toast.LENGTH_SHORT).show();
                    checkMaxBaseCredit = false;
                    checkMaxMajorCredit = false;
                    e.printStackTrace();
                }

                if (checkMaxBaseCredit && checkMaxMajorCredit && isMaxBaseValid && isMaxMajorValid) {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }


}