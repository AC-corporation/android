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
    private void getUserId(){
        // 로그인 시 userId를 설정하는 코드를 작성해 주세요.
    }

    private void checkServer(int userId) {
        ServicePool.graduationService.getListFromServer(userId)
                .enqueue(new Callback<GraduationDto>() {
                    @Override
                    public void onResponse(Call<GraduationDto> call, Response<GraduationDto> response) {
                        Toast.makeText(requireActivity(), "서버 통신 성공", Toast.LENGTH_SHORT).show();

                        // 서버에서 받아온 GraduationDto
                        GraduationDto graduationDto = response.body();

                        // GraduationDto에서 RequirementComponentDto의 리스트를 가져옵니다.
                        requirementComponentDtoList = graduationDto.data.getRequirementComponentList();

                        // 어댑터를 초기화합니다.
                        initTotalAdapter();
                    }

                    @Override
                    public void onFailure(Call<GraduationDto> call, Throwable t) {
                        Toast.makeText(requireActivity(), "서버 통신 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initTotalAdapter(){
        GraduationAdapter graduationAdapter = new GraduationAdapter(requirementComponentDtoList);
        binding.rvGraduationTotal.setAdapter(graduationAdapter);
        binding.rvGraduationTotal.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
