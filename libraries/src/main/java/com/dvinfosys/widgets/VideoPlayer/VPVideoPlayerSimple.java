package com.dvinfosys.widgets.VideoPlayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.dvinfosys.widgets.R;

public class VPVideoPlayerSimple extends VPVideoPlayer {
    public VPVideoPlayerSimple(Context context) {
        super(context);
    }

    public VPVideoPlayerSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.vp_layout_base;
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        updateFullscreenButton();
        fullscreenButton.setVisibility(View.GONE);
    }

    private void updateStartImage() {
        if (currentState == CURRENT_STATE_PLAYING) {
            startButton.setImageResource(R.drawable.vp_click_pause_selector);
        } else if (currentState == CURRENT_STATE_ERROR) {
            startButton.setImageResource(R.drawable.vp_click_error_selector);
        } else {
            startButton.setImageResource(R.drawable.vp_click_play_selector);
        }
    }

    public void updateFullscreenButton() {
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            fullscreenButton.setImageResource(R.drawable.vp_shrink);
        } else {
            fullscreenButton.setImageResource(R.drawable.vp_enlarge);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fullscreen && currentState == CURRENT_STATE_NORMAL) {
            Toast.makeText(getContext(), "Play video first", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onClick(v);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            if (currentState == CURRENT_STATE_NORMAL) {
                Toast.makeText(getContext(), "Play video first", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        super.onProgressChanged(seekBar, progress, fromUser);
    }
}
