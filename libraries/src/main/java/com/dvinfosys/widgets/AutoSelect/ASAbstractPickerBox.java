package com.dvinfosys.widgets.AutoSelect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public abstract class ASAbstractPickerBox<T> extends FrameLayout {

    public ASAbstractPickerBox(@NonNull Context context) {
        super(context);
    }

    public ASAbstractPickerBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ASAbstractPickerBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void onSelect(T selectedItem, int selectedIndex);

    public abstract View getCellRoot();

}
