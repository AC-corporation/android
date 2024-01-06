package com.example.allclear.maketimetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.allclear.AdapterDaySpinner;
import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelfAddTwoBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;

import java.util.ArrayList;

public class SelfAddTwoActivity extends AppCompatActivity {
    Spinner spinner;
    AdapterDaySpinner adapterDaySpinner;

    private ActivitySelfAddTwoBinding binding;

    private SpinnerCustomBinding spinnerCustomBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddTwoBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spinner = binding.daySpinner;
        ArrayList<String> day = new ArrayList<>();
        day.add("월요일");
        day.add("화요일");
        day.add("수요일");
        day.add("목요일");
        day.add("금요일");
        day.add("토요일");
        day.add("일요일");
        //ArrayList에 내가 스피너에 보여주고싶은 값 셋팅


        adapterDaySpinner = new AdapterDaySpinner(this, day); //그 값을 넣어줌
        spinner.setAdapter(adapterDaySpinner); //어댑터연결
        spinnerCustomBinding=SpinnerCustomBinding.inflate(getLayoutInflater());

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



    }
}