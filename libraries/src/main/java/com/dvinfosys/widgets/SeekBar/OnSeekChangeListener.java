package com.dvinfosys.widgets.SeekBar;

/**
 * created by DV Bhuva on  14/06/2019
 */
public interface OnSeekChangeListener {
    void onSeeking(SeekParams seekParams);

    void onStartTrackingTouch(CustomSeekBar seekBar);

    void onStopTrackingTouch(CustomSeekBar seekBar);
}