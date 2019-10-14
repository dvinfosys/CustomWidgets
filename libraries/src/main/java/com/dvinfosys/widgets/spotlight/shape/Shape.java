package com.dvinfosys.widgets.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public interface Shape {
    void draw(Canvas canvas, PointF point, float value, Paint paint);
}
