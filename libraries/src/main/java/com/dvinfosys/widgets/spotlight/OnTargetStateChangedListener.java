package com.dvinfosys.widgets.spotlight;

import com.dvinfosys.widgets.spotlight.target.Target;

public interface OnTargetStateChangedListener<T extends Target> {

    void onStarted(T target);

    void onEnded(T target);
}
