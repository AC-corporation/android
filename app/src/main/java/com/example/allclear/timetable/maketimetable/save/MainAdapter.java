package com.example.allclear.timetable.maketimetable.save;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainAdapter extends FragmentStateAdapter {

    public int mCount;

    public MainAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        // 수정 필요
        if (index == 0) return new SaveTimeTableFragment();
        else if (index == 1) return new SaveTimeTableFragment();
        else if (index == 2) return new SaveTimeTableFragment();
        else return new SaveTimeTableFragment();

    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) {
        return position % mCount;
    }

}