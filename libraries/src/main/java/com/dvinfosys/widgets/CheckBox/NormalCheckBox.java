package com.dvinfosys.widgets.CheckBox;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class NormalCheckBox extends CheckBox {
    public NormalCheckBox(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public NormalCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public NormalCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins.ttf", context);
        setTypeface(customFont);
    }
}
