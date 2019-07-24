package com.dvinfosys.widgets.Animation;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DotsAnimDrawable extends BaseAnimDrawable {

    public static final int DEFAULT_DOTS_COUNT = 3;
    public static final int DEFAULT_LOOP_DURATION = 600;
    public static final int DEFAULT_JUMP_DURATION = 400;
    private final RectF fBounds = new RectF();
    private ValueAnimator mValueAnimator;
    private Paint mPaint;
    private View mAnimatedView;

    private boolean mRunning;

    private int mLoopDuration;

    private int mJumpDuration;
    private int mJumpHeight;

    private int mJumpHalfTime;

    private List<Dot> mDots;

    public DotsAnimDrawable(View view, int color) {
        this(view, color, DEFAULT_DOTS_COUNT, DEFAULT_LOOP_DURATION, DEFAULT_JUMP_DURATION);
    }

    public DotsAnimDrawable(View view, int color, int count, int loopDuration, int jumpDuration) {
        this.mLoopDuration = loopDuration;
        this.mJumpDuration = jumpDuration;

        mAnimatedView = view;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

        mDots = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            mDots.add(new Dot());
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        fBounds.left = bounds.left;
        fBounds.right = bounds.right;
        fBounds.top = bounds.top;
        fBounds.bottom = bounds.bottom;

        setupAnimations();
    }

    @Override
    public void start() {
        if (isRunning()) {
            return;
        }
        mRunning = true;
        mValueAnimator.start();
    }

    @Override
    public void stop() {
        if (!isRunning()) {
            return;
        }
        mRunning = false;
        mValueAnimator.end();
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        for (Dot dot : mDots) {
            dot.draw(canvas);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public void setupAnimations() {
        int count = mDots.size();
        int startOffset = (mLoopDuration - mJumpDuration) / (count - 1);

        mJumpHalfTime = mJumpDuration / 2;

        float mDotSize = (fBounds.width() / count);

        for (int i = 0; i < count; i++) {
            Dot dot = mDots.get(i);
            dot.x = fBounds.left + mDotSize / 2 + (i * mDotSize);
            dot.y = fBounds.bottom - mDotSize / 2;
            dot.radius = (mDotSize / 2) - (mDotSize / 8);
            dot.paint = new Paint(mPaint);

            int startTime = startOffset * i;
            dot.startTime = startTime;
            dot.jumpUpEndTime = startTime + mJumpHalfTime;
            dot.jumpDownEndTime = startTime + mJumpDuration;
        }

        mJumpHeight = (int) (fBounds.height() - mDotSize / 2);

        mValueAnimator = ValueAnimator.ofInt(0, mLoopDuration);
        mValueAnimator.setDuration(mLoopDuration);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int animationValue = (int) animation.getAnimatedValue();

                for (int i = 0; i < mDots.size(); i++) {
                    Dot dot = mDots.get(i);
                    int dotStartTime = dot.startTime;
                    float animationFactor = 0f;
                    if (animationValue >= dotStartTime) {
                        if (animationValue < dot.jumpUpEndTime) {
                            // Animate jump up
                            animationFactor = (float) (animationValue - dotStartTime) / mJumpHalfTime;
                        } else if (animationValue < dot.jumpDownEndTime) {
                            // Animate jump down
                            animationFactor = 1 - ((float) (animationValue - dotStartTime - mJumpHalfTime) / (mJumpHalfTime));
                        }
                    }

                    float translationY = (mJumpHeight) * animationFactor;
                    dot.y = fBounds.bottom - dot.radius - translationY;
                    mAnimatedView.invalidate();
                }
            }
        });
    }

    @Override
    public void dispose() {
        if (mValueAnimator != null) {
            mValueAnimator.end();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.cancel();
        }
        mValueAnimator = null;
    }

    private class Dot {
        float x;
        float y;
        Paint paint;
        float radius;
        int startTime;
        int jumpUpEndTime;
        int jumpDownEndTime;

        void draw(Canvas canvas) {
            canvas.drawCircle(x, y, radius, paint);
        }
    }
}
