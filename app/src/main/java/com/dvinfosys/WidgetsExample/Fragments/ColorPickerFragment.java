package com.dvinfosys.WidgetsExample.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.Button.CustomButton;
import com.dvinfosys.widgets.ColorPicker.ColorPanelView;
import com.dvinfosys.widgets.ColorPicker.ColorPickerDialog;
import com.dvinfosys.widgets.ColorPicker.ColorPickerDialogListener;
import com.dvinfosys.widgets.ColorPicker.ColorPickerView;

public class ColorPickerFragment extends Fragment implements ColorPickerView.OnColorChangedListener, ColorPickerDialogListener {

    private static final int DIALOG_ID = 0;
    private ColorPickerView colorPickerView;
    private ColorPanelView newColorPanelView;
    private CustomButton btnPickColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_picker, container, false);
        colorPickerView = view.findViewById(R.id.cpv_color_picker_view);
        btnPickColor = view.findViewById(R.id.btn_pic_color);
        ColorPanelView colorPanelView = view.findViewById(R.id.cpv_color_panel_old);
        newColorPanelView = view.findViewById(R.id.cpv_color_panel_new);

        colorPickerView.setOnColorChangedListener(this);
        colorPickerView.setColor(Color.BLUE, true);
        colorPanelView.setColor(Color.RED);

        btnPickColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog pickerDialog = ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                        .setAllowPresets(true)
                        .setDialogId(DIALOG_ID)
                        .setColor(Color.BLACK)
                        .setShowAlphaSlider(true)
                        .create();
                pickerDialog.setColorPickerDialogListener(ColorPickerFragment.this);
                pickerDialog.show(getFragmentManager(),"ColorPicker");
                /*ColorPickerDialog.newBuilder()
                        .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                        .setAllowPresets(true)
                        .setDialogId(DIALOG_ID)
                        .setColor(Color.BLACK)
                        .setShowAlphaSlider(true)
                        .show(getActivity());*/
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Color Picker Example");
    }

    @Override
    public void onColorChanged(int newColor) {
        newColorPanelView.setColor(colorPickerView.getColor());
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) {
            case DIALOG_ID:
                Log.e("ColorPicker","Selected color->"+color);
                String hexColor = String.format("#%06X", (0xFFFFFF & color));
                Toast.makeText(getContext(), "Select Color :" + hexColor, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
