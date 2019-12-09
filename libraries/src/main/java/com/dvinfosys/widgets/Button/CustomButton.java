package com.dvinfosys.widgets.Button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.dvinfosys.widgets.Exception.CustomException;
import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.Utils.TextFontCache;

public class CustomButton extends Button {
    Context context;

    public CustomButton(Context context) {
        super(context);
        this.context = context;
        try {
            init(null);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            init(attrs);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        try {
            init(attrs);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }
    private void init(AttributeSet attrs) throws CustomException {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomButton);
            String getFontName = array.getString(R.styleable.CustomButton_font_name);
            if (getFontName != null) {
                try {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + getFontName);
                    setTypeface(typeface);
                } catch (Exception e) {
                    throw new CustomException("Font Not Found Exception");
                }
            }else {
                Typeface customFont = TextFontCache.getTypeface("Poppins.ttf", context);
                setTypeface(customFont);
            }
            array.recycle();
        }
    }
}
