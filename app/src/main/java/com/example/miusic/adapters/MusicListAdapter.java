package com.example.miusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miusic.R;
import com.example.miusic.activities.PlayMusicActivity;
import com.example.miusic.models.MusicModel;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {

    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalcRecyclerViewHeight;
    private List<MusicModel> mDataSource;

    public MusicListAdapter(Context context, RecyclerView recyclerView, List<MusicModel>dataSource) {
        mContext = context;
        mRv = recyclerView;
        mDataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mItemView = LayoutInflater.from(mContext).inflate(R.layout.item_list_music, parent, false);
        return new ViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setRecyclerViewHeight();

        final MusicModel musicModel = mDataSource.get(position);

        Glide.with(mContext)
                .load(musicModel.getPoster())
                .into(holder.ivIcon);

        holder.mTVName.setText(musicModel.getName());
        holder.mTvAuthor.setText(musicModel.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayMusicActivity.class);
                intent.putExtra(PlayMusicActivity.MUSIC_ID, musicModel.getMusicId());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    /**
     * 1.get height of each item view
     * 2.get count of item view
     * 3. itemViewHeight * count = RecyclerViewHeight
     */
    private void setRecyclerViewHeight() {

        if (isCalcRecyclerViewHeight || mRv == null) return;

        isCalcRecyclerViewHeight = true;
        //get height of itemView
        RecyclerView.LayoutParams itemViewLp = (RecyclerView.LayoutParams) mItemView.getLayoutParams();
        //get count of itemView
        int itemCount = getItemCount();
        int recyclerViewHeight = itemViewLp.height * itemCount;
        //set height of recyclerView
        LinearLayout.LayoutParams rvLp = (LinearLayout.LayoutParams) mRv.getLayoutParams();
        rvLp.height = recyclerViewHeight;
        mRv.setLayoutParams(rvLp);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        View itemView;
        TextView mTVName, mTvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            mTVName = itemView.findViewById(R.id.tv_name);
            mTvAuthor = itemView.findViewById(R.id.tv_author);
        }
    }
}
