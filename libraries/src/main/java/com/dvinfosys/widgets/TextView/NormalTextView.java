package com.dvinfosys.widgets.TextView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

public class NormalTextView extends TextView {
    public NormalTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public NormalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public NormalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins.ttf", context);
        setTypeface(customFont);
    }
}
