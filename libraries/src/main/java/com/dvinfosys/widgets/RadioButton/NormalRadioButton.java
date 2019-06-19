package com.dvinfosys.widgets.RadioButton;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class NormalRadioButton extends RadioButton {
    public NormalRadioButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public NormalRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public NormalRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins.ttf", context);
        setTypeface(customFont);
    }
}
