package com.example.miusic.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.example.miusic.R;
import com.example.miusic.models.MusicModel;
import com.example.miusic.services.MusicService;

public class PlayMusicView extends FrameLayout {

    private Context mContext;
    private Intent mServiceIntent;
    private MusicService.MusicBind mMusicBind;
    private MusicModel mMusicModel;

    private View mView;
    private boolean isPlaying, isBind;
    private ImageView mIvIcon, mIvNeedle, mIvPlay;
    private Animation mPlayMusicAnim, mPlayNeedleAnim, mStopNeedleAnim;
    private FrameLayout mFlPlayMusic;


    public PlayMusicView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlayMusicView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.play_music, this, false);
        mFlPlayMusic = mView.findViewById(R.id.fl_play_music);
        mIvNeedle = mView.findViewById(R.id.iv_needle);
        mFlPlayMusic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger();
            }
        });
        mIvIcon = mView.findViewById(R.id.iv_icon);
        mIvPlay = mView.findViewById(R.id.iv_play);

        /**
         * 1. define the anim: 1. the rotation of the disc 2. the point anim 3. leave anim
         * 2. startAnimation to execute the anim
         */
        mPlayMusicAnim = AnimationUtils.loadAnimation(mContext,R.anim.play_music_anim);
        mPlayNeedleAnim = AnimationUtils.loadAnimation(mContext,R.anim.play_needle_anim);
        mStopNeedleAnim = AnimationUtils.loadAnimation(mContext,R.anim.stop_needle_anim);

        addView(mView);

    }

    /**
     * switch the status
     */
    private void trigger() {
        if (isPlaying) {
            stopMusic();
        }
        else {
            playMusic();
        }
    }

    /**
     * play music
     */
    public void playMusic() {
        isPlaying = true;
        mIvPlay.setVisibility(View.GONE);
        mFlPlayMusic.startAnimation(mPlayMusicAnim);
        mIvNeedle.startAnimation(mPlayNeedleAnim);

        startMusicService();
    }

    /**
     * stop the music
     */
    public void stopMusic() {
        isPlaying = false;
        mIvPlay.setVisibility(View.VISIBLE);
        mFlPlayMusic.clearAnimation();
        mIvNeedle.startAnimation(mStopNeedleAnim);

        mMusicBind.stopMusic();


    }

    /**
     * set the music picture of disc
     */
    public void setMusicIcon() {
        Glide.with(mContext)
                .load(mMusicModel.getPoster())
                .into(mIvIcon);
    }

    public void setMusic(MusicModel music) {
        mMusicModel = music;
        setMusicIcon();
    }

    /**
     * start music service
     */
    private void startMusicService() {
        //start the service;
        if (mServiceIntent == null) {
            mServiceIntent = new Intent(mContext, MusicService.class);
            mContext.startService(mServiceIntent);
        } else {
            mMusicBind.playMusic();
        }
        //bind the service
        if (!isBind) {
            isBind = true;
            mContext.bindService(mServiceIntent, conn, Context.BIND_AUTO_CREATE);
        }

    }

    /**
     * unbind
     */
    public void destroy() {
        if (isBind) {
            isBind = false;
            mContext.unbindService(conn);
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBind = (MusicService.MusicBind) service;
            mMusicBind.setMusic(mMusicModel);
            mMusicBind.playMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
