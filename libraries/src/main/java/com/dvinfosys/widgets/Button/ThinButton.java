package com.dvinfosys.widgets.Button;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

/**
 * Created by DV Bhuva on 30/03/2019.
 */

public class ThinButton extends Button {
    public ThinButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public ThinButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public ThinButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_thin.ttf", context);
        setTypeface(customFont);
    }
}
