package com.example.allclear.timetable.maketimetable.save;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.databinding.ItemSaveTimeTableBinding;
import com.islandparadise14.mintable.model.ScheduleEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder> {
    private String[] stringDayList;
    private List<ScheduleEntity> scheduleEntityList;

    public TimeTableAdapter(String[] stringDayList, List<ScheduleEntity> scheduleEntityList) {
        this.stringDayList = Arrays.asList(stringDayList).toArray(new String[0]);
        this.scheduleEntityList = scheduleEntityList;
    }

    @NonNull
    @Override
    public TimeTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 레이아웃을 inflate하여 ViewHolder를 생성합니다.
        ItemSaveTimeTableBinding binding = ItemSaveTimeTableBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new TimeTableViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeTableViewHolder holder, int position) {
        // 데이터를 ViewHolder에 바인딩합니다.
        holder.bind(stringDayList, (ArrayList) scheduleEntityList);
    }

    @Override
    public int getItemCount() {
        return stringDayList.length;
    }

    public static class TimeTableViewHolder extends RecyclerView.ViewHolder {
        ItemSaveTimeTableBinding binding;

        public TimeTableViewHolder(@NonNull ItemSaveTimeTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(String[] day, ArrayList scheduleEntity) {
//            dayTextView.setText(day);
            binding.table.initTable(day);
            binding.table.updateSchedules(scheduleEntity);
            // 스케줄 데이터를 화면에 표시하는 방법에 따라 수정하세요.
            // 예: scheduleTextView.setText(scheduleEntity.getScheduleInfo());
        }
    }
}
