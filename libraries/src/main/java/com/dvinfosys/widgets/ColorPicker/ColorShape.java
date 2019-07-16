package com.dvinfosys.widgets.ColorPicker;

import android.support.annotation.IntDef;

@IntDef({ ColorShape.SQUARE, ColorShape.CIRCLE }) public @interface ColorShape {

  int SQUARE = 0;

  int CIRCLE = 1;
}
