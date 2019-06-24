package com.dvinfosys.WidgetsExample;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayer;
import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayerStandard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VPVideoPlayerStandard videoPlayerStandard = findViewById(R.id.vp_videoplayer);
        videoPlayerStandard.setUp("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",VPVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"Elephant Dream");
        videoPlayerStandard.thumbImageView.setImageURI(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"));
    }

    @Override
    public void onBackPressed() {
        if (VPVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        VPVideoPlayer.releaseAllVideos();
    }
}
