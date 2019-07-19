package com.dvinfosys.widgets.ProgressView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.Utils.Constants;

public class HorizontalProgressView extends View {
    public static final int ACCELERATE_DECELERATE_INTERPOLATOR = 0;
    public static final int LINEAR_INTERPOLATOR = 1;
    public static final int ACCELERATE_INTERPOLATOR = 2;
    public static final int DECELERATE_INTERPOLATOR = 3;
    public static final int OVERSHOOT_INTERPOLATOR = 4;
    private static final String TAG = "HorizontalProgressView";
    private Context mContext;
    private int mAnimateType = 0;
    private float mStartProgress = 0;
    private float mEndProgress = 60;
    private int mStartColor = getResources().getColor(R.color.light_orange);
    private int mEndColor = getResources().getColor(R.color.dark_orange);
    private boolean trackEnabled = false;
    private int mTrackWidth = 6;
    private int mProgressTextSize = 48;
    private int mProgressTextColor = getResources().getColor(R.color.colorAccent);
    private int mTrackColor = getResources().getColor(R.color.default_track_color);
    private int mProgressDuration = 1200;
    private boolean textVisibility = true;
    private int mCornerRadius = 30;
    private int mTextPaddingBottomOffset = 5;
    private boolean isTextMoved = true;
    private ObjectAnimator progressAnimator;
    private float moveProgress = 0;
    private Paint progressPaint;
    private LinearGradient mShader;
    private RectF mRect;
    private RectF mTrackRect;
    private Paint mTextPaint;
    private Interpolator mInterpolator;
    private HorizontalProgressUpdateListener animatorUpdateListener;
    public HorizontalProgressView(Context context) {
        super(context);
        this.mContext = context;
        obtainAttrs(context, null);
        init();
    }

