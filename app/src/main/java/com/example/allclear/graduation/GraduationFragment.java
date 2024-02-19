package com.example.allclear.graduation;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allclear.databinding.FragmentGraduationBinding;

public class GraduationFragment extends Fragment {
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    static final String USER_ID = "User_Id";
    static final String DB = "allClear";


    public GraduationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private FragmentGraduationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraduationBinding.inflate(inflater, container, false);
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