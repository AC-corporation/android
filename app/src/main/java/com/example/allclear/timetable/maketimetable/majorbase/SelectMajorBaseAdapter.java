package com.example.allclear.timetable.maketimetable.majorbase;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectMajorBaseAdapter extends RecyclerView.Adapter<SelectMajorBaseAdapter.SelectMajorBaseViewHolder> {

    @NonNull
    @Override
    public SelectMajorBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectMajorBaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SelectMajorBaseViewHolder extends RecyclerView.ViewHolder {

        public SelectMajorBaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
