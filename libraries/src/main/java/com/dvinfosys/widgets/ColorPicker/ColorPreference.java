package com.dvinfosys.widgets.ColorPicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.Preference;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;

import com.dvinfosys.widgets.R;

public class ColorPreference extends Preference implements ColorPickerDialogListener {

    private static final int SIZE_NORMAL = 0;
    private static final int SIZE_LARGE = 1;

    private OnShowDialogListener onShowDialogListener;
    private int color = Color.BLACK;
    private boolean showDialog;
    @ColorPickerDialog.DialogType private int dialogType;
    private int colorShape;
    private boolean allowPresets;
    private boolean allowCustom;
    private boolean showAlphaSlider;
    private boolean showColorShades;
    private int previewSize;
    private int[] presets;
    private int dialogTitle;

    public ColorPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColorPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setPersistent(true);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ColorPreference);
        showDialog = a.getBoolean(R.styleable.ColorPreference_showDialog, true);
        //noinspection WrongConstant
        dialogType = a.getInt(R.styleable.ColorPreference_dialogType, ColorPickerDialog.TYPE_PRESETS);
        colorShape = a.getInt(R.styleable.ColorPreference_colorShape, ColorShape.CIRCLE);
        allowPresets = a.getBoolean(R.styleable.ColorPreference_allowPresets, true);
        allowCustom = a.getBoolean(R.styleable.ColorPreference_allowCustom, true);
        showAlphaSlider = a.getBoolean(R.styleable.ColorPreference_showAlphaSlider, false);
        showColorShades = a.getBoolean(R.styleable.ColorPreference_showColorShades, true);
        previewSize = a.getInt(R.styleable.ColorPreference_previewSize, SIZE_NORMAL);
        final int presetsResId = a.getResourceId(R.styleable.ColorPreference_colorPresets, 0);
        dialogTitle = a.getResourceId(R.styleable.ColorPreference_dialogTitle, R.string.default_title);
        if (presetsResId != 0) {
            presets = getContext().getResources().getIntArray(presetsResId);
        } else {
            presets = ColorPickerDialog.MATERIAL_COLORS;
        }
        if (colorShape == ColorShape.CIRCLE) {
            setWidgetLayoutResource(
                    previewSize == SIZE_LARGE ? R.layout.preference_circle_large : R.layout.preference_circle);
        } else {
            setWidgetLayoutResource(
                    previewSize == SIZE_LARGE ? R.layout.preference_square_large : R.layout.preference_square);
        }
        a.recycle();
    }

    @Override protected void onClick() {
        super.onClick();
        if (onShowDialogListener != null) {
            onShowDialogListener.onShowColorPickerDialog((String) getTitle(), color);
        } else if (showDialog) {
            ColorPickerDialog dialog = ColorPickerDialog.newBuilder()
                    .setDialogType(dialogType)
                    .setDialogTitle(dialogTitle)
                    .setColorShape(colorShape)
                    .setPresets(presets)
                    .setAllowPresets(allowPresets)
                    .setAllowCustom(allowCustom)
                    .setShowAlphaSlider(showAlphaSlider)
                    .setShowColorShades(showColorShades)
                    .setColor(color)
                    .create();
            dialog.setColorPickerDialogListener(this);
            FragmentActivity activity = (FragmentActivity) getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(dialog, getFragmentTag())
                    .commitAllowingStateLoss();
        }
    }

    @Override protected void onAttachedToActivity() {
        super.onAttachedToActivity();

        if (showDialog) {
            FragmentActivity activity = (FragmentActivity) getContext();
            ColorPickerDialog fragment =
                    (ColorPickerDialog) activity.getSupportFragmentManager().findFragmentByTag(getFragmentTag());
            if (fragment != null) {
                // re-bind preference to fragment
                fragment.setColorPickerDialogListener(this);
            }
        }
    }

    @Override protected void onBindView(View view) {
        super.onBindView(view);
        ColorPanelView preview = (ColorPanelView) view.findViewById(R.id.preference_preview_color_panel);
        if (preview != null) {
            preview.setColor(color);
        }
    }

    @Override protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        if (restorePersistedValue) {
            color = getPersistedInt(0xFF000000);
        } else {
            color = (Integer) defaultValue;
            persistInt(color);
        }
    }

    @Override protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInteger(index, Color.BLACK);
    }

    @Override public void onColorSelected(int dialogId, @ColorInt int color) {
        saveValue(color);
    }

    @Override public void onDialogDismissed(int dialogId) {
        // no-op
    }

    public void saveValue(@ColorInt int color) {
        this.color = color;
        persistInt(this.color);
        notifyChanged();
        callChangeListener(color);
    }

    public int[] getPresets() {
        return presets;
    }

    public void setPresets(@NonNull int[] presets) {
        this.presets = presets;
    }

    public void setOnShowDialogListener(OnShowDialogListener listener) {
        onShowDialogListener = listener;
    }

    public String getFragmentTag() {
        return "color_" + getKey();
    }

    public interface OnShowDialogListener {

        void onShowColorPickerDialog(String title, int currentColor);
    }
}
