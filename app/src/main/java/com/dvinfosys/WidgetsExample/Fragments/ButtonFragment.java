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
import com.dvinfosys.widgets.Button.RoundButton;
import com.dvinfosys.widgets.Button.RoundButtonHelper;

import java.util.Random;

public class ButtonFragment extends Fragment {

    private RoundButton btnStandard, btnDot, btnRound, btnAlpha, btnSuccess, btnFailure, btnBonus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        btnStandard = view.findViewById(R.id.btn_standard);
        btnDot = view.findViewById(R.id.bt_dot);
        btnRound = view.findViewById(R.id.bt_round);
        btnAlpha = view.findViewById(R.id.bt_alpha);
        btnSuccess = view.findViewById(R.id.bt_success);
        btnFailure = view.findViewById(R.id.bt_failure);
        btnBonus = view.findViewById(R.id.bt_bonus);

        btnStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStandard.startAnimation();
                btnStandard.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnStandard.revertAnimation();
                    }
                }, 3000);
            }
        });

        btnDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDot.startAnimation();
                btnDot.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnDot.revertAnimation();
                    }
                }, 3000);
            }
        });

        btnRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRound.startAnimation();
                btnRound.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnRound.revertAnimation();
                    }
                }, 3000);
            }
        });

        btnAlpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnAlpha.isAnimating()) {
                    btnAlpha.setResultState(RoundButton.ResultState.FAILURE);
                } else {
                    btnAlpha.startAnimation();
                    btnAlpha.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnAlpha.revertAnimation();
                        }
                    }, 3000);
                }
            }
        });

        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSuccess.startAnimation();
                btnSuccess.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSuccess.setResultState(RoundButton.ResultState.SUCCESS);
                    }
                }, 3000);
                btnSuccess.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSuccess.revertAnimation();
                    }
                }, 7000);
            }
        });

        btnFailure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFailure.startAnimation();
                btnFailure.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnFailure.setResultState(RoundButton.ResultState.FAILURE);
                    }
                }, 3000);
                btnFailure.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnFailure.revertAnimation();
                    }
                }, 7000);
            }
        });

        btnBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBonus.startAnimation();
                btnBonus.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        RoundButtonHelper.Builder builder = RoundButton.newBuilder()
                                .withText("Bonus")
                                .withBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                                .withTextColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                                .withCornerColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))
                                .withCornerRadius(random.nextInt(20))
                                .withCornerWidth(random.nextInt(20));
                        btnBonus.setCustomizations(builder);
                        btnBonus.revertAnimation();
                    }
                }, 3000);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Button Example");
    }

}
