package com.bignews9527.vidio.video;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;


/**
 * Created by 1945374040 on 2016/11/21.
 */
public class MusicPlayerService extends Service {

    private MediaPlayer mediaPlayer;
    private String postId, lastPostId = "";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        postId = intent.getStringExtra("postid");
        Log.i("tmd", "onBind: "+postId+"    "+lastPostId);
        if (!postId.equals(lastPostId) && mediaPlayer!=null) {
            mediaPlayer.stop();
        }
        lastPostId = postId;
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new MyBinder();
    }

    class MyBinder extends Binder {

        public MediaPlayer getPlayer() {
            return mediaPlayer;
        }

        public MyBinder() {
            if (mediaPlayer == null) {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//                    @Override
//                    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
//
//
////                    seekBar.setSecondaryProgress(i);
////                    int currentprogress = seekBar.getMax()
////                            * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
//                    }
//                });
//                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            @Override
//                            public void onPrepared(MediaPlayer mediaPlayer) {
//
//                                mediaPlayer.start();
//                            }
//                });
            }
        }

        public void play(){
            mediaPlayer.start();

        }

        public void playUrl(String url){

            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void pause(){
            mediaPlayer.pause();
        }

        public void stop(){
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }
}
