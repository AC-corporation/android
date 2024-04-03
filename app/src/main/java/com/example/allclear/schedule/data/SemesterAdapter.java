package com.example.allclear.schedule.data;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allclear.MainPageActivity;
import com.example.allclear.MyApplication;
import com.example.allclear.R;
import com.example.allclear.data.PreferenceUtil;
import com.example.allclear.schedule.Semester;
import com.example.allclear.schedule.TimeTable;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {
    private List<Semester> semesters;
    private Context context;
    private TimetableDao timetableDao;

    public SemesterAdapter(Context context, List<Semester> semesters, AppDatabase db) {
        this.semesters = semesters;
        this.context = context;
        this.timetableDao = db.timetableDao();  // TimetableDao 초기화
    }


    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_item, parent, false);
        return new SemesterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        Semester semester = semesters.get(position);
        holder.semesterName.setText(semester.name);

        // ExecutorService를 사용하여 백그라운드에서 시간표를 가져오는 작업 실행
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<TimeTable>> future = executorService.submit(() -> timetableDao.findTimetablesBySemesterId(semester.id));

        // Future를 사용하여 작업이 완료되면 UI 업데이트
        executorService.execute(() -> {
            try {
                final List<TimeTable> timeTables = future.get();
                new android.os.Handler(Looper.getMainLooper()).post(() -> {
                    // 시간표 RecyclerView 설정
                    TimetableAdapter timetableAdapter = new TimetableAdapter(context, timeTables);
                    timetableAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            TimeTable clickedItem = timeTables.get(position);
                            // 기본 시간표 아이디 저장
                            PreferenceUtil preferenceUtil = MyApplication.getPreferences();
                            preferenceUtil.setDefaultTableId(clickedItem.id);

                            Intent intent = new Intent(context, MainPageActivity.class);
                            context.startActivity(intent);
                        }
                    });
                    holder.timetablesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    holder.timetablesRecyclerView.setAdapter(timetableAdapter);
                });
            } catch (ExecutionException | InterruptedException e) {
                // 에러 처리
                e.printStackTrace();
            }
        });
    }



    @Override
    public int getItemCount() {
        return semesters.size();
    }

    public class SemesterViewHolder extends RecyclerView.ViewHolder {
        TextView semesterName;
        RecyclerView timetablesRecyclerView;

        public SemesterViewHolder(@NonNull View itemView) {
            super(itemView);
            semesterName = itemView.findViewById(R.id.semester_name);
            timetablesRecyclerView = itemView.findViewById(R.id.timetables_recyclerview);
        }
    }
}

