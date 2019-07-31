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
import com.dvinfosys.widgets.ProgressView.CircleProgressView;
import com.dvinfosys.widgets.ProgressView.HorizontalProgressView;

public class ProgressViewFragment extends Fragment implements HorizontalProgressView.HorizontalProgressUpdateListener {

    private CircleProgressView circleProgressView;
    private HorizontalProgressView hpv_language, hpv_math, hpv_history, hpv_english;
    private TextView tv_language, tv_math, tv_history, tv_english, tv_main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_view, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        circleProgressView = view.findViewById(R.id.circle_progressview);
        circleProgressView.startProgressAnimation();
        hpv_language = view.findViewById(R.id.hpv_language);
        hpv_math = view.findViewById(R.id.hpv_math);
        hpv_history = view.findViewById(R.id.hpv_history);
        hpv_english = view.findViewById(R.id.hpv_english);
        tv_language = view.findViewById(R.id.progress_text_language);
        tv_english = view.findViewById(R.id.progress_text_english);
        tv_history = view.findViewById(R.id.progress_text_history);
        tv_math = view.findViewById(R.id.progress_text_math);
        hpv_language.setProgressViewUpdateListener(this);
        hpv_english.setProgressViewUpdateListener(this);
        hpv_history.setProgressViewUpdateListener(this);
        hpv_math.setProgressViewUpdateListener(this);
        hpv_language.startProgressAnimation();
        hpv_math.startProgressAnimation();
        hpv_history.startProgressAnimation();
        hpv_english.startProgressAnimation();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("ProgressView Example");
    }

    @Override
    public void onHorizontalProgressStart(View view) {

    }

    @Override
    public void onHorizontalProgressUpdate(View view, float progress) {
        int progressInt = (int) progress;
        switch (view.getId()){
            case R.id.hpv_language:
                tv_language.setText(progressInt + "%");
                break;

            case R.id.hpv_english:
                tv_english.setText(progressInt + "%");
                break;

            case R.id.hpv_history:
                tv_history.setText(progressInt + "%");
                break;

            case R.id.hpv_math:
                tv_math.setText(progressInt + "%");
                break;
        }
    }

    @Override
    public void onHorizontalProgressFinished(View view) {

    }
}
