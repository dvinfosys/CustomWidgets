package com.dvinfosys.widgets.NumberCounter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.TextView.CustomTextView;

import java.util.Locale;

public class Counter extends LinearLayout {
    public static final String TAG = "JavaHorizontalCounter";

    public static final String DEFAULT_STEP_COUNT_SIZE = "1";
    public static final int DEFAULT_TEXT_SIZE = 16;
    public static final Double DEFAULT_STEP_COUNT = 1.0;
    public static final Double DEFAULT_CURRENT_VALUE = 0.0;
    public static final Double DEFAULT_MAX_VALUE = 999.0;

    private String stepString = DEFAULT_STEP_COUNT_SIZE;
    private Double stepValue = DEFAULT_STEP_COUNT;
    private Double currentValue = DEFAULT_CURRENT_VALUE;
    private Double maxValue = DEFAULT_MAX_VALUE;
    private Double minValue = -DEFAULT_MAX_VALUE;

    @ColorInt
    private int plusButtonColor;
    @ColorInt
    private int minusButtonColor;
    private int textColor;
    private int textSize = DEFAULT_TEXT_SIZE;

    private boolean displayingInteger = false;

    private RepeatListener.ReleaseCallback releaseCallback;
    private CustomTextView value;
    private ImageButton plusButton, minusButton;
    private Drawable plusIcon, minusIcon;

    @DrawableRes
    private int DEFAULT_PLUS_ICON = R.drawable.plus;
    @DrawableRes
    private int DEFAULT_MINUS_ICON = R.drawable.minus;

    public Counter(Context context) {
        super(context);
    }

    public Counter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        inflate(context, R.layout.counter_layout, this);

        plusButton = findViewById(R.id.plus);
        minusButton = findViewById(R.id.minus);
        value = findViewById(R.id.value);

        float density = getResources().getDisplayMetrics().density;
        textSize = (int) (density * textSize);

        plusButtonColor = ContextCompat.getColor(context, R.color.normalColor);
        minusButtonColor = ContextCompat.getColor(context, R.color.normalColor);
        textColor = ContextCompat.getColor(context, android.R.color.primary_text_light);

        if (attrs != null) {

            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.Counter);

            plusButtonColor = attributes.getColor(R.styleable.Counter_plusButtonColor, plusButtonColor);
            minusButtonColor = attributes.getColor(R.styleable.Counter_minusButtonColor, minusButtonColor);
            textSize = (int) attributes.getDimension(R.styleable.Counter_textSize, textSize);
            setTextColor(attributes.getColor(R.styleable.Counter_textColor, textColor));

            String currentValueString = attributes.getString(R.styleable.Counter_initialValue);
            String stepValueString = attributes.getString(R.styleable.Counter_stepValue);
            String maxValueString = attributes.getString(R.styleable.Counter_maxValue);
            String minValueString = attributes.getString(R.styleable.Counter_minValue);

            currentValue = (currentValueString != null) ? Double.valueOf(currentValueString) : DEFAULT_CURRENT_VALUE;
            maxValue = (maxValueString != null) ? Double.valueOf(maxValueString) : DEFAULT_MAX_VALUE;
            minValue = (minValueString != null) ? Double.valueOf(minValueString) : -DEFAULT_MAX_VALUE;
            setStepValue(stepValueString != null ? Double.valueOf(stepValueString) : DEFAULT_STEP_COUNT);

            Drawable rawPlusIcon = attributes.getDrawable(R.styleable.Counter_plusIcon);
            Drawable rawMinusIcon = attributes.getDrawable(R.styleable.Counter_minusIcon);

            plusIcon = (rawPlusIcon != null) ? rawPlusIcon : ContextCompat.getDrawable(context, DEFAULT_PLUS_ICON);
            minusIcon = (rawMinusIcon != null) ? rawMinusIcon : ContextCompat.getDrawable(context, DEFAULT_MINUS_ICON);

            displayingInteger = attributes.getBoolean(R.styleable.Counter_displayAsInteger, false);

            if (maxValue <= minValue || minValue >= maxValue) {
                throw new InvalidLimitsException();
            }

