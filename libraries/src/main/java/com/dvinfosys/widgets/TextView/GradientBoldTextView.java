package com.dvinfosys.widgets.TextView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.Utils.TextFontCache;

/**
 * Created by DV Bhuva on 27/06/19.
 */

public class GradientBoldTextView extends TextView {

    int startColor, endColor;
    Shader shader;

    public GradientBoldTextView(Context context) {
        super(context);
    }

    public GradientBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            createShader(context, attrs, 0);
    }

    public GradientBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            createShader(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GradientBoldTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if (!isInEditMode())
            createShader(context, attrs, defStyleAttr);
    }


    public void createShader(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GradientBoldTextView, defStyleAttr, 0);
        try {
            startColor = a.getInt(R.styleable.GradientBoldTextView_startColor, 0);
            endColor = a.getInt(R.styleable.GradientBoldTextView_endColor, 0);
            shader = new LinearGradient(0, 0, 0, getTextSize(),
                    new int[]{startColor, endColor},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
            getPaint().setShader(shader);
        } finally {
            a.recycle();
        }
        Typeface customFont = TextFontCache.getTypeface("Poppins_Bold.ttf", context);
        setTypeface(customFont);
    }
}
