package com.example.allclear.auth;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.allclear.databinding.ActivityEmailAuthBinding;

public class EmailAuthActivity extends AppCompatActivity {
    ActivityEmailAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backBtnListener();
    }

    // 뒤로가기 버튼
    private void backBtnListener(){
        binding.ivSignupBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
