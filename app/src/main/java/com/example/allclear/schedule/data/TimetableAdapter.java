package com.example.allclear.schedule.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.R;
import com.example.allclear.schedule.Timetable;

import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewHolder> {
    private List<Timetable> timetables;
    private Context context;

    public TimetableAdapter(Context context, List<Timetable> timetables) {
        this.timetables = timetables;
        this.context = context;
    }

    @NonNull
    @Override
    public TimetableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_item, parent, false);
        return new TimetableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimetableViewHolder holder, int position) {
        Timetable timetable = timetables.get(position);
        holder.timetableName.setText(timetable.name);
    }

    @Override
    public int getItemCount() {
        return timetables.size();
    }

    public class TimetableViewHolder extends RecyclerView.ViewHolder {
        TextView timetableName;

        public TimetableViewHolder(@NonNull View itemView) {
            super(itemView);
            timetableName = itemView.findViewById(R.id.timetable_name);
        }
    }
}
