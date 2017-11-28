package com.jeffrey.feiplayer.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeffrey.feiplayer.R;
import com.jeffrey.feiplayer.activity.SimpleActivity;
import com.jeffrey.feiplayer.media.FeiVideoControllerView;
import com.jeffrey.feiplayer.media.FeiVideoInfoManager;
import com.jeffrey.feiplayer.media.FeiVideoView;

/**
 * Created by Li on 2017/11/28.
 */

public class SimpleVideoAdapter extends RecyclerView.Adapter<SimpleVideoAdapter.VH> {


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_video,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.feiVideoView.setVideoPath(SimpleActivity.VIDEO_PATH);
//        holder.feiVideoView.stopPlayback();
        holder.feiVideoView.setClickFullListener(new FeiVideoControllerView.ClickFullListener() {
            @Override
            public void onClickFull() {
                FeiVideoInfoManager.getInstance().setListPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public static class VH extends RecyclerView.ViewHolder {

        FeiVideoView feiVideoView;
        public VH(View itemView) {
            super(itemView);
            feiVideoView = itemView.findViewById(R.id.video_view);
        }
    }
}
