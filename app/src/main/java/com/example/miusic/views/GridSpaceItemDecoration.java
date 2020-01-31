package com.example.miusic.views;

import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;

    public GridSpaceItemDecoration(int space, RecyclerView parent) {
        mSpace = space;
        getRecyclerViewOffsets(parent);
    }

    /**
     *
     * @param outRect edge of item
     * @param view item view
     * @param parent recyclerView
     * @param state state of recyclerView
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.left = mSpace;

        //determine if item is the first item of each line, if so, the space is 0

    }

    private void getRecyclerViewOffsets(RecyclerView parent) {

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)parent.getLayoutParams();
        layoutParams.leftMargin = -mSpace;
        parent.setLayoutParams(layoutParams);
    }
}
