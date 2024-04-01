package com.example.allclear.timetable;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.allclear.R;
import com.example.allclear.databinding.FragmentScheduleBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ScheduleBottomSheetFragment extends BottomSheetDialogFragment {
    private FragmentScheduleBottomSheetBinding binding;
    private String schedulename;
    private String professor;
    private String place;
    private String time;

    public ScheduleBottomSheetFragment(String schedulename,String professor,String place,String time) {
        this.schedulename=schedulename;
        this.professor=professor;
        this.place=place;
        this.time=time;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentScheduleBottomSheetBinding.inflate(inflater,container,false);
        TextView tv_schedulename = binding.tvSchedulename;
        TextView tv_professor = binding.tvProfessor;
        TextView tv_place = binding.tvPlace;
        TextView tv_time = binding.tvTime;

        tv_schedulename.setText(schedulename);
        tv_professor.setText(professor);
        tv_place.setText(place);
        tv_time.setText(time);
        return binding.getRoot();
    }
}