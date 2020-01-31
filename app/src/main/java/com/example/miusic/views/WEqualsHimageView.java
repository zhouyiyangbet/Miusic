package com.example.miusic.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import static android.view.View.MeasureSpec.getSize;

public class WEqualsHimageView extends AppCompatImageView {

    public WEqualsHimageView(Context context) {
        super(context);
    }

    public WEqualsHimageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WEqualsHimageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int mode = MeasureSpec.getMode(widthMeasureSpec);
    }
}
