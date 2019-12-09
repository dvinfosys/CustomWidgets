package com.dvinfosys.widgets.TextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dvinfosys.widgets.Exception.CustomException;
import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.Utils.TextFontCache;

/**
 * Created by DV Bhuva on 19/06/2019.
 */

public class CustomTextView extends TextView {
    Context context;

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            init(attrs);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        try {
            init(attrs);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public CustomTextView(Context context) {
        super(context);
        this.context = context;
        try {
            init(null);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    private void init(AttributeSet attrs) throws CustomException {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String getFontName = array.getString(R.styleable.CustomTextView_font_name);
            if (getFontName != null) {
                try {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + getFontName);
                    setTypeface(typeface);
                } catch (Exception e) {
                    throw new CustomException("Font Not Found Exception");
                }
            }else {
                try {
                    Typeface customFont = TextFontCache.getTypeface("Poppins.ttf", context);
                    setTypeface(customFont);
                } catch (Exception e) {
                    throw new CustomException("Font Not Found Exception");
                }
            }
            array.recycle();
        }
    }
}
