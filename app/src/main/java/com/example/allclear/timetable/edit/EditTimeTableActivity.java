package com.example.allclear.timetable.edit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.request.TimeTableUpdateRequestDto;
import com.example.allclear.data.request.TokenRefreshRequestDto;
import com.example.allclear.data.response.TokenRefreshResponseDto;
import com.example.allclear.databinding.ActivityEditTimeTableBinding;
import com.example.allclear.databinding.ScheduleDeleteDialogBinding;
import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.MainPageActivity;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.timetable.edit.EditTimeTableTwoActivity;
import com.islandparadise14.mintable.model.ScheduleEntity;
import com.islandparadise14.mintable.tableinterface.OnScheduleClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTimeTableActivity extends AppCompatActivity {
    private ActivityEditTimeTableBinding binding;

    private String[] day;

    private ArrayList<Schedule> scheduleDataList=new ArrayList<Schedule>();
    public ArrayList<ScheduleEntity> scheduleEntityList= new ArrayList<>();
    //시간표에 맞게 할당해야 함
    long timetableId=4;
    static final String DB = "allClear";

    private PreferenceUtil preferenceUtil;
    private Long userId;
    private String accessToken;
    private String refreshToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTimeTableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        preferenceUtil = MyApplication.getPreferences();

        accessToken = preferenceUtil.getAccessToken(null);

        //스케줄데이터를 전달받아 타임테이블에 보여지는 요소로 전환
        Intent intent=getIntent();
        if(intent != null && intent.hasExtra("schedulelist")){
            scheduleDataList=(ArrayList<Schedule>)intent.getSerializableExtra("schedulelist");
            scheduleEntityList= ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
            day=new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
            int size=scheduleDataList.size();
            if(size!=0){
                for(int i=0;i<size;i++){
                    if(5==scheduleDataList.get(i).getClassDay()){
                        day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};}
                    if(6==scheduleDataList.get(i).getClassDay()){
                        day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat","Sun"};}
                }
            }
        }
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //EditTimeTableTwoActivity로 ScheduleList전달
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableActivity.this, EditTimeTableTwoActivity.class);
                intent.putExtra("schedulelist",scheduleDataList);
                startActivityForResult(intent,1);
            }
        });
        //TimeTableFragment로 ScheduleList전달
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeTableUpdateToServer(accessToken,timetableId,scheduleDataList);
            }
        });
        binding.table.setOnScheduleClickListener(new OnScheduleClickListener() {
            @Override
            public void scheduleClicked(ScheduleEntity entity) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTimeTableActivity.this);
                LayoutInflater inflater = LayoutInflater.from(EditTimeTableActivity.this);
                ScheduleDeleteDialogBinding dialogBinding=ScheduleDeleteDialogBinding.inflate(getLayoutInflater());
                View dialogView = dialogBinding.getRoot();
                Button cancelButton = dialogBinding.dialogCancelBtn;
                Button okayButton = dialogBinding.dialogOkayBtn;
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                okayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name=entity.getScheduleName();
                        int size=scheduleDataList.size();
                        for(int i=0;i<size;i++){
                            if(name==scheduleDataList.get(i).getSubjectName())
                                scheduleDataList.remove(i);
                            }
                        scheduleEntityList= ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
                        //토요일,일요일 유무에 따라 day 변경
                        day=new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
                        size=scheduleDataList.size();
                        if(size!=0){
                            for(int i=0;i<size;i++){
                                if(5==scheduleDataList.get(i).getClassDay()){
                                    day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};}
                                if(6==scheduleDataList.get(i).getClassDay()){
                                    day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat","Sun"};}
                            }
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    private void TimeTableUpdateToServer(String accessToken,long timetableId, ArrayList<Schedule> scheduleDataList) {
        TimeTableUpdateRequestDto TimeTableUpdateRequestDto = makeTimeTableUpdateRequestDto(scheduleDataList);

        ServicePool.timeTableUpdateRequestService.TimeTableUpdate("Bearer " + accessToken,timetableId,TimeTableUpdateRequestDto)
                .enqueue(new Callback<TimeTableUpdateRequestDto>() {
                    @Override
                    public void onResponse(Call<TimeTableUpdateRequestDto> call, Response<TimeTableUpdateRequestDto> response) {
                        if(response.isSuccessful()){
                            System.out.println("서버 통신 성공");
                            Intent intent=new Intent(EditTimeTableActivity.this, MainPageActivity.class);
                            intent.putExtra("schedulelist",scheduleDataList);
                            startActivity(intent);
                        }else{
                            if(response.code() == 403){
                                tokenRefresh();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TimeTableUpdateRequestDto> call, Throwable t) {
                        Toast.makeText(EditTimeTableActivity.this, R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private  TimeTableUpdateRequestDto makeTimeTableUpdateRequestDto(ArrayList<Schedule> scheduleDataList) {
        TimeTableUpdateRequestDto TimeTableUpdateRequestDto=new TimeTableUpdateRequestDto();
        TimeTableUpdateRequestDto.setTableName(binding.tvSubTitle.getText().toString());
        List<TimeTableUpdateRequestDto.TimetableSubjectRequestDto> timetableSubjectRequestDtoList=new ArrayList<>();


        for (Schedule schedule : scheduleDataList) {
            TimeTableUpdateRequestDto.TimetableSubjectRequestDto timetablesubject=new TimeTableUpdateRequestDto.TimetableSubjectRequestDto();
            timetablesubject.setSubjectName(schedule.getSubjectName());
            timetablesubject.setSubjectId(schedule.getSubjectId());
            timetableSubjectRequestDtoList.add(timetablesubject);

            List<TimeTableUpdateRequestDto.ClassInfoRequestDto> classInfoRequestDtoList=new ArrayList<>();
            TimeTableUpdateRequestDto.ClassInfoRequestDto classinfo=new TimeTableUpdateRequestDto.ClassInfoRequestDto();
            classinfo.setProfessor(schedule.getProfessor());
            classinfo.setClassDay(getClassDay(schedule.getClassDay()));
            classinfo.setStartTime(schedule.getStartTime());
            classinfo.setEndTime(schedule.getEndTime());
            classinfo.setClassRoom(schedule.getClassRoom());
            classInfoRequestDtoList.add(classinfo);
            timetablesubject.setClassInfoRequestDtoList(classInfoRequestDtoList);
        }
        TimeTableUpdateRequestDto.setTimetableSubjectRequestDtoList(timetableSubjectRequestDtoList);

        return TimeTableUpdateRequestDto;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        binding.table.initTable(day);
        binding.table.updateSchedules(scheduleEntityList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //EditTimeTableTwoActivity에서 액티비티가 전환되었을 때 ScheduleList 갱신
        if (requestCode == 1 && resultCode == RESULT_OK){
            scheduleDataList=(ArrayList<Schedule>)data.getSerializableExtra("schedulelist");
            scheduleEntityList= ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
            day=new String[]{"Mon", "Tue", "Wen", "Thu", "Fri"};
            int size=scheduleDataList.size();
            if(size!=0){
                for(int i=0;i<size;i++){
                    if(5==scheduleDataList.get(i).getClassDay()){
                        day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat"};}
                    if(6==scheduleDataList.get(i).getClassDay()){
                        day= new String[]{"Mon", "Tue", "Wen", "Thu", "Fri", "Sat","Sun"};}
                }
            }
        }
        //EditTimeTableTwoActivity에 갱신된 ScheduleList 전달
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditTimeTableActivity.this, EditTimeTableTwoActivity.class);
                intent.putExtra("schedulelist",scheduleDataList);
                startActivityForResult(intent,1);
            }
        });

    }
    private String getClassDay(int day) {
        if (day == 0)
            return getString(R.string.monday);
        else if (day == 1)
            return getString(R.string.tuesday);
        else if (day == 2)
            return getString(R.string.wednesday);
        else if (day == 3)
            return getString(R.string.thursday);
        else if (day == 4)
            return getString(R.string.friday);
        else
            return getString(R.string.saturday);
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
    private void initDefaultData(){
        preferenceUtil = MyApplication.getPreferences();
        accessToken = preferenceUtil.getAccessToken("FAIL");
        refreshToken = preferenceUtil.getRefreshToken("FAIL");
        userId = preferenceUtil.getUserId(-1L);
    }

}