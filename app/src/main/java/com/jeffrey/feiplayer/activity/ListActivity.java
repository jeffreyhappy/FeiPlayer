package com.jeffrey.feiplayer.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jeffrey.feiplayer.R;
import com.jeffrey.feiplayer.adapter.SimpleVideoAdapter;
import com.jeffrey.feiplayer.media.FeiVideoInfoManager;
import com.jeffrey.feiplayer.media.FeiVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class ListActivity extends AppCompatActivity {

    RecyclerView rv;

    private FeiVideoView currentVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new SimpleVideoAdapter());

        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
//            }
//        },6000);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            if (currentVideoView != null){
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv.getLayoutManager();
                linearLayoutManager.scrollToPositionWithOffset(FeiVideoInfoManager.getInstance().getListPosition(),0);
//                testScrollBack();

            }
        }else {
            currentVideoView = FeiVideoInfoManager.getInstance().getPlayView();
        }
    }

    @Override
    public void onBackPressed() {
        FeiVideoInfoManager manager = FeiVideoInfoManager.getInstance();
        if (manager.getPlayView() != null){
            if (manager.getPlayView().handleBack()){
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FeiVideoInfoManager manager = FeiVideoInfoManager.getInstance();
        if (manager.getPlayView() != null){
            FeiVideoView feiVideoView = manager.getPlayView();
            feiVideoView.stopPlayback();
            feiVideoView.release(true);
            feiVideoView.stopBackgroundPlay();
            IjkMediaPlayer.native_profileEnd();
        }
    }

    private void testScrollBack(){
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv.getLayoutManager();
//                linearLayoutManager.scrollToPositionWithOffset(0,0);
//                Toast.makeText(ListActivity.this, "滚回去", Toast.LENGTH_SHORT).show();
//            }
//        },5000);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv.getLayoutManager();
        linearLayoutManager.scrollToPositionWithOffset(0,0);
    }
}
