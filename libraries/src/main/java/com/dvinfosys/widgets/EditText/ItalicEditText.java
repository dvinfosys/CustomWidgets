package com.dvinfosys.widgets.EditText;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.dvinfosys.widgets.Utils.TextFontCache;

/**
 * Created by DV Bhuva on 30/03/2019.
 */

public class ItalicEditText extends EditText {
    public ItalicEditText(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public ItalicEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public ItalicEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = TextFontCache.getTypeface("Poppins_Italic.ttf", context);
        setTypeface(customFont);
    }
}
