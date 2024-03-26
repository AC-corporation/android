package com.example.allclear.timetable.maketimetable.essential;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.data.response.TimeTableEssentialResponseDto;
import com.example.allclear.databinding.ItemTimeTableEssentialBinding;

import java.util.ArrayList;
import java.util.List;

public class EssentialSubjectAdapter extends RecyclerView.Adapter<EssentialSubjectAdapter.EssentialSubjectViewHolder> {

    private final List<TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList> subjectResponseDtoList;

    private static final List<Long> selectedSubjectIds = new ArrayList<>();

    public EssentialSubjectAdapter(List<TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList> subjectResponseDtoList) {
        if (subjectResponseDtoList == null) {
            this.subjectResponseDtoList = new ArrayList<>();
        } else {
            this.subjectResponseDtoList = subjectResponseDtoList;
        }
    }

    @NonNull
    @Override
    public EssentialSubjectAdapter.EssentialSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTimeTableEssentialBinding binding = ItemTimeTableEssentialBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EssentialSubjectAdapter.EssentialSubjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EssentialSubjectAdapter.EssentialSubjectViewHolder holder, int position) {
        // 서버로부터 값 받아와서 Item에 각각 연결
        TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList subject = subjectResponseDtoList.get(position);
        holder.binding.tvTitle.setText(subject.getSubjectName());

        // 해당 위치에 있는 classInfoResponseDtoList 가져오기
        List<TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList.classInfoResponseDtoList> classInfoList = subject.gettimetableGeneratorSubjectResponseDtoList();

        // 예외 처리: classInfoList가 null이거나 비어있는 경우
        if (classInfoList != null && !classInfoList.isEmpty()) {
            // 첫 번째 classInfoResponseDtoList 가져오기
            TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList.classInfoResponseDtoList classInfo = classInfoList.get(0);
            holder.binding.tvProfessor.setText(classInfo.getProfessor());
        }

        // 체크박스 선택된 것들만 따로 리스트 만들기
        TimeTableEssentialResponseDto.timetableGeneratorSubjectResponseDtoList item = subjectResponseDtoList.get(position);
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

    public static class EssentialSubjectViewHolder extends RecyclerView.ViewHolder {
        ItemTimeTableEssentialBinding binding;

        public EssentialSubjectViewHolder(@NonNull ItemTimeTableEssentialBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
