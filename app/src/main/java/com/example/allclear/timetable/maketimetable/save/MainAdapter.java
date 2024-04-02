package com.example.allclear.timetable.maketimetable.save;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends FragmentStateAdapter {
    private List<Long> num_page;
    private String[] stringDay;
    private ArrayList<ScheduleEntity> scheduleEntityList;

    public MainAdapter(@NonNull FragmentActivity fragmentActivity, List<Long> num_page) {
        super(fragmentActivity);
        this.num_page = num_page;
    }

    // stringDay를 설정하는 메서드
    public void setStringDay(String[] stringDay) {
        this.stringDay = stringDay;
    }

    // scheduleEntityList를 설정하는 메서드
    public void setScheduleEntityList(ArrayList<ScheduleEntity> scheduleEntityList) {
        this.scheduleEntityList = scheduleEntityList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 해당 position에 맞는 프래그먼트를 생성하여 반환
        SaveTimeTableFragment fragment = SaveTimeTableFragment.newInstance(num_page.get(position));
        fragment.updateFragmentData(stringDay, scheduleEntityList);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return num_page.size();
    }

    // 프래그먼트에 데이터 전달을 위한 메서드
    public void setFragmentData(int position, String[] stringDay, ArrayList<ScheduleEntity> scheduleEntityList) {
        this.stringDay = stringDay;
        this.scheduleEntityList = scheduleEntityList;
        notifyItemChanged(position);
    }
}
