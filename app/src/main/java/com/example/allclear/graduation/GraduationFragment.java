package com.example.allclear.graduation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allclear.data.ServicePool;
import com.example.allclear.data.responese.GraduationDto;
import com.example.allclear.databinding.FragmentGraduationBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraduationFragment extends Fragment {
    private FragmentGraduationBinding binding;
    int userId = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraduationBinding.inflate(inflater, container, false);

        checkServer(userId);

        return binding.getRoot();
    }

    private void checkServer(int userId) {
        ServicePool.graduationService.getListFromServer(userId)
                .enqueue(new Callback<GraduationDto>() {
                    @Override
                    public void onResponse(Call<GraduationDto> call, Response<GraduationDto> response) {
                        Toast.makeText(requireActivity(), "서버 통신 성공", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<GraduationDto> call, Throwable t) {
                        Toast.makeText(requireActivity(), "서버 통신 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}