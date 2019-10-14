package com.dvinfosys.widgets.spotlight.target;

import android.animation.TimeInterpolator;
import android.graphics.PointF;
import android.view.View;

import com.dvinfosys.widgets.spotlight.OnTargetStateChangedListener;
import com.dvinfosys.widgets.spotlight.shape.Shape;

public abstract class Target {

    private Shape shape;
    private PointF point;
    private View overlay;
    private long duration;
    private TimeInterpolator animation;
    private OnTargetStateChangedListener listener;

    public Target(Shape shape, PointF point, View overlay, long duration, TimeInterpolator animation,
                  OnTargetStateChangedListener listener) {
        this.shape = shape;
        this.point = point;
        this.overlay = overlay;
        this.duration = duration;
        this.animation = animation;
        this.listener = listener;
    }

    public PointF getPoint() {
        return point;
    }

    public View getOverlay() {
        return overlay;
    }

    public Shape getShape() {
        return shape;
    }

    public long getDuration() {
        return duration;
    }


    public TimeInterpolator getAnimation() {
        return animation;
    }

    public OnTargetStateChangedListener getListener() {
        return listener;
    }
}
