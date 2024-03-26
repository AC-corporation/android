package com.example.allclear.grade;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.MyApplication;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.data.ServicePool;
import com.example.allclear.data.response.GradeResponseDto;
import com.example.allclear.data.service.GetGradeService;
import com.example.allclear.databinding.FragmentGradeBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradeFragment extends Fragment {

    private LineChart chart;
    private long userId;
    GetGradeService getGradeService;
    private FragmentGradeBinding binding;
    private String accessToken;
    private PreferenceUtil preferenceUtil;
    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getGradeService = ServicePool.getGradeService;

        preferenceUtil = MyApplication.getPreferences();
        userId = preferenceUtil.getUserId(-1L);
        accessToken = preferenceUtil.getAccessToken("FAIL");

        Log.i("auth", "userId: " + Long.toString(userId));
        Log.i("auth", "accessToken: " + accessToken);

    }

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
        recyclerView = binding.recyclerViewSubject;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // 이 라인 추가
        fetchGrade();  // 데이터를 받아오는 메서드를 호출합니다.
    }

    //그래프를 생성하는 함수
    private void makeGraph(GradeResponseDto gradeData){
        chart = (LineChart) binding.chart;

        //학기 순서 뒤집기(1-1 부터 나오게)
        Collections.reverse(gradeData.data.semesterGradeDtoList);

        //학기 이름 추출하기
        String[] semester_names = new String[gradeData.data.semesterGradeDtoList.size()];
        int count = 0;
        for(GradeResponseDto.GradeData.SemesterGradeDto semester : gradeData.data.semesterGradeDtoList) {
            semester_names[count++] = semester.semesterTitle;
        }

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

        // descriptionlabel 제거
        Description description = new Description();
        description.setEnabled(false);
        chart.setDescription(description);

        // 소수점 첫째 자리까지만 표시하는 ValueFormatter를 생성
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getPointLabel(Entry entry) {
                return String.format("%.1f", entry.getY());
            }
        };

        // 데이터 세트에 ValueFormatter를 설정
        dataSet.setValueFormatter(formatter);

        // X축 라벨을 설정
        String[] semesters = semester_names;
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // X축을 하단에 위치
        xAxis.setValueFormatter(new IndexAxisValueFormatter(semesters));
        xAxis.setDrawGridLines(false); // X축 그리드 라인 제거

        // Y축 라벨을 설정
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0f);  // 최소값
        yAxisLeft.setAxisMaximum(4.5f);  // 최대값
        yAxisLeft.setLabelCount(5, true);  // 라벨 개수
        String[] yAxisValues = new String[]{"0", "1.5", "2.5", "3.5", "4.5"};  // 사용자가 정의한 Y축 라벨 값들
        yAxisLeft.setValueFormatter(new IndexAxisValueFormatter(yAxisValues));
        yAxisLeft.setDrawGridLines(false); // Y축 왼쪽 그리드 라인 제거

        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false); // Y축 오른쪽 제거

        // 범례 제거
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        //차트 내 학기 선택시 리스너
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // e는 선택된 항목에 대한 정보를 가지고 있습니다.
                // e.getX() 또는 e.getY()를 통해 값을 얻을 수 있습니다.
                // h는 선택된 항목에 대한 하이라이트 정보를 가지고 있습니다.

                // 예를 들어, 만약 학기 이름을 가져오려면
                int index = (int) e.getX();
                String semester = semesters[index];

                // 이제 semester 변수는 선택된 항목의 학기 이름을 가지고 있습니다.
                // 이 정보를 활용해서 원하는 행동을 하면 됩니다.
                // 예를 들어, Toast 메시지를 표시하려면 다음과 같이 작성할 수 있습니다.
                binding.tvGradeSemester.setText(semester);
                updateRecyclerView(gradeData.data.semesterGradeDtoList.get(index));
            }

            @Override
            public void onNothingSelected() {
                // 아무런 항목도 선택되지 않았을 때의 동작을 여기에 작성합니다.
            }
        });

        // 차트를 새로 고침합니다.
        chart.invalidate();
    }

    //전체 평점과 취득 학점을 업데이트 하는 함수
    private void setGrade(GradeResponseDto gradeData){
        String totalGrade = String.valueOf(gradeData.data.getAverageGrade());  // 학점 데이터를 가져옵니다.
        String totalCredit = String.valueOf(gradeData.data.getTotalCredit());  // 이수 학점 데이터를 가져옵니다.

        binding.tvGradeTotal.setText(totalGrade);
        binding.tvGradeGet.setText(totalCredit);
    }

    //그래프를 업데이트 하는 함수
    private void fetchGrade() {
        //access 토큰, 유저 아이디 로딩 실패시 예외 처리
        if(accessToken.equals("FAIL") || userId == -1L){
            Toast.makeText(getContext(),"로그인 정보를 가져오는대 실패했어요. 다시 로그인해 주세요",Toast.LENGTH_SHORT);
            return;
        }

        Call<GradeResponseDto> call = ServicePool.getGradeService.getGradeData("Bearer " + accessToken , userId);
        call.enqueue(new Callback<GradeResponseDto>() {
            @Override
            public void onResponse(Call<GradeResponseDto> call, Response<GradeResponseDto> response) {
                if (response.isSuccessful()) {
                    GradeResponseDto gradeData = response.body();
                    makeGraph(gradeData);  // 데이터를 받아온 후 차트를 생성
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

    //리사이클러뷰 업데이트 함수
    public void updateRecyclerView(GradeResponseDto.GradeData.SemesterGradeDto semesterGrade) {
        if (semesterGrade == null || semesterGrade.semesterSubjectDtoList == null) {
            // semesterGrade가 null이거나 semesterGrade.semesterSubjectDtoList가 null인 경우 리턴
            Toast.makeText(getContext(),"성적 정보 동기화가 실패했어요",Toast.LENGTH_SHORT);
            return;
        }

        List<GradeResponseDto.GradeData.SemesterGradeDto.SemesterSubjectDto> semesterSubjectDtoList = semesterGrade.semesterSubjectDtoList;

        if (!semesterSubjectDtoList.isEmpty()) {
            GradeAdapter gradeAdapter = new GradeAdapter(semesterSubjectDtoList);
            recyclerView.setAdapter(gradeAdapter);
            gradeAdapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(getContext(),"성적 정보 동기화가 실패했어요",Toast.LENGTH_SHORT);
        }
    }
}