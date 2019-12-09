package com.dvinfosys.WidgetsExample.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.SeekBar.CustomSeekBar;
import com.dvinfosys.widgets.SeekBar.OnSeekChangeListener;
import com.dvinfosys.widgets.SeekBar.SeekParams;
import com.dvinfosys.widgets.TextView.CustomTextView;

public class SeekbarFragment extends Fragment {

    CustomSeekBar seekBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seekbar, container, false);
        seekBar = view.findViewById(R.id.percent_indicator);
        seekBar.setIndicatorTextFormat("${PROGRESS} %");

        CustomSeekBar scale = view.findViewById(R.id.scale);
        scale.setDecimalScale(4);
        View contentView = scale.getIndicator().getContentView();

        CustomSeekBar thumb_drawable = view.findViewById(R.id.thumb_drawable);
        thumb_drawable.setThumbDrawable(getResources().getDrawable(R.mipmap.ic_launcher));

        CustomSeekBar listenerSeekBar = view.findViewById(R.id.listener);
        final CustomTextView states = view.findViewById(R.id.states);
        states.setText("states: ");
        final CustomTextView progress = view.findViewById(R.id.progress);
        progress.setText("progress: " + listenerSeekBar.getProgress());
        final CustomTextView progress_float = view.findViewById(R.id.progress_float);
        progress_float.setText("progress_float: " + listenerSeekBar.getProgressFloat());
        final CustomTextView from_user = view.findViewById(R.id.from_user);
        from_user.setText("from_user: ");
        listenerSeekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {
                states.setText("states: onSeeking");
                progress.setText("progress: " + seekParams.progress);
                progress_float.setText("progress_float: " + seekParams.progressFloat);
                from_user.setText("from_user: " + seekParams.fromUser);
            }

            @Override
            public void onStartTrackingTouch(CustomSeekBar seekBar) {
                states.setText("states: onStart");
                progress.setText("progress: " + seekBar.getProgress());
                progress_float.setText("progress_float: " + seekBar.getProgressFloat());
                from_user.setText("from_user: true");
            }

            @Override
            public void onStopTrackingTouch(CustomSeekBar seekBar) {
                states.setText("states: onStop");
                progress.setText("progress: " + seekBar.getProgress());
                progress_float.setText("progress_float: " + seekBar.getProgressFloat());
                from_user.setText("from_user: false");
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("SeekBar Example");
    }

}
