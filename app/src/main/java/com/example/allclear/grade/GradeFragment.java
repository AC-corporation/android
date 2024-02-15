package com.example.allclear.grade;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allclear.databinding.FragmentGradeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GradeFragment extends Fragment {
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    static final String USER_ID = "User_Id";
    static final String DB = "allClear";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public GradeFragment() {
        // Required empty public constructor
    }

    public static GradeFragment newInstance(String param1, String param2) {
        GradeFragment fragment = new GradeFragment();
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

    private FragmentGradeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradeBinding.inflate(inflater, container, false);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(DB, MODE_PRIVATE);
        Log.i(ACCESS_TOKEN, preferences.getString(ACCESS_TOKEN,""));
        Log.i(REFRESH_TOKEN,preferences.getString(REFRESH_TOKEN,""));
        Log.i(USER_ID, String.valueOf(preferences.getLong(USER_ID,0)));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}