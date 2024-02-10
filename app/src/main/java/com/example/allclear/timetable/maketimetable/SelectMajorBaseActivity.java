package com.example.allclear.timetable.maketimetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.allclear.R;
import com.example.allclear.databinding.ActivitySelectMajorBaseBinding;
import com.example.allclear.databinding.SpinnerCustomBinding;

import java.util.ArrayList;

public class SelectMajorBaseActivity extends AppCompatActivity {
    private ActivitySelectMajorBaseBinding binding;
    private Spinner spinner;
    private AdapterYearSpinner adapterYearSpinner;
    private SpinnerCustomBinding spinnerCustomBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectMajorBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initNextClickListener();
        initBackClickListener();
        setYearSpinner();

    }

    private void initNextClickListener() {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectMajorBaseActivity.this, SelectEssentialGeneralElectiveActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initBackClickListener() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setYearSpinner() {
        spinner = binding.yearSpinner;
        ArrayList<String> years = new ArrayList<>();
        years.add(getString(R.string.first_year));
        years.add(getString(R.string.second_year));
        years.add(getString(R.string.third_year));
        years.add(getString(R.string.fourth_year));

        adapterYearSpinner = new AdapterYearSpinner(this, years);
        spinner.setAdapter(adapterYearSpinner);
        spinnerCustomBinding = SpinnerCustomBinding.inflate(getLayoutInflater());

        downArrowClickListener();
    }

    private void downArrowClickListener() {
        ImageButton downArrow = spinnerCustomBinding.ibDownArrow1;
        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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