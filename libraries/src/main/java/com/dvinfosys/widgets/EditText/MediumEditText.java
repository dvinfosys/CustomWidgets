package com.dvinfosys.widgets.EditText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class MediumEditText extends EditText {
    public MediumEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public MediumEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public MediumEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_Medium.ttf", context);
        setTypeface(customFont);
    }
}
