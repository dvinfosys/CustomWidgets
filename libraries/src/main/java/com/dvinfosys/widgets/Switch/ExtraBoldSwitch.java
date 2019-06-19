package com.dvinfosys.widgets.Switch;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Switch;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

/**
 * Created by DV Bhuva on 30/03/2019.
 */

public class ExtraBoldSwitch extends Switch {
    public ExtraBoldSwitch(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public ExtraBoldSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public ExtraBoldSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_ExtraBold.ttf", context);
        setTypeface(customFont);
    }
}
