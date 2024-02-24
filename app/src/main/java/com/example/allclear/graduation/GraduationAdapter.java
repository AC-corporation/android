package com.example.allclear.graduation;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.R;
import com.example.allclear.data.response.GraduationDto;
import com.example.allclear.databinding.ItemGraduationBinding;

import java.util.List;

public class GraduationAdapter extends RecyclerView.Adapter<GraduationAdapter.GraduationViewHolder> {
    private List<GraduationDto.RequirementResponseDto.RequirementComponentDto> requirementComponentDtoList;

    public GraduationAdapter(List<GraduationDto.RequirementResponseDto.RequirementComponentDto> requirementComponentDtoList) {
        this.requirementComponentDtoList = requirementComponentDtoList;
    }

    @NonNull
    @Override
    public GraduationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemGraduationBinding itemBinding = ItemGraduationBinding.inflate(layoutInflater, parent, false);
        return new GraduationViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GraduationViewHolder holder, int position) {
        GraduationDto.RequirementResponseDto.RequirementComponentDto requirementComponentDto = requirementComponentDtoList.get(position);
        holder.bind(requirementComponentDto);
    }

    @Override
    public int getItemCount() {
        return requirementComponentDtoList.size();
    }

    public static class GraduationViewHolder extends RecyclerView.ViewHolder {
        private ItemGraduationBinding binding;

        public GraduationViewHolder(@NonNull ItemGraduationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(GraduationDto.RequirementResponseDto.RequirementComponentDto requirementComponentDto) {
            String requirementArgument = requirementComponentDto.getRequirementArgument();
            double requirementCriteria = requirementComponentDto.getRequirementCriteria();
            double requirementComplete = requirementComponentDto.getRequirementComplete();
            binding.tvGraduation.setText(requirementArgument);

            String result = binding.getRoot().getContext().getString(R.string.graduation_result, requirementComplete, requirementCriteria);
            binding.tvGraduationResult.setText(result);
        }

    }
}