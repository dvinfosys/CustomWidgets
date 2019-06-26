package com.dvinfosys.WidgetsExample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dvinfosys.widgets.ToastView.ToastView;
import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayer;
import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayerStandard;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button btnErrorToastView, btnSuccessToastView, btnInfoToastView, btnWarringToastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        btnErrorToastView = findViewById(R.id.button_error_toast);
        btnSuccessToastView = findViewById(R.id.button_success_toast);
        btnInfoToastView = findViewById(R.id.button_info_toast);
        btnWarringToastView = findViewById(R.id.button_warning_toast);

        VPVideoPlayerStandard videoPlayerStandard = findViewById(R.id.vp_videoplayer);
        videoPlayerStandard.setUp("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4", VPVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "Elephant Dream");
        videoPlayerStandard.thumbImageView.setImageURI(Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/ElephantsDream.jpg"));

        btnErrorToastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.error(context,"This is error ToastView",ToastView.LENGTH_SHORT).show();
            }
        });
        btnWarringToastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.warning(context,"This is warring ToastView",ToastView.LENGTH_SHORT).show();
            }
        });
        btnInfoToastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.info(context,"This is info ToastView",ToastView.LENGTH_SHORT).show();
            }
        });

        btnSuccessToastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.success(context,"This is success ToastView",ToastView.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (VPVideoPlayer.backPress()) {
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
