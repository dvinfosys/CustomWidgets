package com.dvinfosys.widgets.Button;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class MediumButton extends Button {
    public MediumButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public MediumButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public MediumButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_Medium.ttf", context);
        setTypeface(customFont);
    }
}
