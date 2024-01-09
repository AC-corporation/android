package com.example.allclear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterDaySpinner extends BaseAdapter {
    Context mContext;
    List<String> Data;
    LayoutInflater Inflater;

    public AdapterDaySpinner(Context context, List<String> data){
        this.mContext = context;
        this.Data = data;
        Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        if(Data!=null) return Data.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = Inflater.inflate(R.layout.spinner_custom, parent, false);
        }
        if(Data!=null){
            String text = Data.get(position);
            ((TextView)convertView.findViewById(R.id.day_spinner_text)).setText(text);
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = Inflater.inflate(R.layout.spinner_getview, parent, false);
        }
        String text = Data.get(position);
        ((TextView)convertView.findViewById(R.id.day_spinner_text)).setText(text);

        return convertView;
    }
}
