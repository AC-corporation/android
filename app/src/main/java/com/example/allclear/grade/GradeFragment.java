package com.example.allclear.grade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allclear.databinding.FragmentGradeBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GradeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GradeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private LineChart chart;

    public GradeFragment() {
        // Required empty public constructor
    }

    public static GradeFragment newInstance(String param1, String param2) {
        GradeFragment fragment = new GradeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        makeGraph();
    }

    private void makeGraph(){
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

// Y축 라벨을 설정합니다.
        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMinimum(0f);  // 최소값
        yAxis.setAxisMaximum(4.5f);  // 최대값
        yAxis.setLabelCount(5, true);  // 라벨 개수

// 차트를 새로 고침합니다.
        chart.invalidate();

    }
}