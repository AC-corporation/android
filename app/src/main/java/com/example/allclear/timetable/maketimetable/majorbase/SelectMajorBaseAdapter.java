package com.example.allclear.timetable.maketimetable.majorbase;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.data.response.TimeTableThreeResponseDto;
import com.example.allclear.databinding.ItemSelfAddBinding;

import java.util.List;

public class SelectMajorBaseAdapter extends RecyclerView.Adapter<SelectMajorBaseAdapter.SelectMajorBaseViewHolder> {

    private List<TimeTableThreeResponseDto.SubjectResponseDto> subjectResponseDtoList;

    public SelectMajorBaseAdapter(List<TimeTableThreeResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        this.subjectResponseDtoList = subjectResponseDtoList;
    }

    @NonNull
    @Override
    public SelectMajorBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSelfAddBinding binding = ItemSelfAddBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SelectMajorBaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectMajorBaseViewHolder holder, int position) {
        TimeTableThreeResponseDto.SubjectResponseDto subject = subjectResponseDtoList.get(position);

        holder.binding.tvTitle.setText(subject.getSubjectName());

        if (!subject.classInfoResponseDtoList.isEmpty()) {
            TimeTableThreeResponseDto.SubjectResponseDto.ClassInfoResponseDto classInfo = subject.classInfoResponseDtoList.get(0);
            holder.binding.tvDay.setText(classInfo.getProfessor());
            holder.binding.tvLocation.setText(String.valueOf(subject.getSubjectTime()));
        }
    }

    @Override
    public int getItemCount() {
        return subjectResponseDtoList.size();
    }

    public static class SelectMajorBaseViewHolder extends RecyclerView.ViewHolder {
        ItemSelfAddBinding binding;

        public SelectMajorBaseViewHolder(@NonNull ItemSelfAddBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
