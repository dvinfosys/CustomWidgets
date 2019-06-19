package com.dvinfosys.widgets.ToggleButton;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dvinfosys.widgets.Utils.TextFontCache;

/**
 * Created by DV Bhuva on 30/03/2019.
 */

public class BlackToggleButton extends ToggleButton {
    public BlackToggleButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public BlackToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public BlackToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_Black.ttf", context);
        setTypeface(customFont);
    }
}
