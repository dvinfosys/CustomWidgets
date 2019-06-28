package com.dvinfosys.WidgetsExample;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;

import com.dvinfosys.widgets.ToastView.ToastView;
import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayer;
import com.dvinfosys.widgets.VideoPlayer.VPVideoPlayerStandard;

import static android.graphics.Typeface.BOLD_ITALIC;

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

        findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastView.normal(MainActivity.this, "normal message without icon").show();
            }
        });
        findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable icon = getResources().getDrawable(R.drawable.menu);
                ToastView.normal(MainActivity.this, "normal message with icon", icon).show();
            }
        });
        findViewById(R.id.button_info_toast_with_formatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastView.info(MainActivity.this, getFormattedMessage()).show();
            }
        });
        findViewById(R.id.button_custom_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastView.Config.getInstance()
                        .setToastTypeface(Typeface.createFromAsset(getAssets(), "fonts/Smoothy.otf"))
                        .allowQueue(false)
                        .apply();
                ToastView.custom(MainActivity.this, R.string.custom_message, getResources().getDrawable(R.drawable.menu),
                        android.R.color.black, android.R.color.holo_green_light, ToastView.LENGTH_SHORT, true, true).show();
                ToastView.Config.reset(); // Use this if you want to use the configuration above only once
            }
        });
    }

    private CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
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
