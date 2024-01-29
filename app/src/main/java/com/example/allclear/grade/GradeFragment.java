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
import com.example.allclear.data.responese.GradeDto;
import com.example.allclear.data.responese.SemesterGradeDto;
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

    int userId = 1;
    long semesterGradeId = 1;
    private FragmentGradeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGradeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkTotal(userId);
        checkSemester(semesterGradeId);
        makeGraph();

    }

    private void checkTotal(int userId) {
        ServicePool.totalGradeService.getTotalGradeList(userId)
                .enqueue(new Callback<GradeDto>() {
                    @Override
                    public void onResponse(Call<GradeDto> call, Response<GradeDto> response) {
                        Toast.makeText(requireActivity(), "서버 통신 성공", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<GradeDto> call, Throwable t) {
                        Toast.makeText(requireActivity(), "서버 통신 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkSemester(long semesterGradeId) {
        ServicePool.semesterGradeService.getSemesterGradeList(semesterGradeId)
                .enqueue(new Callback<SemesterGradeDto>() {
                    @Override
                    public void onResponse(Call<SemesterGradeDto> call, Response<SemesterGradeDto> response) {
                        Toast.makeText(requireActivity(), "서버 통신 성공", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<SemesterGradeDto> call, Throwable t) {
                        Toast.makeText(requireActivity(), "서버 통신 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void makeGraph() {
        LineChart chart = (LineChart) binding.chart;

        // 학점 데이터를 생성합니다.
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 2.5f));  // 1-1 학기
        entries.add(new Entry(1, 3.0f));  // 1-2 학기
        entries.add(new Entry(2, 3.2f));  // 2-1 학기
        entries.add(new Entry(3, 3.5f));  // 2-2 학기
        entries.add(new Entry(4, 3.8f));  // 3-1 학기
        entries.add(new Entry(5, 4.0f));  // 3-2 학기
        entries.add(new Entry(6, 4.2f));  // 4-1 학기
        entries.add(new Entry(7, 4.5f));  // 4-2 학기

        // 데이터 세트를 생성하고 차트에 데이터를 설정합니다.
        LineDataSet dataSet = new LineDataSet(entries, "학점");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        // X축 라벨을 설정합니다.
        String[] semesters = new String[]{"1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1", "4-2"};
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}