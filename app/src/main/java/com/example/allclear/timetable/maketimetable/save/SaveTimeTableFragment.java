package com.example.allclear.timetable.maketimetable.save;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allclear.databinding.FragmentSaveTimeTableBinding;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;

public class SaveTimeTableFragment extends Fragment {

    private static final String ARG_STRING_DAY = "stringDay";
    private static final String ARG_SCHEDULE_ENTITY_LIST = "scheduleEntityList";
    private FragmentSaveTimeTableBinding binding;
    private String[] stringDay;
    private static ArrayList<ScheduleEntity> scheduleEntityList;

    public SaveTimeTableFragment() {

    }

    public static SaveTimeTableFragment newInstance(String[] stringDay, ArrayList<ScheduleEntity> scheduleEntityList) {
        SaveTimeTableFragment fragment = new SaveTimeTableFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_STRING_DAY, stringDay);
        args.putSerializable(ARG_SCHEDULE_ENTITY_LIST, SaveTimeTableFragment.scheduleEntityList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stringDay = getArguments().getStringArray(ARG_STRING_DAY);
            scheduleEntityList = (ArrayList<ScheduleEntity>) getArguments().getSerializable(ARG_SCHEDULE_ENTITY_LIST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSaveTimeTableBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (stringDay != null && scheduleEntityList != null) {
            binding.table.initTable(stringDay);
            binding.table.updateSchedules(scheduleEntityList);
        }

        return view;
    }

    public void setData(String[] stringDay, ArrayList<ScheduleEntity> scheduleEntityList) {
        this.stringDay = stringDay;
        this.scheduleEntityList = scheduleEntityList;

        // 데이터가 설정되면 프래그먼트의 UI를 업데이트합니다.
        if (binding != null && stringDay != null && scheduleEntityList != null) {
            binding.table.initTable(stringDay);
            binding.table.updateSchedules(scheduleEntityList);
        }
    }
}
