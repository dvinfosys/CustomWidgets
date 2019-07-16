package com.dvinfosys.widgets.ColorPicker;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.widget.GridView;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class NestedGridView extends GridView {

    public NestedGridView(Context context) {
        super(context);
    }

    public NestedGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
