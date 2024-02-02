package com.example.allclear.timetable.maketimetable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.SelfAddAdapter;
import com.example.allclear.schedule.Schedule;
import com.example.allclear.databinding.ActivitySelfAddOneBinding;

import java.util.ArrayList;

public class SelfAddOneActivity extends AppCompatActivity {

    private ActivitySelfAddOneBinding binding;
    private ArrayList<Schedule> ScheduleList=new ArrayList<Schedule>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySelfAddOneBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.ivPlusSelfAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfAddOneActivity.this, SelfAddTwoActivity.class);
                intent.putExtra("schedulelist",ScheduleList);
                startActivityForResult(intent,100);

            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelfAddOneActivity.this, SelectMajorBaseActivity.class);
                startActivity(intent);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==100&&resultCode==RESULT_OK){
            Schedule schedule=(Schedule)data.getSerializableExtra("schedule");
            ScheduleList.add(schedule);
            displayDataInRecyclerView(ScheduleList);
        }
    }
    private void displayDataInRecyclerView(ArrayList<Schedule> Schedulelist) {
        RecyclerView recyclerView = binding.rvSelfaddschedule;
        SelfAddAdapter adapter = new SelfAddAdapter(ScheduleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}