package com.dvinfosys.widgets.NumberCounter;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class RepeatListener implements View.OnTouchListener {

    public interface ReleaseCallback {
        void onRelease();
    }

    private Handler handler = new Handler();

    private int initialInterval = 400;
    private int normalInterval = 100;
    private final OnClickListener clickListener;
    private final ReleaseCallback releaseCallback;

    private Runnable handlerRunnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, normalInterval);
            clickListener.onClick(downView);
        }
    };

    private View downView;

    public RepeatListener(int initialInterval, int normalInterval,
                          OnClickListener clickListener, ReleaseCallback releaseCallback) {
        if (clickListener == null)
            throw new IllegalArgumentException("Null Runnable");
        if (initialInterval < 0 || normalInterval < 0)
            throw new IllegalArgumentException("Negative interval is invalid!");

        this.initialInterval = initialInterval;
        this.normalInterval = normalInterval;
        this.clickListener = clickListener;
        this.releaseCallback = releaseCallback;
    }
    RepeatListener(OnClickListener clickListener, ReleaseCallback releaseCallback) {
        if (clickListener == null)
            throw new IllegalArgumentException("Null runnable");
        this.clickListener = clickListener;
        this.releaseCallback = releaseCallback;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(handlerRunnable);
                handler.postDelayed(handlerRunnable, initialInterval);
                view.setPressed(true);
                view.performClick();
                clickListener.onClick(view);
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacks(handlerRunnable);
                view.setPressed(false);
                if(releaseCallback != null) releaseCallback.onRelease();
                return true;
        }
        return false;
    }
}
