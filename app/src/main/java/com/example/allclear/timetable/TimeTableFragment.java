package com.example.allclear.timetable;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.allclear.databinding.FragmentTimeTableBinding;
import com.example.allclear.timetable.maketimetable.SelectSemesterActivity;
import com.islandparadise14.mintable.model.ScheduleDay;
import com.islandparadise14.mintable.model.ScheduleEntity;

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
        scheduleList.add(schedule);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private FragmentTimeTableBinding binding;
    private String[] day = {"Mon", "Tue", "Wen", "Thu", "Fri"};
    private ArrayList<ScheduleEntity> scheduleList = new ArrayList<>();
    ScheduleEntity schedule = new ScheduleEntity(
            32,                  // originId
            "Database",          // scheduleName
            "IT Building 301",    // roomInfo
            ScheduleDay.TUESDAY,  // ScheduleDay object (MONDAY ~ SUNDAY)
            "8:20",               // startTime format: "HH:mm"
            "10:30",              // endTime format: "HH:mm"
            "#fffcae68",          // backgroundColor (optional)
            "#000000"             // textColor (optional)
    );
    public void setTimeTable(){
        binding.timetable.initTable(day);
        binding.timetable.updateSchedules(scheduleList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTimeTableBinding.inflate(inflater, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(DB, MODE_PRIVATE);
        Log.i(ACCESS_TOKEN, preferences.getString(ACCESS_TOKEN,""));
        Log.i(REFRESH_TOKEN,preferences.getString(REFRESH_TOKEN,""));
        Log.i(USER_ID, String.valueOf(preferences.getLong(USER_ID,0)));
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
    }

    @Override
    public void onDestroyView() {
        Log.i("Fragment","onDestroyView()");
        binding = null;
        super.onDestroyView();
    }
}