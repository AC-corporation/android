package com.example.allclear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.Utils;
import com.example.allclear.databinding.ActivityMainPageBinding;
import com.example.allclear.grade.GradeFragment;
import com.example.allclear.graduation.GraduationFragment;
import com.example.allclear.mypage.MyPageFragment;
import com.example.allclear.timetable.TimeTableFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainPageActivity extends AppCompatActivity {
    private ActivityMainPageBinding binding;
    private FragmentManager fragmentManager;
    private Fragment timeTable, gr, credit, myPage;
    static final String ACCESS_TOKEN = "Access_Token";
    static final String REFRESH_TOKEN = "Refresh_Token";
    static final String USER_ID = "User_Id";
    static final String DB = "allClear";

//    private PreferenceUtil preferences = MyApplication.getPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initBottomNavigation();
        PreferenceUtil preferenceUtil = MyApplication.getPreferences();

        Log.i(ACCESS_TOKEN, preferenceUtil.getAccessToken("Fail"));
        Log.i(REFRESH_TOKEN, preferenceUtil.getRefreshToken("Fail"));
        Log.i(USER_ID, "" + preferenceUtil.getUserId(-1L));
    }

    private void initBottomNavigation() {
        NavigationBarView navigationBarView = binding.bottomNavigationview;
        fragmentManager = getSupportFragmentManager();
        timeTable = new TimeTableFragment();
        fragmentManager.beginTransaction().replace(R.id.containers, timeTable).commit();
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.timeTable) {
                    if(timeTable == null) {
                        timeTable = new TimeTableFragment();
                        fragmentManager.beginTransaction().add(R.id.containers, timeTable).commit();
                    }
                    if(timeTable != null) fragmentManager.beginTransaction().show(timeTable).commit();
                    if(gr != null) fragmentManager.beginTransaction().hide(gr).commit();
                    if(credit != null) fragmentManager.beginTransaction().hide(credit).commit();
                    if(myPage != null) fragmentManager.beginTransaction().hide(myPage).commit();
                    return true;
                } else if (item.getItemId() == R.id.gr) {
                    if(gr == null) {
                        gr = new GradeFragment();
                        fragmentManager.beginTransaction().add(R.id.containers, gr).commit();
                    }
                    if(gr != null) fragmentManager.beginTransaction().show(gr).commit();
                    if(timeTable != null) fragmentManager.beginTransaction().hide(timeTable).commit();
                    if(credit != null) fragmentManager.beginTransaction().hide(credit).commit();
                    if(myPage != null) fragmentManager.beginTransaction().hide(myPage).commit();
                    return true;
                } else if (item.getItemId() == R.id.credit) {
                    if(credit == null) {
                        credit = new GraduationFragment();
                        fragmentManager.beginTransaction().add(R.id.containers, credit).commit();
                    }
                    if(credit != null) fragmentManager.beginTransaction().show(credit).commit();
                    if(timeTable != null) fragmentManager.beginTransaction().hide(timeTable).commit();
                    if(gr != null) fragmentManager.beginTransaction().hide(gr).commit();
                    if(myPage != null) fragmentManager.beginTransaction().hide(myPage).commit();
                    return true;
                } else if (item.getItemId() == R.id.myPage) {
                    if(myPage == null) {
                        myPage = new MyPageFragment();
                        fragmentManager.beginTransaction().add(R.id.containers, myPage).commit();
                    }
                    if(myPage != null) fragmentManager.beginTransaction().show(myPage).commit();
                    if(timeTable != null) fragmentManager.beginTransaction().hide(timeTable).commit();
                    if(gr != null) fragmentManager.beginTransaction().hide(gr).commit();
                    if(credit != null) fragmentManager.beginTransaction().hide(credit).commit();
                    return true;
                }
                return false;
            }
        });
    }
}