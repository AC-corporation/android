package com.example.allclear.timetable.maketimetable.save;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // 페이지에 해당하는 프래그먼트를 반환
        return SaveTimeTableFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        // 페이지의 총 수를 반환
        return 5;
    }
}
