package com.dvinfosys.widgets.spotlight.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Circle implements Shape {

    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }

    @Override public void draw(Canvas canvas, PointF point, float value, Paint paint) {
        canvas.drawCircle(point.x, point.y, value * radius, paint);
    }
}