package com.example.allclear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.allclear.databinding.ActivityMainPageBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainPageActivity extends AppCompatActivity {
    private ActivityMainPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBottomNavigation();
    }
    private void initBottomNavigation(){
        NavigationBarView navigationBarView = binding.bottomNavigationview;
        getSupportFragmentManager().beginTransaction().replace(R.id.containers, new TimeTableFragment()).commit();
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.timeTable) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, new TimeTableFragment()).commit();
                    return true;
                } else if (item.getItemId() == R.id.gr) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, new GradeFragment()).commit();
                    return true;
                } else if (item.getItemId() == R.id.credit) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, new GraduationFragment()).commit();
                    return true;
                } else if (item.getItemId() == R.id.myPage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.containers, new MyPageFragment()).commit();
                    return true;
                }
                return false;
            }
        });
    }
}