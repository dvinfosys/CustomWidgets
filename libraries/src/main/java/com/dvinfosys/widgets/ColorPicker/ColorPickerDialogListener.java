package com.dvinfosys.widgets.ColorPicker;

import android.support.annotation.ColorInt;

public interface ColorPickerDialogListener {
    void onColorSelected(int dialogId, @ColorInt int color);
    void onDialogDismissed(int dialogId);
}
