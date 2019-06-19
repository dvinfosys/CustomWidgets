package com.dvinfosys.widgets.ToggleButton;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class NormalToggleButton extends ToggleButton {
    public NormalToggleButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public NormalToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public NormalToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins.ttf", context);
        setTypeface(customFont);
    }
}
