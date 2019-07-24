package com.dvinfosys.widgets.Animation;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.View;

public class CustomAnimDrawable extends BaseAnimDrawable {
    private static final String TAG = "CustomAnimatedDrawable";

    private View view;
    @DrawableRes
    private int resource;
    @ColorInt
    private int color;
    private Rect bounds = new Rect();

    private boolean mRunning;

    private AnimatedVectorDrawableCompat animateDrawable;
    private Animatable2Compat.AnimationCallback callback = new Animatable2Compat.AnimationCallback() {

        @Override
        public void onAnimationEnd(final Drawable drawable) {
            Log.d(TAG, "onAnimationEnd");
            animateDrawable.start();
            view.invalidate();
        }
    };

    public CustomAnimDrawable(View view, @DrawableRes int resource, @ColorInt int color) {
        this.view = view;
        this.resource = resource;
        this.color = color;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.bounds = bounds;
        setupAnimations();
    }

    @Override
    public void start() {
        Log.d(TAG, "start");
        if (isRunning()) {
            return;
        }
        mRunning = true;
        animateDrawable.registerAnimationCallback(callback);
        animateDrawable.start();
    }

    @Override
    public void stop() {
        Log.d(TAG, "stop");
        if (!isRunning()) {
            return;
        }
        mRunning = false;
        animateDrawable.unregisterAnimationCallback(callback);
        animateDrawable.stop();
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        animateDrawable.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public void setupAnimations() {
        animateDrawable = AnimatedVectorDrawableCompat.create(view.getContext(), resource);
        DrawableCompat.setTint(animateDrawable, color);
        animateDrawable.setBounds(bounds);
    }

    @Override
    public void dispose() {
        animateDrawable = null;
    }
}
