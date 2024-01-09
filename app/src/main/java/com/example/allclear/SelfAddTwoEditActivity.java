package com.example.allclear;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.allclear.databinding.ActivitySelfAddTwoBinding;
import com.example.allclear.databinding.ActivitySelfAddTwoEditBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;

import java.util.ArrayList;

public class SelfAddTwoEditActivity extends AppCompatActivity {
    Spinner spinner;
    AdapterDaySpinner adapterDaySpinner;

    private ActivitySelfAddTwoEditBinding binding;

    private SpinnerCustomBinding spinnerCustomBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddTwoEditBinding.inflate(getLayoutInflater());
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
        EditText subtext=binding.etSubTextOne;
        EditText professor=binding.etProfessorName;
        Spinner dayspinner=binding.daySpinner;
        EditText place=binding.etPlace;
        EditText starttime=binding.etStarttime;
        EditText endtime=binding.etEndtime;
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subtext.getText().toString().isEmpty())
                    Toast.makeText(SelfAddTwoEditActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                else
                    addscheduletotimetable();
            }
        });
    }
    public void addscheduletotimetable(){

    }
}