package com.dvinfosys.widgets.RadioButton;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class MediumRadioButton extends RadioButton {
    public MediumRadioButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public MediumRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public MediumRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_Medium.ttf", context);
        setTypeface(customFont);
    }
}
