package com.example.allclear;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allclear.databinding.FragmentGradeBinding;
import com.example.allclear.databinding.FragmentTimeTableBinding;
import com.example.allclear.maketimetable.SelectSemesterActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeTableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeTableFragment extends Fragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTimeTableBinding.inflate(inflater, container, false);
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
        super.onDestroyView();
        binding = null;
    }
}