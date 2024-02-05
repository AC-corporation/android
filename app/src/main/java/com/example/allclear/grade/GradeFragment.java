package com.example.allclear.grade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.allclear.data.ServicePool;
import com.example.allclear.data.gradeData.GetGradeService;
import com.example.allclear.data.gradeData.GradeResponseDto;
import com.example.allclear.databinding.FragmentGradeBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeFragment extends Fragment {

    private LineChart chart;
    GetGradeService getGradeService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGradeService = ServicePool.getGradeService;
    }

    private FragmentGradeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGradeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchGrade();  // 데이터를 받아오는 메서드를 호출합니다.
    }


    //그래프를 생성하는 함수
    private void makeGraph(GradeResponseDto gradeData){
        LineChart chart = (LineChart) binding.chart;

        // 학점 데이터를 생성합니다.
        ArrayList<Entry> entries = new ArrayList<>();
        for(int i = 0; i < gradeData.data.semesterGradeDtoList.size(); i++) {
            String grade = gradeData.data.semesterGradeDtoList.get(i).semesterAverageGrade;
            entries.add(new Entry(i, Float.parseFloat(grade)));
        }

        // 데이터 세트를 생성하고 차트에 데이터를 설정합니다.
        LineDataSet dataSet = new LineDataSet(entries, "학점");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        // X축 라벨을 설정합니다.
        String[] semesters = new String[gradeData.data.semesterGradeDtoList.size()];
        for(int i = 0; i < gradeData.data.semesterGradeDtoList.size(); i++) {
            semesters[i] = String.valueOf(i+1);
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(semesters));
        xAxis.setDrawGridLines(false); // X축 그리드 라인 제거

        // Y축 라벨을 설정합니다.
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0f);  // 최소값
        yAxisLeft.setAxisMaximum(4.5f);  // 최대값
        yAxisLeft.setLabelCount(5, true);  // 라벨 개수
        yAxisLeft.setDrawGridLines(false); // Y축 왼쪽 그리드 라인 제거

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false); // Y축 오른쪽 제거

        // 범례 제거
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        // 차트를 새로 고침합니다.
        chart.invalidate();
    }

    //전체 평점과 취득 학점을 업데이트 하는 함수
    private void setGrade(GradeResponseDto gradeData){
        String totalGrade = String.valueOf(gradeData.data.getTotalCredit());  // 학점 데이터를 가져옵니다.
        String totalCredit = String.valueOf(gradeData.data.getTotalCredit());  // 이수 학점 데이터를 가져옵니다.

        binding.tvGradeTotal.setText(totalGrade);
        binding.tvCreditGot.setText(totalCredit);
    }

    //그래프를 업데이트 하는 함수
    private void fetchGrade() {

        String userId = "사용자 아이디";  // 실제 사용자 아이디로 변경해야 함
        Call<GradeResponseDto> call = ServicePool.getGradeService.getGradeData(userId);
        call.enqueue(new Callback<GradeResponseDto>() {
            @Override
            public void onResponse(Call<GradeResponseDto> call, Response<GradeResponseDto> response) {
                if (response.isSuccessful()) {
                    GradeResponseDto gradeData = response.body();
                    makeGraph(gradeData);  // 데이터를 받아온 후 차트를 생성합니다.
                    setGrade(gradeData); // 데이터를 받아온 후 전체평점, 취득학점을 업데이트
                }
            }

            @Override
            public void onFailure(Call<GradeResponseDto> call, Throwable t) {
                // 통신 실패 시 처리하는 코드
                Toast.makeText(getContext(),"성적 정보 동기화가 실패했어요",Toast.LENGTH_SHORT);
            }
        });
    }

}