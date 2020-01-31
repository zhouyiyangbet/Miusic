package com.example.miusic.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.example.miusic.R;
import com.example.miusic.activities.WelcomeActivity;
import com.example.miusic.helpers.MediaPlayerHelper;
import com.example.miusic.models.MusicModel;

/**
 * 1.connect to PlayMusicView and MediaPlayHelper with service
 */
public class MusicService extends Service {

    //cannot be 0
    public static final int NOTIFICATION_ID = 1;

    private MediaPlayerHelper mMediaPlayerHelper;
    private MusicModel mMusicModel;

    public MusicService() {

    }

    public class MusicBind extends Binder {
        /**
         * set the musicModel
         *
         */
        public void setMusic(MusicModel musicModel) {
            mMusicModel = musicModel;
            startForeground();
        }

        /**
         * play music
         */
        public void playMusic() {
            if (mMediaPlayerHelper.getPath() != null &&
                    mMediaPlayerHelper.getPath().equals(mMusicModel.getPath())) {
                mMediaPlayerHelper.start();
            } else {
                mMediaPlayerHelper.setPath(mMusicModel.getPath());
                mMediaPlayerHelper.setOnMediaPlayerHelperListener(new MediaPlayerHelper.OnMediaPlayerHelperListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayerHelper.start();
                    }

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopSelf();
                    }
                });
            }
        }

        /**
         * pause music
         */
        public void stopMusic() {
            mMediaPlayerHelper.pause();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBind();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayerHelper = MediaPlayerHelper.getInstance(this);
    }

    /**
     * set the service to be visible
     */

    private void startForeground() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, WelcomeActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
        /**
         * build a notification
         */
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = createNotificationChannel();
            notification = new Notification.Builder(this, channel.getId())
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        } else {
            notification = new Notification.Builder(this)
                    .setContentTitle(mMusicModel.getName())
                    .setContentText(mMusicModel.getAuthor())
                    .setSmallIcon(R.mipmap.welcome)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        /**
         * set the notification to be shown
         */
        startForeground(NOTIFICATION_ID, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createNotificationChannel () {
        String channelId = "miusic";
        String channelName = "MiusicService";
        String Description = "Miusic";
        NotificationChannel channel = new NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(Description);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        channel.setShowBadge(false);

        return channel;

    }
}
