package com.dvinfosys.widgets.TextView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvinfosys.widgets.Animation.BaseAnimDrawable;
import com.dvinfosys.widgets.Animation.CircularAnimDrawable;
import com.dvinfosys.widgets.Animation.CircularRevealAnimDrawable;
import com.dvinfosys.widgets.Animation.CustomAnimDrawable;
import com.dvinfosys.widgets.Animation.DotsAnimDrawable;
import com.dvinfosys.widgets.R;

import static com.dvinfosys.widgets.TextView.RoundTextViewHelper.createDrawable;
import static com.dvinfosys.widgets.TextView.RoundTextViewHelper.manipulateColor;

public class RoundTextView extends AppCompatTextView {
    private BaseAnimDrawable progressDrawable;
    private CircularRevealAnimDrawable resultDrawable;
    private AnimatorSet morpSet;
    private RoundTextViewAnimationListener listener;
    private TextViewProperty property;
    private ResultState resultState = ResultState.NONE;
    private AnimationState animationState = AnimationState.IDLE;
    private AnimationProgressStyle animationProgressStyle = AnimationProgressStyle.CIRCLE;
    private int animationDurations;
    private int animationCornerRadius;

    private int animationProgressWidth;

    @ColorInt
    private int animationProgressColor;

    private int animationProgressPadding;

    private boolean animationAlpha;

    @DrawableRes
    private int animationCustomResource;

    @DrawableRes
    private int animationInnerResource;

    @DrawableRes
    private int animationInnerResourceColor;

    private int resultSuccessColor;

    private int resultSuccessResource;

    private int resultFailureColor;

    private int resultFailureResource;

    private int cornerRadius;
    /**
     * The width of the corner
     */
    private int cornerWidth;
    private int cornerColor;
    private int cornerColorPressed;
    private int cornerColorDisabled = -1;
    private int backgroundColor;
    private int backgroundColorPressed;
    private int backgroundColorDisabled = -1;
    private int textColor;
    private int textColorPressed;
    private int textColorDisabled = -1;

