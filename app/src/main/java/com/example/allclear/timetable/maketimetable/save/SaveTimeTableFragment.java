package com.example.allclear.timetable.maketimetable.save;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allclear.R;

public class SaveTimeTableFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    public static SaveTimeTableFragment newInstance(int position) {
        SaveTimeTableFragment fragment = new SaveTimeTableFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_save_time_table, container, false);
    }
}