            attributes.recycle();
        }

        setupViews();

    }

    private void setupViews() {
        prepareViews(true);
        setupValueTextView();
        setupButtons();
        prepareViews(false);
    }

    private void setupButtons() {
        setupPlusButton();
        setupMinusButton();
    }

    private void setupValueTextView() {
        value.setTextColor(textColor);
        value.setTextSize(textSize);
        updateCurrentValue();
    }

    private void prepareViews(boolean initializing) {
        value.setVisibility((initializing) ? GONE : VISIBLE);
        plusButton.setVisibility((initializing) ? GONE : VISIBLE);
        minusButton.setVisibility((initializing) ? GONE : VISIBLE);

        value.setAlpha((initializing) ? 0 : 1f);
        plusButton.setAlpha((initializing) ? 0 : 1f);
        minusButton.setAlpha((initializing) ? 0 : 1f);
    }

    private void updateCurrentValue() {
        if (getCurrentValue() != null) {
            value.setText((isDisplayingInteger()) ?
                    String.format(Locale.US, "%d", getCurrentValue().intValue()) :
                    String.format("%." + getStepString() + "f", getCurrentValue().floatValue()));
        }
    }

    private void setupPlusButton() {
        setPlusButtonColor(plusButtonColor);
        plusButton.setImageDrawable(plusIcon);
        plusButton.setOnTouchListener(getPlusButtonListener());
    }

    public int getPlusButtonColor() {
        return plusButtonColor;
    }

    public void setPlusButtonColor(int plusButtonColor) {
        this.plusButtonColor = plusButtonColor;
        DrawableCompat.setTint(this.plusButton.getDrawable(), plusButtonColor);
    }

    public Drawable getPlusIcon() {
        return plusIcon;
    }

    public void setPlusIcon(Drawable plusIcon) {
        this.plusIcon = (plusIcon != null) ? plusIcon : ContextCompat.getDrawable(getContext(), DEFAULT_PLUS_ICON);
        this.plusButton.setImageDrawable(this.plusIcon);
        setPlusButtonColor(this.plusButtonColor);
    }

    private OnTouchListener getPlusButtonListener() {
        /*return new RepeatListener(v -> {
            if(getCurrentValue() < getMaxValue() && (getCurrentValue() + getStepValue()) <= getMaxValue()) {
                setCurrentValue(getCurrentValue() + getStepValue());
                updateCurrentValue();
            }
        }, getReleaseCallback());*/
        return new RepeatListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Counter.this.getCurrentValue() < Counter.this.getMaxValue() && (Counter.this.getCurrentValue() + Counter.this.getStepValue()) <= Counter.this.getMaxValue()) {
                    Counter.this.setCurrentValue(Counter.this.getCurrentValue() + Counter.this.getStepValue());
                    Counter.this.updateCurrentValue();
                }
            }
        }, getReleaseCallback());

    }

    private void setupMinusButton() {
        setMinusButtonColor(minusButtonColor);
        minusButton.setImageDrawable(minusIcon);
        minusButton.setOnTouchListener(getMinusButtonListener());
    }

    public int getMinusButtonColor() {
        return minusButtonColor;
    }

    public void setMinusButtonColor(int minusButtonColor) {
        this.minusButtonColor = minusButtonColor;
        DrawableCompat.setTint(this.minusButton.getDrawable(), minusButtonColor);
    }

    public Drawable getMinusIcon() {
        return minusIcon;
    }

    public void setMinusIcon(Drawable minusIcon) {
        this.minusIcon = (minusIcon != null) ? minusIcon : ContextCompat.getDrawable(getContext(), DEFAULT_MINUS_ICON);
        this.minusButton.setImageDrawable(this.minusIcon);
        setMinusButtonColor(this.minusButtonColor);
    }

    private OnTouchListener getMinusButtonListener() {
        return new RepeatListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Counter.this.getCurrentValue() > Counter.this.getMinValue() && (Counter.this.getCurrentValue() - Counter.this.getStepValue()) >= Counter.this.getMinValue()) {
                    Counter.this.setCurrentValue(Counter.this.getCurrentValue() - Counter.this.getStepValue());
                    Counter.this.updateCurrentValue();
                }
            }
        }, getReleaseCallback());
    }

    public void setOnReleaseListener(@NonNull RepeatListener.ReleaseCallback releaseCallback) {
        this.releaseCallback = releaseCallback;
    }

    public RepeatListener.ReleaseCallback getReleaseCallback() {
        return releaseCallback;
    }

    public String getStepString() {
        return stepString;
    }

    public Double getStepValue() {
        return stepValue;
    }

    public void setStepValue(@NonNull Double stepValue) {
        this.stepValue = stepValue;
        this.stepString = String.valueOf(stepValue.toString().split("\\.")[1].length());
        updateCurrentValue();
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(@NonNull Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(@NonNull Double maxValue) {
        if (maxValue <= getMinValue()) {
            throw new InvalidLimitsException();
        }
        this.maxValue = maxValue;
        setupButtons();
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(@NonNull Double minValue) {
        if (minValue >= getMaxValue()) {
            throw new InvalidLimitsException();
        }
        this.minValue = minValue;
        setupButtons();
    }

    public boolean isDisplayingInteger() {
        return displayingInteger;
    }

    public void setDisplayingInteger(boolean displayingInteger) {
        this.displayingInteger = displayingInteger;
        updateCurrentValue();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        value.setTextColor(textColor);
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = (int) (textSize * getResources().getDisplayMetrics().density);
        value.setTextSize(this.textSize);
    }

    public void setInitialValue(String value){
        currentValue = (value != null) ? Double.valueOf(value) : DEFAULT_CURRENT_VALUE;
        setupValueTextView();
    }
}
