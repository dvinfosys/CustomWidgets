package com.dvinfosys.WidgetsExample.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.ColorPicker.ColorPanelView;
import com.dvinfosys.widgets.ColorPicker.ColorPickerView;

public class ColorPickerFragment extends Fragment implements ColorPickerView.OnColorChangedListener{

    private ColorPickerView colorPickerView;
    private ColorPanelView newColorPanelView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);
        colorPickerView = view.findViewById(R.id.cpv_color_picker_view);
        ColorPanelView colorPanelView = view.findViewById(R.id.cpv_color_panel_old);
        newColorPanelView = view.findViewById(R.id.cpv_color_panel_new);

        colorPickerView.setOnColorChangedListener(this);
        colorPickerView.setColor(Color.BLUE, true);
        colorPanelView.setColor(Color.RED);

        return view;
    }

    @Override
    public void onColorChanged(int newColor) {
        newColorPanelView.setColor(colorPickerView.getColor());
    }
}
