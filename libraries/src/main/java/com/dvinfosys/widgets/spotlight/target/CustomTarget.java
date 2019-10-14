package com.dvinfosys.widgets.spotlight.target;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.graphics.PointF;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.dvinfosys.widgets.spotlight.OnTargetStateChangedListener;
import com.dvinfosys.widgets.spotlight.shape.Shape;

public class CustomTarget extends Target {

    private CustomTarget(Shape shape, PointF point, View overlay, long duration,
                         TimeInterpolator animation, OnTargetStateChangedListener listener) {
        super(shape, point, overlay, duration, animation, listener);
    }

    public static class Builder extends AbstractTargetBuilder<Builder, CustomTarget> {

        @Override protected Builder self() {
            return this;
        }

        private View overlay;

        public Builder(Activity context) {
            super(context);
        }

        public Builder setOverlay(@LayoutRes int layoutId) {
            this.overlay = getContext().getLayoutInflater().inflate(layoutId, null);
            return this;
        }

        public Builder setOverlay(View overlay) {
            this.overlay = overlay;
            return this;
        }

        @Override public CustomTarget build() {
            return new CustomTarget(shape, point, overlay, duration, animation, listener);
        }
    }
}