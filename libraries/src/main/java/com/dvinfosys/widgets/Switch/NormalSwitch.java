package com.dvinfosys.widgets.Switch;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Switch;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class NormalSwitch extends Switch {
    public NormalSwitch(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public NormalSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public NormalSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins.ttf", context);
        setTypeface(customFont);
    }
}
