package com.example.allclear.timetable.edit;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.allclear.data.response.*;
import com.example.allclear.databinding.ItemEditTimeTableTwoBinding;

import java.util.ArrayList;
import java.util.List;

public class EditTimeTableTwoAdapter extends RecyclerView.Adapter<EditTimeTableTwoAdapter.EditTimeTableTwoViewHolder> {

    private final List<SubjectAllDto.SubjectAllResponseDto.SubjectInfoResponseDto> subjectResponseDtoList;

    private static final List<Long> selectedSubjectIds = new ArrayList<>();

    public EditTimeTableTwoAdapter(List<SubjectAllDto.SubjectAllResponseDto.SubjectInfoResponseDto> subjectResponseDtoList) {
        if (subjectResponseDtoList == null) {
            this.subjectResponseDtoList = new ArrayList<>();
        } else {
            this.subjectResponseDtoList = subjectResponseDtoList;
        }
    }


    @NonNull
    @Override
    public EditTimeTableTwoAdapter.EditTimeTableTwoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEditTimeTableTwoBinding binding = ItemEditTimeTableTwoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EditTimeTableTwoAdapter.EditTimeTableTwoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EditTimeTableTwoAdapter.EditTimeTableTwoViewHolder holder, int position) {
        // 서버로부터 값 받아와서 Item에 각각 연결
        SubjectAllDto.SubjectAllResponseDto.SubjectInfoResponseDto subject = subjectResponseDtoList.get(position);
        holder.binding.tvTitle.setText(subject.getSubjectName());
        holder.binding.tvProfessor.setText(subject.getClassInfoResponseDtoList().get(0).getProfessor());
        holder.binding.tvYear.setText(subject.getSubjectTarget() + " / " + subject.getCredit());
    }

    public static List<Long> getSelectedSubjectIds() {
        return selectedSubjectIds;
    }

    @Override
    public int getItemCount() {
        return subjectResponseDtoList.size();
    }

    public static class EditTimeTableTwoViewHolder extends RecyclerView.ViewHolder {
        ItemEditTimeTableTwoBinding binding;

        public EditTimeTableTwoViewHolder(@NonNull ItemEditTimeTableTwoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}