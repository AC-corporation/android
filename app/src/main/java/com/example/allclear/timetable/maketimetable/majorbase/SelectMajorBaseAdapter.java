package com.example.allclear.timetable.maketimetable.majorbase;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.data.response.TimeTableThreeResponseDto;
import com.example.allclear.databinding.ItemTimeTableBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectMajorBaseAdapter extends RecyclerView.Adapter<SelectMajorBaseAdapter.SelectMajorBaseViewHolder> {

    private List<TimeTableThreeResponseDto.SubjectResponseDto> subjectResponseDtoList;

    private static List<Long> selectedSubjectIds = new ArrayList<>();

    public SelectMajorBaseAdapter(List<TimeTableThreeResponseDto.SubjectResponseDto> subjectResponseDtoList) {
        this.subjectResponseDtoList = subjectResponseDtoList;
    }

    @NonNull
    @Override
    public SelectMajorBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTimeTableBinding binding = ItemTimeTableBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SelectMajorBaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectMajorBaseViewHolder holder, int position) {
        // 서버로부터 값 받아와서 Item에 각각 연결
        TimeTableThreeResponseDto.SubjectResponseDto subject = subjectResponseDtoList.get(position);

        holder.binding.tvTitle.setText(subject.getSubjectName());

        if (!subject.classInfoResponseDtoList.isEmpty()) {
            TimeTableThreeResponseDto.SubjectResponseDto.ClassInfoResponseDto classInfo = subject.classInfoResponseDtoList.get(0);

            holder.binding.tvProfessor.setText(classInfo.getProfessor());
            holder.binding.tvYear.setText(String.valueOf(subject.getSubjectTime()));
        }

        // 체크박스 선택된 것들만 따로 리스트 만들기
        TimeTableThreeResponseDto.SubjectResponseDto item = subjectResponseDtoList.get(position);
        holder.binding.cbTimeTable.setOnCheckedChangeListener(null);
        holder.binding.cbTimeTable.setChecked(selectedSubjectIds.contains(item.getSubjectId()));
        holder.binding.cbTimeTable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedSubjectIds.add(item.getSubjectId());
                } else {
                    selectedSubjectIds.remove(item.getSubjectId());
                }
            }
        });

    }

    public static List<Long> getSelectedSubjectIds() {
        return selectedSubjectIds;
    }

    @Override
    public int getItemCount() {
        return subjectResponseDtoList.size();
    }

    public static class SelectMajorBaseViewHolder extends RecyclerView.ViewHolder {
        ItemTimeTableBinding binding;

        public SelectMajorBaseViewHolder(@NonNull ItemTimeTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}