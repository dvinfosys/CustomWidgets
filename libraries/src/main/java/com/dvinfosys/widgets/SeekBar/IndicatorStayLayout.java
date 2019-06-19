package com.dvinfosys.widgets.SeekBar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dvinfosys.widgets.Utils.SizeUtils;

/**
 * created by DV Bhuva on 14/06/2019
 */

public class IndicatorStayLayout extends LinearLayout {

    public IndicatorStayLayout(Context context) {
        this(context, null);
    }

    public IndicatorStayLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorStayLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            layoutIndicator(getChildAt(i), i);
        }
        super.onFinishInflate();
    }

    public void attachTo(CustomSeekBar seekBar) {
        attachTo(seekBar, -2);
    }

    public void attachTo(CustomSeekBar seekBar, int index) {
        if (seekBar == null) {
            throw new NullPointerException("the seek bar wanna attach to IndicatorStayLayout " +
                    "can not be null value.");
        }
        layoutIndicator(seekBar, index);
        addView(seekBar, index + 1);
    }

    private void layoutIndicator(View child, int index) {
        if (child instanceof CustomSeekBar) {
            CustomSeekBar seekBar = (CustomSeekBar) child;
            seekBar.setIndicatorStayAlways(true);
            View contentView = seekBar.getIndicatorContentView();
            if (contentView == null) {
                throw new IllegalStateException("Can not find any indicator in the CustomSeekBar, please " +
                        "make sure you have called the attr: SHOW_INDICATOR_TYPE for CustomSeekBar and the value is not IndicatorType.NONE.");
            }
            if (contentView instanceof CustomSeekBar) {
                throw new IllegalStateException("CustomSeekBar can not be a contentView for Indicator in case this inflating loop.");
            }
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            MarginLayoutParams layoutParams = new MarginLayoutParams(params);
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin,
                    layoutParams.rightMargin, SizeUtils.dp2px(seekBar.getContext(), 2) - seekBar.getPaddingTop());
            addView(contentView, index, layoutParams);
            seekBar.showStayIndicator();
        }
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation != VERTICAL) {
            throw new IllegalArgumentException("IndicatorStayLayout is always vertical and does"
                    + " not support horizontal orientation");
        }
        super.setOrientation(orientation);
    }

}