package com.dvinfosys.WidgetsExample.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.spotlight.OnSpotlightStateChangedListener;
import com.dvinfosys.widgets.spotlight.OnTargetStateChangedListener;
import com.dvinfosys.widgets.spotlight.Spotlight;
import com.dvinfosys.widgets.spotlight.shape.Circle;
import com.dvinfosys.widgets.spotlight.target.SimpleTarget;

public class SpotlightFragment extends Fragment {

    private TextView one, two, three;
    private Button btnSimpleTarget, btnCustomTarget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_spotlight, container, false);
        one = v.findViewById(R.id.one);
        two = v.findViewById(R.id.two);
        three = v.findViewById(R.id.three);
        btnSimpleTarget = v.findViewById(R.id.simple_target);
        btnCustomTarget = v.findViewById(R.id.custom_target);

        btnSimpleTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleTarget first = new SimpleTarget.Builder((Activity) getContext())
                        .setPoint(one)
                        .setShape(new Circle(200f)) // or RoundedRectangle()
                        .setTitle("first title")
                        .setDescription("first description")
                        .setOverlayPoint(100f, 100f)
                        .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                            @Override
                            public void onStarted(SimpleTarget target) {
                                // do something
                            }

                            @Override
                            public void onEnded(SimpleTarget target) {
                                // do something
                            }
                        })
                        .build();
                SimpleTarget second = new SimpleTarget.Builder((Activity) getContext())
                        .setPoint(two)
                        .setShape(new Circle(200f)) // or RoundedRectangle()
                        .setTitle("second title")
                        .setDescription("second description")
                        .setOverlayPoint(100f, 100f)
                        .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                            @Override
                            public void onStarted(SimpleTarget target) {
                                // do something
                            }

                            @Override
                            public void onEnded(SimpleTarget target) {
                                // do something
                            }
                        })
                        .build();
                SimpleTarget thirdTarget = new SimpleTarget.Builder((Activity) getContext())
                        .setPoint(three)
                        .setShape(new Circle(200f)) // or RoundedRectangle()
                        .setTitle("thirdTarget title")
                        .setDescription("thirdTarget description")
                        .setOverlayPoint(100f, 100f)
                        .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                            @Override
                            public void onStarted(SimpleTarget target) {
                                // do something
                            }

                            @Override
                            public void onEnded(SimpleTarget target) {
                                // do something
                            }
                        })
                        .build();

                Spotlight.with(getActivity())
                        .setOverlayColor(R.color.background)
                        .setDuration(1000L)
                        .setAnimation(new DecelerateInterpolator(2f))
                        .setTargets(first, second,thirdTarget)
                        .setClosedOnTouchedOutside(false)
                        .setOnSpotlightStateListener(new OnSpotlightStateChangedListener() {
                            @Override
                            public void onStarted() {
                                Toast.makeText(getContext(), "spotlight is started", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onEnded() {
                                Toast.makeText(getContext(), "spotlight is ended", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .start();

            }
        });
        return v;
    }

}
