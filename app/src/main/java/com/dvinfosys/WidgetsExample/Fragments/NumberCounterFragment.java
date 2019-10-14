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
import com.dvinfosys.widgets.NumberCounter.Counter;

public class NumberCounterFragment extends Fragment {

    private Counter ncJavaBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_number_counter, container, false);
        ncJavaBase = v.findViewById(R.id.nc_java);
        ncJavaBase.setInitialValue("20");
        ncJavaBase.setStepValue(Double.valueOf(2));
        ncJavaBase.setMaxValue(Double.valueOf(40));
        ncJavaBase.setMinValue(Double.valueOf(10));
        ncJavaBase.setCurrentValue(Double.valueOf(14));
        ncJavaBase.setDisplayingInteger(true);
        ncJavaBase.setTextColor(Color.parseColor("#4CAF50"));
        ncJavaBase.setMinusButtonColor(Color.parseColor("#909090"));
        ncJavaBase.setPlusButtonColor(Color.parseColor("#909090"));
        ncJavaBase.setTextSize(16);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("NumberCounter Example");
    }

}
