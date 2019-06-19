package com.dvinfosys.widgets.RadioButton;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

/**
 * Created by DV Bhuva on 30/03/2019.
 */

public class SemiBoldRadioButton extends RadioButton {
    public SemiBoldRadioButton(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public SemiBoldRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public SemiBoldRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_SemiBold.ttf", context);
        setTypeface(customFont);
    }
}
