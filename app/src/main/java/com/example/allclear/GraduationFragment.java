package com.example.allclear;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allclear.databinding.FragmentGradeBinding;
import com.example.allclear.databinding.FragmentGraduationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraduationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraduationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GraduationFragment() {
        // Required empty public constructor
    }
    public static GraduationFragment newInstance(String param1, String param2) {
        GraduationFragment fragment = new GraduationFragment();
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
    private FragmentGraduationBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraduationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}