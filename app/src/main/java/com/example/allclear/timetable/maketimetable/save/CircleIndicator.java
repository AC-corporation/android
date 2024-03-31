package com.example.allclear.timetable.maketimetable.save;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class CircleIndicator extends LinearLayout {

    private Context mContext;
    private int mDefaultCircle = 0;
    private int mSelectCircle = 0;
    private List<ImageView> imageDot = new ArrayList<>();
    private float temp;

    public CircleIndicator(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        temp = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4.5f, getResources().getDisplayMetrics());
    }

    /**
     * 기본 점 생성
     *
     * @param count         점의 갯수
     * @param defaultCircle 기본 점의 이미지
     * @param selectCircle  선택된 점의 이미지
     * @param position      선택된 점의 포지션
     */
    public void createDotPanel(int count, int defaultCircle, int selectCircle, int position) {
        removeAllViews();
        mDefaultCircle = defaultCircle;
        mSelectCircle = selectCircle;

        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setPadding((int) temp, 0, (int) temp, 0);
            imageDot.add(imageView);
            addView(imageDot.get(i));
        }

        // 인덱스 선택
        selectDot(position);
    }

    /**
     * 선택된 점 표시
     *
     * @param position
     */
    public void selectDot(int position) {
        for (int i = 0; i < imageDot.size(); i++) {
            imageDot.get(i).setImageResource(i == position ? mSelectCircle : mDefaultCircle);
        }
    }
}
