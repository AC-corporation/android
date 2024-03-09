package com.example.allclear.timetable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelfAddTwoBinding;
import com.example.allclear.databinding.ActivitySelfAddTwoEditBinding;
import com.example.allclear.databinding.ItemviewAddtimeandplaceBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.schedule.AdapterSpinner;

import java.util.ArrayList;
import java.util.List;

public class addplacetimeadapter extends RecyclerView.Adapter<addplacetimeadapter.ViewHolder> {
    private List<DataModel_timeplace> dataList;
    public addplacetimeadapter(List<DataModel_timeplace> dataList) {
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_addtimeandplace, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel_timeplace data = dataList.get(position);
        holder.starttime.setText(data.getStarttime());
        holder.endtime.setText(data.getEndtime());
        holder.place.setText(data.getPlace());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText starttime;
        EditText endtime;
        String day;
        EditText place;
        public ViewHolder(View itemview) {
            super(itemview);
            starttime=itemview.findViewById(R.id.et_starttime);
            endtime=itemview.findViewById(R.id.et_endtime);
            place=itemview.findViewById(R.id.et_place);

        }
    }
}



