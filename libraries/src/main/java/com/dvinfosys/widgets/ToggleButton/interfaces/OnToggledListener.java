package com.dvinfosys.widgets.ToggleButton.interfaces;

import com.dvinfosys.widgets.ToggleButton.model.ToggleableView;

public interface OnToggledListener {
    void onSwitched(ToggleableView toggleableView, boolean isOn);
}
