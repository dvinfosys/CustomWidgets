package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.SeekBar.RangeSeekBar;

import java.text.DecimalFormat;

public class RangeSeekbarFragment extends Fragment {

    private RangeSeekBar seekbar1;
    private RangeSeekBar seekbar2;
    private TextView tv2;
    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_range_seekbar, container, false);
        seekbar1 = (RangeSeekBar)v.findViewById(R.id.seekbar1);
        seekbar2 = (RangeSeekBar)v.findViewById(R.id.seekbar2);
        tv2 = (TextView)v.findViewById(R.id.progress2_tv);

        seekbar1.setValue(10);
        seekbar2.setValue(-0.5f,0.8f);

        seekbar1.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                seekbar1.setProgressDescription((int)min+"%");
            }
        });

        seekbar2.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                if (isFromUser) {
                    tv2.setText(min + "-" + max);
                    seekbar2.setLeftProgressDescription(df.format(min));
                    seekbar2.setRightProgressDescription(df.format(max));
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Range Seekbar Example");
    }

}
