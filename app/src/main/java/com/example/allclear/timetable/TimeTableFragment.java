package com.example.allclear.timetable;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.allclear.schedule.ChangeSchedule;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.databinding.FragmentTimeTableBinding;
import com.example.allclear.timetable.edit.EditTimeTableActivity;
import com.example.allclear.timetable.maketimetable.SelectSemesterActivity;
import com.islandparadise14.mintable.model.ScheduleEntity;
import com.islandparadise14.mintable.tableinterface.OnScheduleClickListener;

import java.util.ArrayList;

public class TimeTableFragment extends Fragment{
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    static final String USER_ID = "User_Id";
    static final String DB = "allClear";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public TimeTableFragment() {
        // Required empty public constructor
    }


    public static TimeTableFragment newInstance(String param1, String param2) {
        TimeTableFragment fragment = new TimeTableFragment();
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

    private FragmentTimeTableBinding binding;
    private String[] day = {"Mon", "Tue", "Wen", "Thu", "Fri"};
    private ArrayList<Schedule> scheduleDataList=new ArrayList<Schedule>();
    private ArrayList<ScheduleEntity> scheduleEntityList = new ArrayList<>();
    public void setTimeTable(){
        binding.timetable.initTable(day);
        binding.timetable.updateSchedules(scheduleEntityList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //스케줄데이터를 전달받아 타임테이블에 보여지는 요소로 전환
        Intent intent=getActivity().getIntent();
        if(intent != null && intent.hasExtra("schedulelist")){
            scheduleDataList=(ArrayList<Schedule>)intent.getSerializableExtra("schedulelist");
            scheduleEntityList= ChangeSchedule.getInstance().Change_scheduleEntity(scheduleDataList);
            //토요일,일요일 유무에 따라 day 변경
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
        binding = FragmentTimeTableBinding.inflate(inflater, container, false);
        binding.timetable.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                v.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
                    @Override
                    public void onWindowFocusChanged(boolean hasFocus) {
                        if (hasFocus) {
                            setTimeTable();
                            // Window has gained focus
                            // Perform your actions here when window gains focus
                        } else {
                            // Window has lost focus
                            // Perform your actions here when window loses focus
                        }
                    }
                });
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                v.getViewTreeObserver().removeOnWindowFocusChangeListener(null);
            }
        });
        btnClick();
        return binding.getRoot();
    }

    private void btnClick() {
        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectSemesterActivity.class);
                startActivity(intent);
            }
        });
        binding.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditTimeTableActivity.class);
                intent.putExtra("schedulelist",scheduleDataList);
                startActivity(intent);
            }
        });
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListTimeTableActivity.class);
                startActivity(intent);
            }
        });
        binding.timetable.setOnScheduleClickListener(new OnScheduleClickListener() {
            @Override
            public void scheduleClicked(@NonNull ScheduleEntity scheduleEntity) {
                String schedulename=scheduleEntity.getScheduleName();
                String professor=null;
                int size=scheduleDataList.size();
                for(int i=0;i<size;i++){
                    if(schedulename==scheduleDataList.get(i).getSubjectName())
                        professor=scheduleDataList.get(i).getProfessor();
                }
                String place=scheduleEntity.getRoomInfo();
                ScheduleBottomSheetFragment bottomSheet = new ScheduleBottomSheetFragment(schedulename,professor,place);
                bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());

            }
        });
    }

    @Override
    public void onDestroyView() {
        Log.i("Fragment","onDestroyView()");
        binding = null;
        super.onDestroyView();
    }
}