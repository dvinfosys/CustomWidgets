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

public class HeadingSwitch extends Switch {
    public HeadingSwitch(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public HeadingSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public HeadingSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_Bold.ttf", context);
        setTypeface(customFont);
    }
}
