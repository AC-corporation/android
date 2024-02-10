package com.example.allclear.timetable.maketimetable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.allclear.R;

import java.util.List;

public class AdapterYearSpinner extends BaseAdapter {

    Context context;
    List<String> data;
    LayoutInflater inflater;

    public AdapterYearSpinner(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data != null) return data.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_custom, parent, false);
        }
        if (data != null) {
            String text = data.get(position);
            ((TextView) convertView.findViewById(R.id.day_spinner_text)).setText(text);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_getview, parent, false);
        }
        String text = data.get(position);
        ((TextView) convertView.findViewById(R.id.day_spinner_text)).setText(text);

        return convertView;
    }
}
