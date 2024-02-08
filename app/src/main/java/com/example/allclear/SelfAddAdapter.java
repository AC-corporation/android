package com.example.allclear;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.schedule.Schedule;

import java.util.ArrayList;

public class SelfAddAdapter extends RecyclerView.Adapter<SelfAddAdapter.ViewHolder> {
    private ArrayList<Schedule>scheduleDataList;

    public SelfAddAdapter(ArrayList<Schedule> scheduleDataList) {
        this.scheduleDataList = scheduleDataList;
    }

    @NonNull
    @Override
    public SelfAddAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_self_add, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelfAddAdapter.ViewHolder holder, int position) {
        holder.title.setText(scheduleDataList.get(position).getScheduleName());
        holder.day.setText(getday(scheduleDataList.get(position).getScheduleDay()));
        holder.start.setText(scheduleDataList.get(position).getStartTime());
        holder.end.setText(scheduleDataList.get(position).getEndTime());
        holder.location.setText(scheduleDataList.get(position).getRoomInfo());
    }

    @Override
    public int getItemCount() {
        return (null!=scheduleDataList?scheduleDataList.size():0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView day;
        protected TextView tilde;
        protected TextView start;
        protected TextView end;
        protected TextView location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.tv_title);
            day=itemView.findViewById(R.id.tv_day);
            tilde=itemView.findViewById(R.id.tv_tilde);
            start=itemView.findViewById(R.id.tv_start);
            end=itemView.findViewById(R.id.tv_end);
            location=itemView.findViewById(R.id.tv_location);
        }
    }
    protected String getday(int day) {
        if (day==0)
            return "월";
        else if (day==1)
            return "화";
        else if (day==2)
            return "수";
        else if (day==3)
            return "목";
        else if (day==4)
            return "금";
        else
            return "토";
    }
}
