package com.jeffrey.feiplayer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jeffrey.feiplayer.R;
import com.jeffrey.feiplayer.media.FeiVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class SimpleActivity extends AppCompatActivity {

    FeiVideoView mVideoView;
    public static final String VIDEO_PATH = "http://cdn9.video.checheng.com/flvs/FF91E122A113F07F/2017-05-24/57AD7568D620A06E-10.flv";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoView = findViewById(R.id.video_view);
        mVideoView.setVideoPath(VIDEO_PATH);
//        mVideoView.start();
    }




    @Override
    public void onBackPressed() {
        if (mVideoView.handleBack()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        mVideoView.release(true);
        mVideoView.stopBackgroundPlay();
        IjkMediaPlayer.native_profileEnd();
    }
}
