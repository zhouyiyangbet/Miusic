package com.example.miusic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miusic.R;
import com.example.miusic.activities.AlbumListActivity;
import com.example.miusic.models.AlbumModel;

import java.util.List;

public class MiusicGridAdapter extends RecyclerView.Adapter<MiusicGridAdapter.ViewHolder> {

    private Context mContext;
    private List<AlbumModel> mDataSource;

    public MiusicGridAdapter(Context context, List<AlbumModel>mDataSource) {
        this.mContext = context;
        this.mDataSource = mDataSource;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_grid_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final AlbumModel albumModel = mDataSource.get(i);
        Glide.with(mContext)
                .load(albumModel.getPoster())
                .into(holder.ivIcon);
        holder.mTvPlayNum.setText(albumModel.getPlayNum());
        holder.mTvName.setText(albumModel.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlbumListActivity.class);
                intent.putExtra(AlbumListActivity.ALBUM_ID, albumModel.getAlbumId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        View itemView;
        TextView mTvPlayNum, mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            ivIcon = itemView.findViewById(R.id.iv_icon);
            mTvPlayNum = itemView.findViewById(R.id.tv_play_num);
            mTvName = itemView.findViewById(R.id.tv_name);
        }
    }

}
