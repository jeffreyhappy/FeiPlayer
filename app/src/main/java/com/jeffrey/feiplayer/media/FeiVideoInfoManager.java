package com.jeffrey.feiplayer.media;

import android.view.ViewGroup;

/**
 * 为了实现全屏而存储一些数据
 * Created by Li on 2017/11/28.
 */

public class FeiVideoInfoManager {
    static FeiVideoInfoManager manager;
    private ViewGroup mOriginParentView;
    private ViewGroup.LayoutParams mOriginLP;
    private boolean isFullScreen;
    private FeiVideoView mPlayView;
    private int listPosition; //给列表用的

    public static FeiVideoInfoManager getInstance(){
        if (manager == null){
            synchronized (FeiVideoInfoManager.class){
                if (manager == null){
                    manager = new FeiVideoInfoManager();
                }
            }
        }
        return manager;
    }

    public ViewGroup getOriginParentView() {
        return mOriginParentView;
    }

    public void setOriginParentView(ViewGroup mOriginParentView) {
        this.mOriginParentView = mOriginParentView;
    }

    public ViewGroup.LayoutParams getOriginLP() {
        return mOriginLP;
    }

    public void setOriginLP(ViewGroup.LayoutParams mOriginLP) {
        this.mOriginLP = mOriginLP;
    }

    public boolean getFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    public FeiVideoView getPlayView() {
        return mPlayView;
    }

    public void setPlayView(FeiVideoView playView) {
        this.mPlayView = playView;
    }

    public int getListPosition() {
        return listPosition;
    }

    public void setListPosition(int listPosition) {
        this.listPosition = listPosition;
    }
}
