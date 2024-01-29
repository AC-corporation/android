package com.example.allclear.grade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allclear.data.ServicePool;
import com.example.allclear.data.responese.GradeDto;
import com.example.allclear.data.responese.GraduationDto;
import com.example.allclear.data.responese.SemesterGradeDto;
import com.example.allclear.databinding.FragmentGradeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeFragment extends Fragment {

    int userId = 1;
    long semesterGradeId = 1;
    private FragmentGradeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradeBinding.inflate(inflater, container, false);

        checkTotal(userId);
        checkSemester(semesterGradeId);

        return binding.getRoot();
    }

    private void checkTotal(int userId) {
        ServicePool.totalGradeService.getTotalGradeList(userId)
                .enqueue(new Callback<GradeDto>() {
                    @Override
                    public void onResponse(Call<GradeDto> call, Response<GradeDto> response) {
                        Toast.makeText(requireActivity(), "서버 통신 성공", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<GradeDto> call, Throwable t) {
                        Toast.makeText(requireActivity(), "서버 통신 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkSemester(long semesterGradeId){
        ServicePool.semesterGradeService.getSemesterGradeList(semesterGradeId)
                .enqueue(new Callback<SemesterGradeDto>() {
                    @Override
                    public void onResponse(Call<SemesterGradeDto> call, Response<SemesterGradeDto> response) {
                        Toast.makeText(requireActivity(), "서버 통신 성공", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<SemesterGradeDto> call, Throwable t) {
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