package com.dvinfosys.widgets.SeekBar;


/**
 * created by DV Bhuva on 14/06/2019
 */
public class SeekParams {

    SeekParams(CustomSeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public CustomSeekBar seekBar;
    public int progress;
    public float progressFloat;
    public boolean fromUser;
    public int thumbPosition;
    public String tickText;
}
