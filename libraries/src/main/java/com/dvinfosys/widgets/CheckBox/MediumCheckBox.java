package com.dvinfosys.widgets.CheckBox;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class MediumCheckBox extends CheckBox {
    public MediumCheckBox(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public MediumCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public MediumCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_Medium.ttf", context);
        setTypeface(customFont);
    }
}
