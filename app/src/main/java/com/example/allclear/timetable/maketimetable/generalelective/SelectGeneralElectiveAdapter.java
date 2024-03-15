package com.example.allclear.timetable.maketimetable.generalelective;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.data.response.TimeTableGetResponseDto;
import com.example.allclear.databinding.ItemArgumentBinding;

import java.util.List;

public class SelectGeneralElectiveAdapter extends RecyclerView.Adapter<SelectGeneralElectiveAdapter.SelectGeneralElectiveViewHolder> {

    private List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponentList;

    public SelectGeneralElectiveAdapter(List<TimeTableGetResponseDto.RequirementComponentResponseDto> requirementComponentList) {
        this.requirementComponentList = requirementComponentList;
    }

    @NonNull
    @Override
    public SelectGeneralElectiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemArgumentBinding binding = ItemArgumentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SelectGeneralElectiveViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectGeneralElectiveViewHolder holder, int position) {
        TimeTableGetResponseDto.RequirementComponentResponseDto component = requirementComponentList.get(position);

        holder.binding.tvArgument.setText(component.getRequirementArgument());

    }

    @Override
    public int getItemCount() {
        return requirementComponentList.size();
    }

    public static class SelectGeneralElectiveViewHolder extends RecyclerView.ViewHolder {

        ItemArgumentBinding binding;

        public SelectGeneralElectiveViewHolder(@NonNull ItemArgumentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
