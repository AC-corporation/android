package com.example.allclear.graduation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allclear.R;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.responese.GraduationDto;
import com.example.allclear.databinding.FragmentGraduationBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraduationFragment extends Fragment {
    private FragmentGraduationBinding binding;
    int userId = 1;
    ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> requirementComponentDtoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGraduationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getUserId();
        checkServer(userId);

    }

    // 로그인이 되면 id 가져오는 것으로 하겠습니다.
    private void getUserId() {

    }

    private void checkServer(int userId) {
        ServicePool.graduationService.getListFromServer(userId)
                .enqueue(new Callback<GraduationDto>() {
                    @Override
                    public void onResponse(Call<GraduationDto> call, Response<GraduationDto> response) {
                        Toast.makeText(requireActivity(), R.string.server_success, Toast.LENGTH_SHORT).show();

                        GraduationDto graduationDto = response.body();

                        if (graduationDto != null && graduationDto.data != null) {
                            ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> totalList = new ArrayList<>();
                            ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> generalList = new ArrayList<>();
                            ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> majorList = new ArrayList<>();

                            for (GraduationDto.RequirementResponseDto.RequirementComponentDto dto : graduationDto.data.getRequirementComponentList()) {
                                if (requireActivity().getString(R.string.graduation_total).equals(dto.getRequirementCategory())) {
                                    totalList.add(dto);
                                } else if (requireActivity().getString(R.string.graduation_general).equals(dto.getRequirementCategory())) {
                                    generalList.add(dto);
                                } else if (requireActivity().getString(R.string.graduation_major).equals(dto.getRequirementCategory())) {
                                    majorList.add(dto);
                                }
                            }

                            initTotalAdapter(totalList);
                            initGeneralAdapter(generalList);
                            initMajorAdapter(majorList);

                        } else {
                            Toast.makeText(requireActivity(), "서버에서 데이터를 받지 못함", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GraduationDto> call, Throwable t) {
                        Toast.makeText(requireActivity(), R.string.server_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initTotalAdapter(ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> totalList) {
        GraduationAdapter graduationAdapter = new GraduationAdapter(totalList);
        binding.rvGraduationTotal.setAdapter(graduationAdapter);
        binding.rvGraduationTotal.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    private void initGeneralAdapter(ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> generalList) {
        GraduationAdapter graduationAdapter = new GraduationAdapter(generalList);
        binding.rvGraduationGeneral.setAdapter(graduationAdapter);
        binding.rvGraduationGeneral.setLayoutManager(new LinearLayoutManager((requireActivity())));
    }

    private void initMajorAdapter(ArrayList<GraduationDto.RequirementResponseDto.RequirementComponentDto> majorList) {
        GraduationAdapter graduationAdapter = new GraduationAdapter(majorList);
        binding.rvGraduationMajor.setAdapter(graduationAdapter);
        binding.rvGraduationMajor.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
