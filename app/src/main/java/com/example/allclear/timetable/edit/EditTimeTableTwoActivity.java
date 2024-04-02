package com.example.allclear.timetable.edit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.SubjectSearchsRequestDto;
import com.example.allclear.data.request.TokenRefreshRequestDto;
import com.example.allclear.data.response.SubjectAllDto;
import com.example.allclear.data.response.SubjectDto;
import com.example.allclear.data.response.TokenRefreshResponseDto;
import com.example.allclear.databinding.ActivityEditTimeTableTwoBinding;
import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.schedule.Schedule;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTimeTableTwoActivity extends AppCompatActivity {
    private ActivityEditTimeTableTwoBinding binding;
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String[] day;
    String subtext;
    String professor;
    String place;
    int selectedDay;
    String starttime;
    String endtime;
    ScheduleEntity added_schedule;
    private SubjectSearchsRequestDto subjectSearchsRequestDto;

    private ArrayList<Schedule> scheduleDataList = new ArrayList<Schedule>();

    private ArrayList<ScheduleEntity> scheduleEntityList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTimeTableTwoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDefaultData();
        //스케줄데이터를 전달받아 타임테이블에 보여지는 요소로 전환
        initCalendar();
        initRV();
        clickListener();
    }
    private void initCalendar(){
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("schedulelist")) {
            scheduleDataList = (ArrayList<Schedule>) intent.getSerializableExtra("schedulelist");
            scheduleEntityList = ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
            day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
            int size = scheduleDataList.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    if (5 == scheduleDataList.get(i).getClassDay()) {
                        day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};
                    }
                    if (6 == scheduleDataList.get(i).getClassDay()) {
                        day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun"};
                    }
                }
            }
        }
    }
    private void initDefaultData(){
        preferenceUtil = MyApplication.getPreferences();
        accessToken = preferenceUtil.getAccessToken("FAIL");
        refreshToken = preferenceUtil.getRefreshToken("FAIL");
        userId = preferenceUtil.getUserId(-1L);
    }
    private void clickListener(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //SelfAddTwoEditActivity로 ScheduleList전달
        binding.tvSelfadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableTwoActivity.this, com.example.allclear.timetable.edit.SelfAddTwoEditActivity.class);
                intent.putExtra("schedulelist", scheduleDataList);
                startActivityForResult(intent, 10);
            }
        });
//        binding.spFilterGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),EditTimeTableTwoGroupActivity.class);
//                intent.putExtra("schedulelist",scheduleDataList);
//                startActivityForResult(intent,11);
//            }
//        });
//        binding.spFilterSearch.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        binding.spFilterGrade.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),EditTimeTableTwoGradeActivity.class);
//                startActivityForResult(intent,12);
//            }
//        });
    }
    private void initRV(){
        if (subjectSearchsRequestDto == null){
            ServicePool.subjectService.getSubjectAllServer("Bearer " + accessToken,0)
                    .enqueue(new Callback<SubjectAllDto>() {
                        @Override
                        public void onResponse(Call<SubjectAllDto> call, Response<SubjectAllDto> response) {
                            if(response.isSuccessful()){
                                System.out.println("서버 통신 성공");
                                Log.i("if",response.toString());
                                Log.i("if",response.body().getMessage());
                                String statusCode = response.body().getCode();
                                Log.i(statusCode,statusCode);
                                switch (statusCode) {
                                    case "OK":
//                                        Log.i("currentPage",String.valueOf(response.body().getData().getCurrentPage()));
//                                        Log.i("pageSize",String.valueOf(response.body().getData().getPageSize()));
//                                        Log.i("totalPage",String.valueOf(response.body().getData().getTotalPage()));
//                                        Log.i("totalElement",String.valueOf(response.body().getData().getTotalElement()));
//                                        Log.i("SubjectResponseDtoList",response.body().getData().getSubjectAllResponseDtoList().get(0).getSubjectName());
                                        initData(response.body().getData().getSubjectAllResponseDtoList());
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
                        public void onFailure(Call<SubjectAllDto> call, Throwable t) {

                        }
                    });
        }
    }
    private void initData(List<SubjectAllDto.SubjectAllResponseDto.SubjectInfoResponseDto> data){
        EditTimeTableTwoAdapter editTimeTableTwoAdapter = new EditTimeTableTwoAdapter(data);
        binding.rvSubject.setAdapter(editTimeTableTwoAdapter);
    }
    private int getClassDay(String day) {
        if (day.equals(R.string.monday))
            return 0;
        else if (day.equals(R.string.tuesday))
            return 1;
        else if (day.equals(R.string.wednesday))
            return 2;
        else if (day.equals(R.string.thursday))
            return 3;
        else if (day.equals(R.string.friday))
            return 4;
        else if (day.equals(R.string.saturday))
            return 5;
        else
            return 6;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(day);
        binding.table.updateSchedules(scheduleEntityList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //SelfAddTwoEditActivity에서 액티비티가 전환되었을 때
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            //사용자가 직접추가한 스케줄데이터를 ScheduleList에 추가
            scheduleDataList= (ArrayList<Schedule>) data.getSerializableExtra("addedschedulelist");
            scheduleEntityList=ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
            day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
            int size = scheduleDataList.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    if (5 == scheduleDataList.get(i).getClassDay()) {
                        day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};
                    }
                    if (6 == scheduleDataList.get(i).getClassDay()) {
                        day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun"};
                    }
                }
            }
            initRV();
        }
        //EditTimeTableTwoGroupActivity에서 전환되었을때
        else if (requestCode == 11 && resultCode == RESULT_OK) {
            // 액티비티 전환됐을때 시간표 안터지는거까지 성공 이제 필터 버튼 구현해야함
            subjectSearchsRequestDto = new SubjectSearchsRequestDto();
            if(data.getSerializableExtra("courseClassification")!=null){
                String courseClassification = data.getStringExtra("courseClassification");
                subjectSearchsRequestDto.setElectivesClassification(courseClassification);
            }
            scheduleDataList= (ArrayList<Schedule>) data.getSerializableExtra("addedschedulelist");
            scheduleEntityList=ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
            day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
            int size = scheduleDataList.size();
            if (size != 0) {
                for (int i = 0; i < size; i++) {
                    if (5 == scheduleDataList.get(i).getClassDay()) {
                        day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};
                    }
                    if (6 == scheduleDataList.get(i).getClassDay()) {
                        day = new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat", "Sun"};
                    }
                }
            }
        }
        else if (requestCode == 12 && resultCode == RESULT_OK){
            subjectSearchsRequestDto.setElectivesClassification(data.getSerializableExtra("year").toString());
        }
        //갱신된 ScheduleList를 EditTimeTableActivity로 전달
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTimeTableTwoActivity.this, EditTimeTableActivity.class);
                intent.putExtra("schedulelist", scheduleDataList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
        preferenceUtil.setAccessToken(tokenRefreshDto.getAccessToken());
        preferenceUtil.setRefreshToken(tokenRefreshDto.getRefreshToken());

        // 저장된 토큰 확인
        accessToken = preferenceUtil.getAccessToken(null);
        refreshToken = preferenceUtil.getRefreshToken(null);

        Log.i("Saved access token", accessToken != null ? accessToken : "null");
        Log.i("Saved refresh token", refreshToken != null ? refreshToken : "null");
    }
}