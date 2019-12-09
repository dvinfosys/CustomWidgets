package com.dvinfosys.widgets.CheckBox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.dvinfosys.widgets.Exception.CustomException;
import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.Utils.TextFontCache;

public class CustomCheckBox extends CheckBox {
    private Context context;

    public CustomCheckBox(Context context) {
        super(context);
        this.context = context;
        try {
            init(null);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        try {
            init(attrs);
        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
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
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomCheckBox);
            String getFontName = array.getString(R.styleable.CustomCheckBox_font_name);
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
