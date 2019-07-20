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

    private Counter counter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_number_counter, container, false);
        counter = v.findViewById(R.id.number_counter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("NumberCounter Example");
    }

}
