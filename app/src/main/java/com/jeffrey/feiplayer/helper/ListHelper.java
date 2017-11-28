package com.jeffrey.feiplayer.helper;

import com.jeffrey.feiplayer.media.FeiVideoView;

import java.util.ArrayList;

/**
 * Created by Li on 2017/11/28.
 */

public class ListHelper {
    private static ListHelper helper;
    private FeiVideoView currentShowView;
    private ListHelper(){

    }

    public static ListHelper getInstance(){
        if (helper == null){
            synchronized (ListHelper.class){
                if (helper == null){
                    helper = new ListHelper();
                }
            }
        }
        return helper;
    }


    public FeiVideoView getCurrentShowView() {
        return currentShowView;
    }

    public void setCurrentShowView(FeiVideoView currentShowView) {
        this.currentShowView = currentShowView;
    }
}
