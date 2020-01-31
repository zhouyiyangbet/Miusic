package com.example.miusic.helpers;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MediaPlayerHelper {

    private static MediaPlayerHelper instance;

    private Context mContext;
    private MediaPlayer mMediaPlayer;
    private String mPath;
    private OnMediaPlayerHelperListener onMediaPlayerHelperListener;


    public void setOnMediaPlayerHelperListener(OnMediaPlayerHelperListener onMediaPlayerHelperListener) {
        this.onMediaPlayerHelperListener = onMediaPlayerHelperListener;
    }




    public static MediaPlayerHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelper(context);
                }
            }
        }
        return instance;
    }

    private MediaPlayerHelper(Context context) {
        mContext = context;
        mMediaPlayer = new MediaPlayer();
    }

    /**
     * 1. setPath: the dir path of the music to play
     * 2. start: play music
     * 3. pause: pause the music
     */

    public void setPath(String path) {
        /**
         * 1.music playing, reset the status of music
         * 2.set the path of the music
         * 3.prepare to play
         */
        //step1, is playing or switch song will reset the player
        if (mMediaPlayer.isPlaying() || !path.equals(mPath)) {
            mMediaPlayer.reset();
        }
        mPath = path;
        //step2
        try {
            mMediaPlayer.setDataSource(mContext, Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //step3
        mMediaPlayer.prepareAsync();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onPrepared(mp);
                }
            }
        });

        //listener on status of music
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (onMediaPlayerHelperListener != null) {
                    onMediaPlayerHelperListener.onCompletion(mp);
                }
            }
        });
    }

    public String getPath() {
        return mPath;
    }

    public void start() {
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public interface OnMediaPlayerHelperListener {
        void onPrepared(MediaPlayer mp);
        void onCompletion(MediaPlayer mp);
    }
}
