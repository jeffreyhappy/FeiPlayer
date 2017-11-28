package com.jeffrey.feiplayer.media;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jeffrey.feiplayer.R;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by Li on 2017/11/24.
 */

public class FeiVideoView extends FrameLayout {
    IjkVideoView ijkVideoView;
    ProgressBar mLoadingView;
    FeiVideoControllerView controllerView;
//    FeiVideoInfoManager videoInfoManager;
    private int initWidth;
    private int initHeight;
    private FeiVideoControllerView.ClickFullListener clickFullListener;


    public FeiVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public FeiVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FeiVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public void init() {
        Log.d("fei","FeiVideoView init");
//        videoInfoManager = new FeiVideoInfoManager();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_fei_video_view,this,false);
        addView(view);
        controllerView = new FeiVideoControllerView(getContext(),(ViewGroup)view);
        controllerView.setClickFullListener(new FeiVideoControllerView.ClickFullListener() {
            @Override
            public void onClickFull() {
                showFull(getContext(),true);
                if (clickFullListener != null){
                    clickFullListener.onClickFull();
                }
            }
        });

        ijkVideoView = (IjkVideoView) view.findViewById(R.id.video_view);
        ijkVideoView.setMediaController(controllerView);
        ijkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                controllerView.show();
                Log.d("fei","onPrepared start");
                initWidth = getWidth();
                initHeight = getHeight();

            }
        });
        ijkVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                Log.d("fei","onInfo what = " + what);
                if (what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED||what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    showLoading();
                } else if (what == IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START||what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    hideLoading();
                }
                return false;
            }
        });
        ijkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {
                Toast.makeText(getContext(),"看完了", Toast.LENGTH_SHORT).show();
            }
        });
        mLoadingView = (ProgressBar) view.findViewById(R.id.loading);

    }



    public void setVideoPath(String path){
        ijkVideoView.setVideoPath(path);
    }

    public void start() {
        ijkVideoView.start();

    }




    public void stopPlayback(){
        ijkVideoView.stopPlayback();
    }

    public void release(boolean release){
        ijkVideoView.release(release);
    }

    public void stopBackgroundPlay(){
        ijkVideoView.stopBackgroundPlay();
    }

    private void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        //在全屏的模式下，才随着屏幕的变化而变化
        if (getVideoInfoManager().getFullScreen()){
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                getLayoutParams().width = initWidth;
                getLayoutParams().height = initHeight;
            } else {
                getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
            }
        }


    }

    public void setClickFullListener(FeiVideoControllerView.ClickFullListener clickFullListener) {
        this.clickFullListener = clickFullListener;
    }

    //    public static void startFullscreen(Context context, Class _class, String url,VideoInfos infos) {
////        JZUtils.setRequestedOrientation(JZUtils.scanForActivity(context), FULLSCREEN_ORIENTATION);
//        ViewGroup vp = (ViewGroup) (JZUtils.scanForActivity(context))//.getWindow().getDecorView();
//                .findViewById(Window.ID_ANDROID_CONTENT);
//        View old = vp.findViewById(R.id.jz_fullscreen_id);
//        if (old != null) {
//            vp.removeView(old);
//        }
//        try {
//            Constructor<TestVideoView> constructor = _class.getConstructor(Context.class,VideoInfos.class);
//            final TestVideoView jzVideoPlayer = constructor.newInstance(context,infos);
//            jzVideoPlayer.setId(R.id.jz_fullscreen_id);
//            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            vp.addView(jzVideoPlayer, lp);
//            jzVideoPlayer.setVideoPath(url);
//            jzVideoPlayer.start();
////            final Animation ra = AnimationUtils.loadAnimation(context, R.anim.start_fullscreen);
////            jzVideoPlayer.setAnimation(ra);
////            jzVideoPlayer.setUp(dataSourceObjects, defaultUrlMapIndex, JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, objects);
////            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
////            jzVideoPlayer.startButton.performClick();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public FeiVideoInfoManager getVideoInfoManager() {
        return FeiVideoInfoManager.getInstance();
    }


    private void showFull(Context context,Boolean showFull){
//        if (videoView == null){
//            return;
//        }
        ViewGroup fullScreen = findFullView(context);
        if (showFull){
            ViewGroup viewGroup = (ViewGroup) this.getParent();
            if (viewGroup == null){
                return;
            }
            getVideoInfoManager().setOriginParentView(viewGroup);
            getVideoInfoManager().setFullScreen(true);
            getVideoInfoManager().setOriginLP(getLayoutParams());
            getVideoInfoManager().setPlayView(this);
//            viewGroup.addView(new FrameLayout(getContext()),getLayoutParams());
            viewGroup.removeView(this);
            //放一个view来站着位置，要不然listview会跑偏
            fullScreen.addView(this);
            fullScreen.setVisibility(View.VISIBLE);
            this.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            scanForActivity(context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            int mHideFlags =
                    View.SYSTEM_UI_FLAG_LOW_PROFILE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            fullScreen.setSystemUiVisibility(mHideFlags);
        }else {
            fullScreen.setVisibility(View.GONE);
            fullScreen.removeAllViews();
            ViewGroup originGroupView = getVideoInfoManager().getOriginParentView();
            ViewGroup.LayoutParams originLP= getVideoInfoManager().getOriginLP();
//            originGroupView.removeAllViews();
            originGroupView.addView(this,originLP);
            getVideoInfoManager().setFullScreen(false);
            getVideoInfoManager().setOriginLP(null);
            getVideoInfoManager().setOriginParentView(null);
            getVideoInfoManager().setPlayView(null);
            scanForActivity(context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
    }

    ViewGroup findFullView(Context context){
        ViewGroup vp = (ViewGroup) (scanForActivity(context))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        FrameLayout old = (FrameLayout) vp.findViewById(R.id.jz_fullscreen_id);
        if (old != null) {
            vp.removeView(old);
            return old;
        }
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Color.BLACK);
        frameLayout.setId(R.id.jz_fullscreen_id);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        vp.addView(frameLayout, lp);
        return frameLayout;
    }

    public static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }


    public boolean handleBack(){
        if (getVideoInfoManager().getFullScreen()){
            showFull(getContext(),false);
            return  true;
        }else {
            return false;
        }
    }
}
