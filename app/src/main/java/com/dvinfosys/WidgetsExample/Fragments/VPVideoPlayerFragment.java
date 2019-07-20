package com.dvinfosys.WidgetsExample.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayer;
import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayerStandard;

public class VPVideoPlayerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vpvideo_player, container, false);
        VPVideoPlayerStandard videoPlayerStandard = view.findViewById(R.id.vp_videoplayer);
        videoPlayerStandard.setUp("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", VPVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "Elephant Dream");
        videoPlayerStandard.thumbImageView.setImageURI(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"));
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        VPVideoPlayer.releaseAllVideos();
    }
}
