package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.ToggleButton.ToggleButton;
import com.dvinfosys.widgets.ToggleButton.interfaces.OnToggledListener;
import com.dvinfosys.widgets.ToggleButton.model.ToggleableView;

public class ToggleButtonFragment extends Fragment {

    ToggleButton toggleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toggle_button, container, false);
        toggleButton = view.findViewById(R.id.toggle_button_1);
        toggleButton.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                Toast.makeText(getContext(), "Toggle Button : " + isOn, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("ToggleButton Example");
    }

}
