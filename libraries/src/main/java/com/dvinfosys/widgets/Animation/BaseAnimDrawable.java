package com.dvinfosys.widgets.Animation;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

public abstract class BaseAnimDrawable extends Drawable implements Animatable {
    public abstract void dispose();

    public abstract void setupAnimations();
}
