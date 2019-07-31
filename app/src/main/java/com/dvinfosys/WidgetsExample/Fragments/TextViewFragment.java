package com.dvinfosys.WidgetsExample.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.TextView.RoundTextView;
import com.dvinfosys.widgets.TextView.RoundTextViewHelper;

import java.util.Random;

public class TextViewFragment extends Fragment {

    private RoundTextView tvStandard, tvDot, tvRound, tvAlpha, tvSuccess, tvFailure, tvBonus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_view, container, false);

        tvStandard = view.findViewById(R.id.tv_standard);
        tvDot = view.findViewById(R.id.tv_dot);
        tvRound = view.findViewById(R.id.tv_round);
        tvAlpha = view.findViewById(R.id.tv_alpha);
        tvSuccess = view.findViewById(R.id.tv_success);
        tvFailure = view.findViewById(R.id.tv_failure);
        tvBonus = view.findViewById(R.id.tv_bonus);

        tvStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStandard.startAnimation();
                tvStandard.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvStandard.revertAnimation();
                    }
                }, 3000);
            }
        });

        tvDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDot.startAnimation();
                tvDot.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvDot.revertAnimation();
                    }
                }, 3000);
            }
        });

        tvRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvRound.startAnimation();
                tvRound.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvRound.revertAnimation();
                    }
                }, 3000);
            }
        });

        tvAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvAlpha.isAnimating()) {
                    tvAlpha.setResultState(RoundTextView.ResultState.FAILURE);
                } else {
                    tvAlpha.startAnimation();
                    tvAlpha.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvAlpha.revertAnimation();
                        }
                    }, 3000);
                }
            }
        });

        tvSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSuccess.startAnimation();
                tvSuccess.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvSuccess.setResultState(RoundTextView.ResultState.SUCCESS);
                    }
                }, 3000);
                tvSuccess.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvSuccess.revertAnimation();
                    }
                }, 7000);
            }
        });

        tvFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFailure.startAnimation();
                tvFailure.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvFailure.setResultState(RoundTextView.ResultState.FAILURE);
                    }
                }, 3000);
                tvFailure.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvFailure.revertAnimation();
                    }
                }, 7000);
            }
        });

        tvBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvBonus.startAnimation();
                tvBonus.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        RoundTextViewHelper.Builder builder = RoundTextView.newBuilder()
                                .withText("Bonus")
                                .withBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                                .withTextColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                                .withCornerColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                                .withCornerRadius(random.nextInt(20))
                                .withCornerWidth(random.nextInt(20));
                        tvBonus.setCustomizations(builder);
                        tvBonus.revertAnimation();
                    }
                }, 3000);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("TextView Example");
    }
}
