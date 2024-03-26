package com.example.allclear.timetable;

import android.util.Log;
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
import com.example.allclear.databinding.ActivitySelfAddPersonalTwoBinding;
import com.example.allclear.databinding.ActivitySelfAddTwoEditBinding;
import com.example.allclear.databinding.ItemviewAddtimeandplaceBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;
import com.example.allclear.schedule.AdapterSpinner;

import java.util.ArrayList;
import java.util.List;

public class addplacetimeadapter extends RecyclerView.Adapter<addplacetimeadapter.ViewHolder> {
    private SpinnerCustomBinding spinnerCustomBinding;
    private List<DataModel_timeplace> dataList;
    public addplacetimeadapter(List<DataModel_timeplace> dataList) {
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_addtimeandplace, parent, false);
        spinnerCustomBinding=spinnerCustomBinding=SpinnerCustomBinding.inflate(LayoutInflater.from(view.getContext()));
        return new ViewHolder(view,spinnerCustomBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel_timeplace data = dataList.get(position);
        holder.starttime.setText(data.getStarttime());
        holder.endtime.setText(data.getEndtime());
        holder.place.setText(data.getPlace());
        holder.spinner.setSelection(getday(data.getDay()));
        data.setStarttime(holder.starttime.getText().toString());
        data.setEndtime(holder.endtime.getText().toString());
        data.setPlace(holder.place.getText().toString());
        data.setDay(holder.spinner.getSelectedItem().toString());

        holder.starttime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    data.setStarttime(holder.starttime.getText().toString());
                }
            }
        });
        holder.endtime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    data.setEndtime(holder.endtime.getText().toString());
                }
            }
        });
        holder.place.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    data.setPlace(holder.place.getText().toString());
                }
            }
        });
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                data.setDay(holder.spinner.getSelectedItem().toString());
                Log.d("day",holder.spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText starttime;
        EditText endtime;
        Spinner spinner;
        EditText place;
        AdapterSpinner adapterSpinner;
        private SpinnerCustomBinding spinnerCustomBinding;
        ArrayList<String> days = new ArrayList<>();

        public ViewHolder(View itemview,SpinnerCustomBinding spinnerCustomBinding) {
            super(itemview);
            days.add("월요일");
            days.add("화요일");
            days.add("수요일");
            days.add("목요일");
            days.add("금요일");
            days.add("토요일");
            days.add("일요일");
            spinner=itemview.findViewById(R.id.day_spinner);
            adapterSpinner = new AdapterSpinner(spinner.getContext(),days); //그 값을 넣어줌
            spinner.setAdapter(adapterSpinner); //어댑터연결
            this.spinnerCustomBinding=spinnerCustomBinding;
            ImageButton downarrow=spinnerCustomBinding.ibDownArrow1;
            downarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            });
            starttime=itemview.findViewById(R.id.et_starttime);
            endtime=itemview.findViewById(R.id.et_endtime);
            place=itemview.findViewById(R.id.et_place);
            spinner=itemview.findViewById(R.id.day_spinner);

        }

    }
    protected int getday(String day) {
        if(day!=null){
            if (day.equals("월요일"))
                return 0;
            else if (day.equals("화요일"))
                return 1;
            else if (day.equals("수요일"))
                return 2;
            else if (day.equals("목요일"))
                return 3;
            else if (day.equals("금요일"))
                return 4;
            else if (day.equals("토요일"))
                return 5;
            else return 6;
        }
        return 7;
    }
}



