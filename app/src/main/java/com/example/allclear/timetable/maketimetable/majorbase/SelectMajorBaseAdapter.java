//package com.example.allclear.timetable.maketimetable.majorbase;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.allclear.R;
//import com.example.allclear.data.response.TimeTableThreeResponseDto;
//import com.example.allclear.databinding.ItemSelfAddBinding;
//
//import java.util.List;
//
//public class SelectMajorBaseAdapter extends RecyclerView.Adapter<SelectMajorBaseAdapter.SelectMajorBaseViewHolder> {
//
//    private List<TimeTableThreeResponseDto.SubjectResponseDto> subjectResponseDtoList;
//
//    public SelectMajorBaseAdapter(List<TimeTableThreeResponseDto.SubjectResponseDto> subjectResponseDtoList) {
//        this.subjectResponseDtoList = subjectResponseDtoList;
//    }
//
//    @NonNull
//    @Override
//    public SelectMajorBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_self_add, parent, false);
//        return new SelectMajorBaseViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SelectMajorBaseViewHolder holder, int position) {
//        TimeTableThreeResponseDto.SubjectResponseDto subject = subjectResponseDtoList.get(position);
//
//        holder.tvTitle.setText(subject.subjectName);
//        holder.tvDay.setText(subject.professor);
//        holder.tvLocation.setText(subject.subjectTime);
//    }
//
//    @Override
//    public int getItemCount() {
//        return subjectResponseDtoList.size();
//    }
//
//    public static class SelectMajorBaseViewHolder extends RecyclerView.ViewHolder {
//
//        private ItemSelfAddBinding binding;
//
//        public SelectMajorBaseViewHolder(@NonNull ItemSelfAddBinding binding){
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//
//        public SelectMajorBaseViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            public void bind(TimeTableThreeResponseDto.SubjectResponseDto require){
//
//            }
//            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvDay = itemView.findViewById(R.id.tv_day);
//            tvLocation = itemView.findViewById(R.id.tv_location);
//        }
//    }
//}
