package com.example.allclear.grade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.R;
import com.example.allclear.data.response.GradeResponseDto;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {
    private List<GradeResponseDto.GradeData.SemesterGradeDto.SemesterSubjectDto> semesterSubjectDtoList;

    public GradeAdapter(List<GradeResponseDto.GradeData.SemesterGradeDto.SemesterSubjectDto> semesterSubjectDtoList) {
        this.semesterSubjectDtoList = semesterSubjectDtoList;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        GradeResponseDto.GradeData.SemesterGradeDto.SemesterSubjectDto semesterSubjectDto = semesterSubjectDtoList.get(position);
        holder.subjectName.setText(semesterSubjectDto.semesterSubjectName);
        holder.subjectScore.setText(semesterSubjectDto.semesterSubjectScore);
    }

    @Override
    public int getItemCount() {
        return semesterSubjectDtoList.size();
    }

    static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView subjectName;
        TextView subjectScore;

        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectName = itemView.findViewById(R.id.subject_name);
            subjectScore = itemView.findViewById(R.id.subject_score);
        }
    }
}

