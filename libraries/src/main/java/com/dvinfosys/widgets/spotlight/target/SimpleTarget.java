package com.dvinfosys.widgets.spotlight.target;

import android.animation.TimeInterpolator;
import android.app.Activity;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.spotlight.OnTargetStateChangedListener;
import com.dvinfosys.widgets.spotlight.shape.Shape;

public class SimpleTarget extends Target {

    private SimpleTarget(Shape shape, PointF point, View overlay, long duration,
                         TimeInterpolator animation, OnTargetStateChangedListener listener) {
        super(shape, point, overlay, duration, animation, listener);
    }

    public static class Builder extends AbstractTargetBuilder<Builder, SimpleTarget> {

        @Override protected Builder self() {
            return this;
        }

        private CharSequence title;
        private CharSequence description;
        private PointF overlayPoint;

        public Builder(@NonNull Activity context) {
            super(context);
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(CharSequence description) {
            this.description = description;
            return this;
        }

        public Builder setOverlayPoint(PointF overlayPoint) {
            this.overlayPoint = overlayPoint;
            return this;
        }

        public Builder setOverlayPoint(float x, float y) {
            this.overlayPoint = new PointF(x, y);
            return this;
        }

        @Override public SimpleTarget build() {
            ViewGroup root = new FrameLayout(getContext());
            View overlay = getContext().getLayoutInflater().inflate(R.layout.layout_spotlight, root);
            TextView titleView = overlay.findViewById(R.id.title);
            TextView descriptionView = overlay.findViewById(R.id.description);
            LinearLayout layout = overlay.findViewById(R.id.container);
            if (title != null) {
                titleView.setText(title);
            }
            if (description != null) {
                descriptionView.setText(description);
            }
            if (overlayPoint != null) {
                layout.setX(overlayPoint.x);
                layout.setY(overlayPoint.y);
            }
            return new SimpleTarget(shape, point, overlay, duration, animation, listener);
        }
    }
}