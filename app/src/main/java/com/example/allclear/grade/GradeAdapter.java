package com.example.allclear.grade;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.data.responese.GradeDto;
import com.example.allclear.databinding.ItemGradeBinding;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {

    private List<GradeDto.GradeResponseDto.SemesterGradeDtoList.SemesterSubjectDtoList> items;

    public GradeAdapter(List<GradeDto.GradeResponseDto.SemesterGradeDtoList.SemesterSubjectDtoList> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GradeViewHolder(ItemGradeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<GradeDto.GradeResponseDto.SemesterGradeDtoList.SemesterSubjectDtoList> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public static class GradeViewHolder extends RecyclerView.ViewHolder {
        private ItemGradeBinding binding;

        public GradeViewHolder(@NonNull ItemGradeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(GradeDto.GradeResponseDto.SemesterGradeDtoList.SemesterSubjectDtoList item) {
            binding.tvGrade.setText(item.getSemesterSubjectName());
            binding.tvGradeResult.setText(item.getSemesterSubjectScore());
        }
    }

}
