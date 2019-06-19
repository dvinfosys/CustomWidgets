package com.dvinfosys.widgets.EditText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class NormalEditText extends EditText {
    public NormalEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public NormalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public NormalEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins.ttf", context);
        setTypeface(customFont);
    }
}