    public RoundTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RoundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public static RoundTextViewHelper.Builder newBuilder() {
        return new RoundTextViewHelper.Builder();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundTextView, defStyleAttr, 0);

        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundTextView_rb_corner_radius, 0);
        cornerWidth = a.getDimensionPixelSize(R.styleable.RoundTextView_rb_corner_width, 0);

        cornerColor = a.getColor(R.styleable.RoundTextView_rb_corner_color, Color.TRANSPARENT);
        cornerColorDisabled = a.getColor(R.styleable.RoundTextView_rb_corner_color_disabled, cornerColorDisabled);
        if (a.hasValue(R.styleable.RoundTextView_rb_corner_color_pressed))
            cornerColorPressed = a.getColor(R.styleable.RoundTextView_rb_corner_color_pressed, cornerColor);
        else
            cornerColorPressed = manipulateColor(cornerColor, .8f);

        backgroundColor = a.getColor(R.styleable.RoundTextView_rb_background_color, Color.TRANSPARENT);
        backgroundColorDisabled = a.getColor(R.styleable.RoundTextView_rb_background_color_disabled, backgroundColorDisabled);
        if (a.hasValue(R.styleable.RoundTextView_rb_background_color_pressed))
            backgroundColorPressed = a.getColor(R.styleable.RoundTextView_rb_background_color_pressed, backgroundColor);
        else
            backgroundColorPressed = manipulateColor(backgroundColor, .8f);

        textColor = a.getColor(R.styleable.RoundTextView_rb_text_color, Color.TRANSPARENT);
        textColorDisabled = a.getColor(R.styleable.RoundTextView_rb_text_color_disabled, textColorDisabled);
        if (a.hasValue(R.styleable.RoundTextView_rb_text_color_pressed))
            textColorPressed = a.getColor(R.styleable.RoundTextView_rb_text_color_pressed, textColor);
        else
            textColorPressed = manipulateColor(textColor, .8f);

        animationDurations = a.getInt(R.styleable.RoundTextView_rb_animation_duration, 300);
        animationCornerRadius = a.getDimensionPixelSize(R.styleable.RoundTextView_rb_animation_corner_radius, 0);
        animationAlpha = a.getBoolean(R.styleable.RoundTextView_rb_animation_alpha, false);
        animationProgressWidth = a.getDimensionPixelSize(R.styleable.RoundTextView_rb_animation_progress_width, 20);
        animationProgressColor = a.getColor(R.styleable.RoundTextView_rb_animation_progress_color, backgroundColor);
        animationProgressPadding = a.getDimensionPixelSize(R.styleable.RoundTextView_rb_animation_progress_padding, 10);
        if (a.hasValue(R.styleable.RoundTextView_rt_animation_progress_style)) {
            animationProgressStyle = AnimationProgressStyle.values()[a.getInt(R.styleable.RoundTextView_rt_animation_progress_style, 0)];
        }
        animationCustomResource = a.getResourceId(R.styleable.RoundTextView_rb_animation_custom_resource, 0);
        animationInnerResource = a.getResourceId(R.styleable.RoundTextView_rb_animation_inner_resource, 0);
        if (a.hasValue(R.styleable.RoundTextView_rb_animation_inner_resource_color)) {
            animationInnerResourceColor = a.getColor(R.styleable.RoundTextView_rb_animation_inner_resource_color, Color.BLACK);
        }

        resultSuccessColor = a.getColor(R.styleable.RoundTextView_rb_success_color, Color.GREEN);
        resultSuccessResource = a.getResourceId(R.styleable.RoundTextView_rb_success_resource, 0);
        resultFailureColor = a.getColor(R.styleable.RoundTextView_rb_failure_color, Color.RED);
        resultFailureResource = a.getResourceId(R.styleable.RoundTextView_rb_failure_resource, 0);

        a.recycle();

        update();
    }

    private void update() {
        StateListDrawable background = new StateListDrawable();
        background.addState(new int[]{android.R.attr.state_pressed}, createDrawable(
                backgroundColorPressed, cornerColorPressed, cornerWidth, cornerRadius));

        if (backgroundColorDisabled != -1)
            background.addState(new int[]{-android.R.attr.state_enabled}, createDrawable(
                    backgroundColorDisabled, cornerColorDisabled, cornerWidth, cornerRadius));

        background.addState(StateSet.WILD_CARD, createDrawable(
                backgroundColor, cornerColor, cornerWidth, cornerRadius));
        setBackground(background);

        setTextColor(textColorDisabled != -1 ?
                new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_pressed},
                                new int[]{-android.R.attr.state_enabled},
                                new int[]{}
                        },
                        new int[]{
                                textColorPressed,
                                textColorDisabled,
                                textColor
                        }) :
                new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_pressed},
                                new int[]{}
                        },
                        new int[]{
                                textColorPressed,
                                textColor
                        }
                ));
    }

    public void setTextViewAnimationListener(RoundTextViewAnimationListener listener) {
        this.listener = listener;
    }

    public void setCustomizations(RoundTextViewHelper.Builder builder) {
        if (builder.cornerRadius != null)
            cornerRadius = builder.cornerRadius;

        if (builder.cornerWidth != null)
            cornerWidth = builder.cornerWidth;

        if (builder.cornerColor != null)
            cornerColor = builder.cornerColor;

        if (builder.cornerColorSelected != null)
            cornerColorPressed = builder.cornerColorSelected;

        if (builder.cornerColorDisabled != null)
            cornerColorDisabled = builder.cornerColorDisabled;

        if (builder.backgroundColor != null)
            backgroundColor = builder.backgroundColor;

        if (builder.backgroundColorSelected != null)
            backgroundColorPressed = builder.backgroundColorSelected;

        if (builder.backgroundColorDisabled != null)
            backgroundColorDisabled = builder.backgroundColorDisabled;

        if (builder.textColor != null)
            textColor = builder.textColor;

        if (builder.textColorSelected != null)
            textColorPressed = builder.textColorSelected;

        if (builder.textColorDisabled != null)
            textColorDisabled = builder.textColorDisabled;

        if (builder.textColorDisabled != null)
            textColorDisabled = builder.textColorDisabled;

        if (builder.animationInnerResource != null)
            animationInnerResource = builder.animationInnerResource;

        if (builder.animationCustomResource != null)
            animationCustomResource = builder.animationCustomResource;

        if (builder.animationDurations != null)
            animationDurations = builder.animationDurations;

        if (builder.animationCornerRadius != null)
            animationCornerRadius = builder.animationCornerRadius;

        if (builder.animationProgressWidth != null)
            animationProgressWidth = builder.animationProgressWidth;

        if (builder.animationProgressColor != null)
            animationProgressColor = builder.animationProgressColor;

        if (builder.animationProgressPadding != null)
            animationProgressPadding = builder.animationProgressPadding;

        if (builder.animationAlpha != null)
            animationAlpha = builder.animationAlpha;

        if (builder.animationProgressStyle != null)
            animationProgressStyle = builder.animationProgressStyle;

        if (builder.resultSuccessColor != null)
            resultSuccessColor = builder.resultSuccessColor;

        if (builder.resultSuccessResource != null)
            resultSuccessResource = builder.resultSuccessResource;

        if (builder.resultFailureColor != null)
            resultFailureColor = builder.resultFailureColor;

        if (builder.resultFailureResource != null)
            resultFailureResource = builder.resultFailureResource;

        if (builder.text != null) {
            if (animationState != AnimationState.IDLE) {
                property.setText(builder.text);
            } else {
                setText(builder.text);
            }
        }

        if (builder.width != null) {
            if (animationState != AnimationState.IDLE) {
                property.setAdjustWidth(builder.width);
            } else {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = builder.width;
                setLayoutParams(layoutParams);
            }
        }

        if (builder.height != null) {
            if (animationState != AnimationState.IDLE) {
                property.setAdjustHeight(builder.height);
            } else {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = builder.height;
                setLayoutParams(layoutParams);
            }
        }

        update();
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
        showResultState();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (animationState == AnimationState.PROGRESS) {
            if (!progressDrawable.isRunning())
                progressDrawable.start();
            progressDrawable.draw(canvas);
        } else if (animationState == AnimationState.DONE && resultState != ResultState.NONE) {
            resultDrawable.draw(canvas);
        }
    }

    public void startAnimation() {
        if (animationState != AnimationState.IDLE) {
            return;
        }

        stopAnimation();
        stopMorphing();

        property = new TextViewProperty(this);

        resultState = ResultState.NONE;
        animationState = AnimationState.MORPHING;

        int fromAlpha = 255, toAlpha = 255;
        if (animationAlpha) {
            toAlpha = 0;
        }
        morphTrasformations(property.getWidth(), property.getHeight(), property.getHeight(), property.getHeight(), cornerRadius, animationCornerRadius, fromAlpha, toAlpha, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.onApplyMorphingEnd();
                }

                switch (animationProgressStyle) {
                    case CIRCLE:
                        progressDrawable = new CircularAnimDrawable(RoundTextView.this, animationProgressWidth, animationProgressColor, animationInnerResource, animationInnerResourceColor);
                        if (animationInnerResource != 0)
                            setClickable(true);
                        break;
                    case DOTS:
                        progressDrawable = new DotsAnimDrawable(RoundTextView.this, animationProgressColor);
                        break;
                    case CUSTOM:
                        progressDrawable = new CustomAnimDrawable(RoundTextView.this, animationCustomResource, animationProgressColor);
                        break;
                }

                int offset = (getWidth() - getHeight()) / 2;

                int left = offset + animationProgressPadding;
                int right = getWidth() - offset - animationProgressPadding;
                int bottom = getHeight() - animationProgressPadding;
                int top = animationProgressPadding;

                progressDrawable.setBounds(left, top, right, bottom);
                progressDrawable.setCallback(RoundTextView.this);
                progressDrawable.start();

                animationState = AnimationState.PROGRESS;

                if (listener != null) {
                    listener.onShowProgress();
                }

                if (resultState != ResultState.NONE) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showResultState();
                        }
                    }, 50);
                }
            }
        });
    }

    public void revertAnimation() {
        if (animationState == AnimationState.IDLE) {
            return;
        }

        stopAnimation();
        stopMorphing();

        resultState = ResultState.NONE;
        animationState = AnimationState.MORPHING;

        int fromAlpha = 255, toAlpha = 255;
        if (animationAlpha) {
            fromAlpha = 0;
        }

        morphTrasformations(getWidth(), getHeight(), property.getWidth(), property.getHeight(), animationCornerRadius, cornerRadius, fromAlpha, toAlpha, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (property.getAdjustWidth() != null) {
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.width = property.getAdjustWidth();
                    setLayoutParams(layoutParams);
                }
                if (property.getAdjustHeight() != null) {
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.height = property.getAdjustHeight();
                    setLayoutParams(layoutParams);
                }
                setText(property.getText());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    setCompoundDrawablesRelative(property.getDrawables()[0], property.getDrawables()[1], property.getDrawables()[2], property.getDrawables()[3]);
                } else {
                    setCompoundDrawables(property.getDrawables()[0], property.getDrawables()[1], property.getDrawables()[2], property.getDrawables()[3]);
                }
                setClickable(true);
                animationState = AnimationState.IDLE;
                if (listener != null) {
                    listener.onRevertMorphingEnd();
                }
            }
        });
    }

    private void showResultState() {
        if (animationState != AnimationState.PROGRESS) {
            return;
        }

        stopAnimation();

        if (resultDrawable != null) {
            resultDrawable.stop();
            resultDrawable.dispose();
        }

        if (resultState == ResultState.SUCCESS) {
            Bitmap bitmap = resultSuccessResource != 0 ? BitmapFactory.decodeResource(getResources(), resultSuccessResource) : Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            resultDrawable = new CircularRevealAnimDrawable(RoundTextView.this, resultSuccessColor, bitmap);
        } else {
            Bitmap bitmap = resultFailureResource != 0 ? BitmapFactory.decodeResource(getResources(), resultFailureResource) : Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            resultDrawable = new CircularRevealAnimDrawable(RoundTextView.this, resultFailureColor, bitmap);
        }

        int left = 0;
        int right = getWidth();
        int bottom = getHeight();
        int top = 0;

        resultDrawable.setBounds(left, top, right, bottom);
        resultDrawable.setCallback(RoundTextView.this);
        resultDrawable.start();

        if (listener != null) {
            listener.onShowResultState();
        }
    }

    private void stopMorphing() {
        if (morpSet != null && morpSet.isRunning()) {
            morpSet.cancel();
        }
    }

    public void stopAnimation() {
        if (animationState == AnimationState.PROGRESS && progressDrawable != null && progressDrawable.isRunning()) {
            progressDrawable.stop();
            animationState = AnimationState.DONE;
        }
    }

    private void morphTrasformations(int fromWidth, int fromHeight, int toWidth, int toHeight, int fromCorner, int toCorner, int fromAlpha, int toAlpha, Animator.AnimatorListener listener) {

        setCompoundDrawables(null, null, null, null);
        setText(null);
        setClickable(false);

        ValueAnimator cornerAnimation = ValueAnimator.ofInt(fromCorner, toCorner);
        cornerAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ((GradientDrawable) getBackground().getCurrent()).setCornerRadius((Integer) animation.getAnimatedValue());
            }
        });

        ValueAnimator widthAnimation = ValueAnimator.ofInt(fromWidth, toWidth);
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.width = val;
                setLayoutParams(layoutParams);
            }
        });

        ValueAnimator heightAnimation = ValueAnimator.ofInt(fromHeight, toHeight);
        heightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                layoutParams.height = val;
                setLayoutParams(layoutParams);
            }
        });

        ValueAnimator alphaAnimator = ValueAnimator.ofInt(fromAlpha, toAlpha);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                getBackground().getCurrent().setAlpha((Integer) animation.getAnimatedValue());
            }
        });

        morpSet = new AnimatorSet();
        morpSet.setDuration(animationDurations);
        morpSet.playTogether(cornerAnimation, widthAnimation, heightAnimation, alphaAnimator);
        morpSet.addListener(listener);
        morpSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (progressDrawable != null) {
            progressDrawable.dispose();
        }

        if (resultDrawable != null) {
            resultDrawable.dispose();
        }
    }

    public boolean isAnimating() {
        return animationState != AnimationState.IDLE;
    }

    private enum AnimationState {
        IDLE, MORPHING, PROGRESS, DONE
    }

    public enum ResultState {
        NONE, SUCCESS, FAILURE,
    }

    public enum AnimationProgressStyle {
        CIRCLE, DOTS, CUSTOM
    }

    public interface RoundTextViewAnimationListener {

        void onRevertMorphingEnd();

        void onApplyMorphingEnd();

        void onShowProgress();

        void onShowResultState();
    }

    private class TextViewProperty {

        private int height;

        private int width;

        private String text;

        private Drawable[] drawables;

        private Integer adjustWidth;

        private Integer adjustHeight;

        public TextViewProperty(TextView button) {
            width = button.getWidth();
            height = button.getHeight();
            text = button.getText().toString();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                drawables = button.getCompoundDrawablesRelative();
            } else {
                drawables = button.getCompoundDrawables();
            }
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Drawable[] getDrawables() {
            return drawables;
        }

        public Integer getAdjustWidth() {
            return adjustWidth;
        }

        public void setAdjustWidth(Integer adjustWidth) {
            this.adjustWidth = adjustWidth;
        }

        public Integer getAdjustHeight() {
            return adjustHeight;
        }

        public void setAdjustHeight(Integer adjustHeight) {
            this.adjustHeight = adjustHeight;
        }
    }
}
