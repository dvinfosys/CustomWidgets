package com.dvinfosys.widgets.RadioButton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.dvinfosys.widgets.Exception.CustomException;
import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.Utils.TextFontCache;

public class CustomRadioButton extends RadioButton {
    private Context context;

    public CustomRadioButton(Context context) {
        super(context);
        this.context = context;
        try {
            init(null);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            init(attrs);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomRadioButton);
            String getFontName = array.getString(R.styleable.CustomRadioButton_font_name);
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
