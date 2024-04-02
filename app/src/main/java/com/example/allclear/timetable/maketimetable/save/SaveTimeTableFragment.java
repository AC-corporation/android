package com.example.allclear.timetable.maketimetable.save;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.allclear.databinding.FragmentSaveTimeTableBinding;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class SaveTimeTableFragment extends Fragment {
    private static final String ARG_TIMETABLE_ID = "timetable_id";
    private long timetableId;
    private FragmentSaveTimeTableBinding binding; // 바인딩 객체 선언

    private String[] tempStringDay;
    private ArrayList<ScheduleEntity> tempScheduleEntityList;
    private boolean isDataReady = false; // 데이터가 준비되었는지 확인하는 플래그

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSaveTimeTableBinding.inflate(inflater, container, false); // 바인딩 객체 초기화

        // 데이터가 준비되었으면 UI 업데이트
        if (isDataReady) {
            updateFragmentData(tempStringDay, tempScheduleEntityList);
        }

        // getArguments()에서 데이터 받아오는 코드 추가
        if (getArguments() != null) {
            timetableId = getArguments().getLong(ARG_TIMETABLE_ID);
        }

        // View 초기화 및 설정
        return binding.getRoot(); // 루트 뷰 반환
    }

    public static SaveTimeTableFragment newInstance(long timetableId) {
        SaveTimeTableFragment fragment = new SaveTimeTableFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TIMETABLE_ID, timetableId);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateFragmentData(String[] stringDay, ArrayList<ScheduleEntity> scheduleEntityList) {
        // onCreateView가 호출되기 전이면 임시 변수에 데이터 저장
        if (binding == null) {
            tempStringDay = stringDay;
            tempScheduleEntityList = scheduleEntityList;
            isDataReady = true;
            return;
        }

        // UI 업데이트 작업 수행
        if (stringDay != null) {
            binding.table.initTable(stringDay);
        }
        if (scheduleEntityList != null) {
            binding.table.updateSchedules(scheduleEntityList);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