    public HorizontalProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        obtainAttrs(context, attrs);
        init();
    }

    public HorizontalProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        obtainAttrs(context, attrs);
        init();
    }

    private void obtainAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgressView);

        mStartProgress = typedArray.getInt(R.styleable.HorizontalProgressView_start_progress, 0);
        mEndProgress = typedArray.getInt(R.styleable.HorizontalProgressView_end_progress, 60);
        mStartColor = typedArray.getColor(R.styleable.HorizontalProgressView_start_color, getResources().getColor(R.color.light_orange));
        mEndColor = typedArray.getColor(R.styleable.HorizontalProgressView_end_color, getResources().getColor(R.color.dark_orange));
        trackEnabled = typedArray.getBoolean(R.styleable.HorizontalProgressView_isTracked, false);
        mProgressTextColor = typedArray.getColor(R.styleable.HorizontalProgressView_progressTextColor, getResources().getColor(R.color.colorAccent));
        mProgressTextSize = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressView_progressTextSize, getResources().getDimensionPixelSize(R.dimen.default_horizontal_text_size));
        mTrackWidth = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressView_track_width, getResources().getDimensionPixelSize(R.dimen.default_trace_width));
        mAnimateType = typedArray.getInt(R.styleable.HorizontalProgressView_animateType, ACCELERATE_DECELERATE_INTERPOLATOR);
        mTrackColor = typedArray.getColor(R.styleable.HorizontalProgressView_trackColor, getResources().getColor(R.color.default_track_color));
        textVisibility = typedArray.getBoolean(R.styleable.HorizontalProgressView_progressTextVisibility, true);
        mProgressDuration = typedArray.getInt(R.styleable.HorizontalProgressView_progressDuration, 1200);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressView_corner_radius, getResources().getDimensionPixelSize(R.dimen.default_corner_radius));
        mTextPaddingBottomOffset = typedArray.getDimensionPixelSize(R.styleable.HorizontalProgressView_text_padding_bottom, getResources().getDimensionPixelSize(R.dimen.default_corner_radius));
        isTextMoved = typedArray.getBoolean(R.styleable.HorizontalProgressView_textMovedEnable, true);
        Log.e(TAG, "progressDuration: " + mProgressDuration);

        typedArray.recycle();
        moveProgress = mStartProgress;
    }

    private void init() {
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updateTheTrack();
        drawTrack(canvas);
        progressPaint.setShader(mShader);
        canvas.drawRoundRect(mRect, mCornerRadius, mCornerRadius, progressPaint);
        drawProgressText(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mShader = new LinearGradient(getPaddingLeft() - 50, (getHeight() - getPaddingTop()) - 50, getWidth() - getPaddingRight(), getHeight() / 2 + getPaddingTop() + mTrackWidth,
                mStartColor, mEndColor, Shader.TileMode.CLAMP);


    }

    private void drawTrack(Canvas canvas) {
        if (trackEnabled) {
            progressPaint.setShader(null);
            progressPaint.setColor(mTrackColor);
            canvas.drawRoundRect(mTrackRect, mCornerRadius, mCornerRadius, progressPaint);

        }
    }

    private void drawProgressText(Canvas canvas) {

        if (textVisibility) {
            mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setStyle(Paint.Style.FILL);
            mTextPaint.setTextSize(mProgressTextSize);
            mTextPaint.setColor(mProgressTextColor);
            mTextPaint.setTextAlign(Paint.Align.CENTER);

            String progressText = ((int) moveProgress) + "%";
            if (isTextMoved) {
                canvas.drawText(progressText,
                        (getWidth() - getPaddingLeft() - getPaddingRight() - Constants.dp2px(mContext, 28)) * (moveProgress / 100) + Constants.dp2px(mContext, 10),
                        getHeight() / 2 - getPaddingTop() - mTextPaddingBottomOffset, mTextPaint);
            } else {
                canvas.drawText(progressText, (getWidth() - getPaddingLeft()) / 2, getHeight() / 2 - getPaddingTop() - mTextPaddingBottomOffset, mTextPaint);
            }
        }

    }

    public void setAnimateType(@AnimateType int type) {
        this.mAnimateType = type;
        setObjectAnimatorType(type);
    }

    private void setObjectAnimatorType(int animatorType) {
        Log.e(TAG, "AnimatorType>>>>>>" + animatorType);
        switch (animatorType) {
            case ACCELERATE_DECELERATE_INTERPOLATOR:

                if (mInterpolator != null) {
                    mInterpolator = null;
                }

                mInterpolator = new AccelerateDecelerateInterpolator();
                break;

            case LINEAR_INTERPOLATOR:

                if (mInterpolator != null) {
                    mInterpolator = null;
                }
                mInterpolator = new LinearInterpolator();

                break;

            case ACCELERATE_INTERPOLATOR:

                if (mInterpolator != null) {
                    mInterpolator = null;
                    mInterpolator = new AccelerateInterpolator();
                }

                break;

            case DECELERATE_INTERPOLATOR:

                if (mInterpolator != null) {
                    mInterpolator = null;
                }
                mInterpolator = new DecelerateInterpolator();

                break;

            case OVERSHOOT_INTERPOLATOR:

                if (mInterpolator != null) {
                    mInterpolator = null;
                }
                mInterpolator = new OvershootInterpolator();

                break;
        }
    }

    public float getProgress() {
        return this.moveProgress;
    }

    public void setProgress(float progress) {
        this.moveProgress = progress;
        refreshTheView();
    }

    public void setStartProgress(float startProgress) {
        if (startProgress < 0 || startProgress > 100) {
            throw new IllegalArgumentException("Illegal progress value, please change it!");
        }
        this.mStartProgress = startProgress;
        this.moveProgress = mStartProgress;
        refreshTheView();
    }

    public void setEndProgress(float endProgress) {
        if (endProgress < 0 || endProgress > 100) {
            throw new IllegalArgumentException("Illegal progress value, please change it!");
        }
        this.mEndProgress = endProgress;
        refreshTheView();

    }

    public void setStartColor(@ColorInt int startColor) {
        this.mStartColor = startColor;
        mShader = new LinearGradient(getPaddingLeft() - 50, (getHeight() - getPaddingTop()) - 50, getWidth() - getPaddingRight(), getHeight() / 2 + getPaddingTop() + mTrackWidth,
                mStartColor, mEndColor, Shader.TileMode.CLAMP);
        refreshTheView();
    }

    public void setEndColor(@ColorInt int endColor) {
        this.mEndColor = endColor;
        mShader = new LinearGradient(getPaddingLeft() - 50, (getHeight() - getPaddingTop()) - 50, getWidth() - getPaddingRight(), getHeight() / 2 + getPaddingTop() + mTrackWidth,
                mStartColor, mEndColor, Shader.TileMode.CLAMP);
        refreshTheView();
    }

    public void setTrackWidth(int width) {
        this.mTrackWidth = Constants.dp2px(mContext, width);
        refreshTheView();
    }

    public void setTrackColor(@ColorInt int color) {
        this.mTrackColor = color;
        refreshTheView();
    }

    public void setProgressTextColor(@ColorInt int textColor) {
        this.mProgressTextColor = textColor;
    }

    public void setProgressTextSize(int size) {
        mProgressTextSize = (int) Constants.sp2px(mContext, size);
        refreshTheView();
    }

    public void setProgressDuration(int duration) {
        this.mProgressDuration = duration;
    }

    public void setTrackEnabled(boolean trackAble) {
        this.trackEnabled = trackAble;
        refreshTheView();
    }

    public void setProgressTextVisibility(boolean visibility) {
        this.textVisibility = visibility;
        refreshTheView();
    }

    public void setProgressTextMoved(boolean moved) {
        this.isTextMoved = moved;
    }

    public void startProgressAnimation() {
        progressAnimator = ObjectAnimator.ofFloat(this, "progress", mStartProgress, mEndProgress);
        Log.e(TAG, "progressDuration: " + mProgressDuration);
        progressAnimator.setInterpolator(mInterpolator);
        progressAnimator.setDuration(mProgressDuration);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue("progress");
                if (animatorUpdateListener != null) {
                    animatorUpdateListener.onHorizontalProgressUpdate(HorizontalProgressView.this, progress);
                }

            }

        });
        progressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (animatorUpdateListener != null) {
                    animatorUpdateListener.onHorizontalProgressStart(HorizontalProgressView.this);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (animatorUpdateListener != null) {
                    animatorUpdateListener.onHorizontalProgressFinished(HorizontalProgressView.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        progressAnimator.start();
    }

    public void stopProgressAnimation() {
        if (progressAnimator != null) {
            progressAnimator.cancel();
            progressAnimator = null;
        }
    }

    public void setProgressCornerRadius(int radius) {
        this.mCornerRadius = Constants.dp2px(mContext, radius);
        refreshTheView();
    }

    public void setProgressTextPaddingBottom(int offset) {
        this.mTextPaddingBottomOffset = Constants.dp2px(mContext, offset);
    }

    private void refreshTheView() {
        invalidate();
        //requestLayout();
    }

    private void updateTheTrack() {
        mRect = new RectF(getPaddingLeft() + mStartProgress * (getWidth() - getPaddingLeft() - getPaddingRight() + 60) / 100, getHeight() / 2 - getPaddingTop(),
                (getWidth() - getPaddingRight() - 20) * ((moveProgress) / 100),
                getHeight() / 2 + getPaddingTop() + mTrackWidth);
        mTrackRect = new RectF(getPaddingLeft(), getHeight() / 2 - getPaddingTop(),
                (getWidth() - getPaddingRight() - 20),
                getHeight() / 2 + getPaddingTop() + mTrackWidth);
    }

    public void setProgressViewUpdateListener(HorizontalProgressUpdateListener listener) {
        this.animatorUpdateListener = listener;
    }

    @IntDef({ACCELERATE_DECELERATE_INTERPOLATOR, LINEAR_INTERPOLATOR, ACCELERATE_INTERPOLATOR, DECELERATE_INTERPOLATOR, OVERSHOOT_INTERPOLATOR})
    private @interface AnimateType {
    }

    public interface HorizontalProgressUpdateListener {
        void onHorizontalProgressStart(View view);

        void onHorizontalProgressUpdate(View view, float progress);

        void onHorizontalProgressFinished(View view);

    }

}
