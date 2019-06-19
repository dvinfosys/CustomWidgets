package com.dvinfosys.widgets.CheckBox;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;


/**
 * Created by DV Bhuva on 30/03/2019.
 */

public class HeadingCheckBox extends CheckBox {
    public HeadingCheckBox(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public HeadingCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public HeadingCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_Bold.ttf", context);
        setTypeface(customFont);
    }
}
