package com.dvinfosys.widgets.ColorPicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.ColorUtils;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dvinfosys.widgets.R;

import java.util.Arrays;
import java.util.Locale;

public class ColorPickerDialog extends DialogFragment implements ColorPickerView.OnColorChangedListener, TextWatcher {

    public static final int TYPE_CUSTOM = 0;
    public static final int TYPE_PRESETS = 1;

    public static final int[] MATERIAL_COLORS = {
            0xFFF44336, // RED 500
            0xFFE91E63, // PINK 500
            0xFFFF2C93, // LIGHT PINK 500
            0xFF9C27B0, // PURPLE 500
            0xFF673AB7, // DEEP PURPLE 500
            0xFF3F51B5, // INDIGO 500
            0xFF2196F3, // BLUE 500
            0xFF03A9F4, // LIGHT BLUE 500
            0xFF00BCD4, // CYAN 500
            0xFF009688, // TEAL 500
            0xFF4CAF50, // GREEN 500
            0xFF8BC34A, // LIGHT GREEN 500
            0xFFCDDC39, // LIME 500
            0xFFFFEB3B, // YELLOW 500
            0xFFFFC107, // AMBER 500
            0xFFFF9800, // ORANGE 500
            0xFF795548, // BROWN 500
            0xFF607D8B, // BLUE GREY 500
            0xFF9E9E9E, // GREY 500
    };
    static final int ALPHA_THRESHOLD = 165;
    private static final String TAG = "ColorPickerDialog";
    private static final String ARG_ID = "id";
    private static final String ARG_TYPE = "dialogType";
    private static final String ARG_COLOR = "color";
    private static final String ARG_ALPHA = "alpha";
    private static final String ARG_PRESETS = "presets";
    private static final String ARG_ALLOW_PRESETS = "allowPresets";
    private static final String ARG_ALLOW_CUSTOM = "allowCustom";
    private static final String ARG_DIALOG_TITLE = "dialogTitle";
    private static final String ARG_SHOW_COLOR_SHADES = "showColorShades";
    private static final String ARG_COLOR_SHAPE = "colorShape";
    private static final String ARG_PRESETS_BUTTON_TEXT = "presetsButtonText";
    private static final String ARG_CUSTOM_BUTTON_TEXT = "customButtonText";
    private static final String ARG_SELECTED_BUTTON_TEXT = "selectedButtonText";

    ColorPickerDialogListener colorPickerDialogListener;
    FrameLayout rootView;
    int[] presets;
    @ColorInt
    int color;
    int dialogType;
    int dialogId;
    boolean showColorShades;
    int colorShape;

    // -- PRESETS --------------------------
    ColorPaletteAdapter adapter;
    LinearLayout shadesLayout;
    SeekBar transparencySeekBar;
    TextView transparencyPercText;

    // -- CUSTOM ---------------------------
    ColorPickerView colorPicker;
    ColorPanelView newColorPanel;
    EditText hexEditText;
    private final OnTouchListener onPickerTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v != hexEditText && hexEditText.hasFocus()) {
                hexEditText.clearFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(hexEditText.getWindowToken(), 0);
                hexEditText.clearFocus();
                return true;
            }
            return false;
        }
    };
    boolean showAlphaSlider;
    private int presetsButtonStringRes;
    private boolean fromEditText;
    private int customButtonStringRes;

    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialogId = getArguments().getInt(ARG_ID);
        showAlphaSlider = getArguments().getBoolean(ARG_ALPHA);
        showColorShades = getArguments().getBoolean(ARG_SHOW_COLOR_SHADES);
        colorShape = getArguments().getInt(ARG_COLOR_SHAPE);
        if (savedInstanceState == null) {
            color = getArguments().getInt(ARG_COLOR);
            dialogType = getArguments().getInt(ARG_TYPE);
        } else {
            color = savedInstanceState.getInt(ARG_COLOR);
            dialogType = savedInstanceState.getInt(ARG_TYPE);
        }

        rootView = new FrameLayout(requireActivity());
        if (dialogType == TYPE_CUSTOM) {
            rootView.addView(createPickerView());
        } else if (dialogType == TYPE_PRESETS) {
            rootView.addView(createPresetsView());
        }

        int selectedButtonStringRes = getArguments().getInt(ARG_SELECTED_BUTTON_TEXT);
        if (selectedButtonStringRes == 0) {
            selectedButtonStringRes = R.string.select;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity()).setView(rootView)
                .setPositiveButton(selectedButtonStringRes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onColorSelected(color);
                    }
                });

        int dialogTitleStringRes = getArguments().getInt(ARG_DIALOG_TITLE);
        if (dialogTitleStringRes != 0) {
            builder.setTitle(dialogTitleStringRes);
        }

        presetsButtonStringRes = getArguments().getInt(ARG_PRESETS_BUTTON_TEXT);
        customButtonStringRes = getArguments().getInt(ARG_CUSTOM_BUTTON_TEXT);

        int neutralButtonStringRes;
        if (dialogType == TYPE_CUSTOM && getArguments().getBoolean(ARG_ALLOW_PRESETS)) {
            neutralButtonStringRes = (presetsButtonStringRes != 0 ? presetsButtonStringRes : R.string.presets);
        } else if (dialogType == TYPE_PRESETS && getArguments().getBoolean(ARG_ALLOW_CUSTOM)) {
            neutralButtonStringRes = (customButtonStringRes != 0 ? customButtonStringRes : R.string.custom);
        } else {
            neutralButtonStringRes = 0;
        }

        if (neutralButtonStringRes != 0) {
            builder.setNeutralButton(neutralButtonStringRes, null);
        }

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();

        // http://stackoverflow.com/a/16972670/1048340
        //noinspection ConstantConditions
        dialog.getWindow()
                .clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        // Do not dismiss the dialog when clicking the neutral button.
        Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        if (neutralButton != null) {
            neutralButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootView.removeAllViews();
                    switch (dialogType) {
                        case TYPE_CUSTOM:
                            dialogType = TYPE_PRESETS;
                            ((Button) v).setText(customButtonStringRes != 0 ? customButtonStringRes : R.string.custom);
                            rootView.addView(createPresetsView());
                            break;
                        case TYPE_PRESETS:
                            dialogType = TYPE_CUSTOM;
                            ((Button) v).setText(presetsButtonStringRes != 0 ? presetsButtonStringRes : R.string.presets);
                            rootView.addView(createPickerView());
                    }
                }
            });
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onDialogDismissed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARG_COLOR, color);
        outState.putInt(ARG_TYPE, dialogType);
        super.onSaveInstanceState(outState);
    }

    public void setColorPickerDialogListener(ColorPickerDialogListener colorPickerDialogListener) {
        this.colorPickerDialogListener = colorPickerDialogListener;
    }

    View createPickerView() {
        View contentView = View.inflate(getActivity(), R.layout.dialog_color_picker, null);
        colorPicker = contentView.findViewById(R.id.color_picker_view);
        ColorPanelView oldColorPanel = contentView.findViewById(R.id.color_panel_old);
        newColorPanel = contentView.findViewById(R.id.color_panel_new);
        ImageView arrowRight = contentView.findViewById(R.id.cpv_arrow_right);
        hexEditText = contentView.findViewById(R.id.cpv_hex);

        try {
            final TypedValue value = new TypedValue();
            TypedArray typedArray =
                    getActivity().obtainStyledAttributes(value.data, new int[]{android.R.attr.textColorPrimary});
            int arrowColor = typedArray.getColor(0, Color.BLACK);
            typedArray.recycle();
            arrowRight.setColorFilter(arrowColor);
        } catch (Exception ignored) {
        }

        colorPicker.setAlphaSliderVisible(showAlphaSlider);
        oldColorPanel.setColor(getArguments().getInt(ARG_COLOR));
        colorPicker.setColor(color, true);
        newColorPanel.setColor(color);
        setHex(color);

        if (!showAlphaSlider) {
            hexEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        }

        newColorPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newColorPanel.getColor() == color) {
                    onColorSelected(color);
                    dismiss();
                }
            }
        });

        contentView.setOnTouchListener(onPickerTouchListener);
        colorPicker.setOnColorChangedListener(this);
        hexEditText.addTextChangedListener(this);

        hexEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(hexEditText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });

        return contentView;
    }

    @Override
    public void onColorChanged(int newColor) {
        color = newColor;
        if (newColorPanel != null) {
            newColorPanel.setColor(newColor);
        }
        if (!fromEditText && hexEditText != null) {
            setHex(newColor);
            if (hexEditText.hasFocus()) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(hexEditText.getWindowToken(), 0);
                hexEditText.clearFocus();
            }
        }
        fromEditText = false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (hexEditText.isFocused()) {
            int color = parseColorString(s.toString());
            if (color != colorPicker.getColor()) {
                fromEditText = true;
                colorPicker.setColor(color, true);
            }
        }
    }

    private void setHex(int color) {
        if (showAlphaSlider) {
            hexEditText.setText(String.format("%08X", (color)));
        } else {
            hexEditText.setText(String.format("%06X", (0xFFFFFF & color)));
        }
    }

    private int parseColorString(String colorString) throws NumberFormatException {
        int a, r, g, b = 0;
        if (colorString.startsWith("#")) {
            colorString = colorString.substring(1);
        }
        if (colorString.length() == 0) {
            r = 0;
            a = 255;
            g = 0;
        } else if (colorString.length() <= 2) {
            a = 255;
            r = 0;
            b = Integer.parseInt(colorString, 16);
            g = 0;
        } else if (colorString.length() == 3) {
            a = 255;
            r = Integer.parseInt(colorString.substring(0, 1), 16);
            g = Integer.parseInt(colorString.substring(1, 2), 16);
            b = Integer.parseInt(colorString.substring(2, 3), 16);
        } else if (colorString.length() == 4) {
            a = 255;
            r = Integer.parseInt(colorString.substring(0, 2), 16);
            g = r;
            r = 0;
            b = Integer.parseInt(colorString.substring(2, 4), 16);
        } else if (colorString.length() == 5) {
            a = 255;
            r = Integer.parseInt(colorString.substring(0, 1), 16);
            g = Integer.parseInt(colorString.substring(1, 3), 16);
            b = Integer.parseInt(colorString.substring(3, 5), 16);
        } else if (colorString.length() == 6) {
            a = 255;
            r = Integer.parseInt(colorString.substring(0, 2), 16);
            g = Integer.parseInt(colorString.substring(2, 4), 16);
            b = Integer.parseInt(colorString.substring(4, 6), 16);
        } else if (colorString.length() == 7) {
            a = Integer.parseInt(colorString.substring(0, 1), 16);
            r = Integer.parseInt(colorString.substring(1, 3), 16);
            g = Integer.parseInt(colorString.substring(3, 5), 16);
            b = Integer.parseInt(colorString.substring(5, 7), 16);
        } else if (colorString.length() == 8) {
            a = Integer.parseInt(colorString.substring(0, 2), 16);
            r = Integer.parseInt(colorString.substring(2, 4), 16);
            g = Integer.parseInt(colorString.substring(4, 6), 16);
            b = Integer.parseInt(colorString.substring(6, 8), 16);
        } else {
            b = -1;
            g = -1;
            r = -1;
            a = -1;
        }
        return Color.argb(a, r, g, b);
    }

    View createPresetsView() {
        View contentView = View.inflate(getActivity(), R.layout.dialog_presets, null);
        shadesLayout = contentView.findViewById(R.id.shades_layout);
        transparencySeekBar = contentView.findViewById(R.id.transparency_seekbar);
        transparencyPercText = contentView.findViewById(R.id.transparency_text);
        GridView gridView = contentView.findViewById(R.id.gridView);

        loadPresets();

        if (showColorShades) {
            createColorShades(color);
        } else {
            shadesLayout.setVisibility(View.GONE);
            contentView.findViewById(R.id.shades_divider).setVisibility(View.GONE);
        }

        adapter = new ColorPaletteAdapter(new ColorPaletteAdapter.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int newColor) {
                if (color == newColor) {
                    // Double tab selects the color
                    ColorPickerDialog.this.onColorSelected(color);
                    dismiss();
                    return;
                }
                color = newColor;
                if (showColorShades) {
                    createColorShades(color);
                }
            }
        }, presets, getSelectedItemPosition(), colorShape);

        gridView.setAdapter(adapter);

        if (showAlphaSlider) {
            setupTransparency();
        } else {
            contentView.findViewById(R.id.transparency_layout).setVisibility(View.GONE);
            contentView.findViewById(R.id.transparency_title).setVisibility(View.GONE);
        }

        return contentView;
    }

    private void loadPresets() {
        int alpha = Color.alpha(color);
        presets = getArguments().getIntArray(ARG_PRESETS);
        if (presets == null) presets = MATERIAL_COLORS;
        boolean isMaterialColors = presets == MATERIAL_COLORS;
        presets = Arrays.copyOf(presets, presets.length); // don't update the original array when modifying alpha
        if (alpha != 255) {
            // add alpha to the presets
            for (int i = 0; i < presets.length; i++) {
                int color = presets[i];
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);
                presets[i] = Color.argb(alpha, red, green, blue);
            }
        }
        presets = unshiftIfNotExists(presets, color);
        int initialColor = getArguments().getInt(ARG_COLOR);
        if (initialColor != color) {
            // The user clicked a color and a configuration change occurred. Make sure the initial color is in the presets
            presets = unshiftIfNotExists(presets, initialColor);
        }
        if (isMaterialColors && presets.length == 19) {
            // Add black to have a total of 20 colors if the current color is in the material color palette
            presets = pushIfNotExists(presets, Color.argb(alpha, 0, 0, 0));
        }
    }

    void createColorShades(@ColorInt final int color) {
        final int[] colorShades = getColorShades(color);

        if (shadesLayout.getChildCount() != 0) {
            for (int i = 0; i < shadesLayout.getChildCount(); i++) {
                FrameLayout layout = (FrameLayout) shadesLayout.getChildAt(i);
                final ColorPanelView cpv = layout.findViewById(R.id.color_panel_view);
                ImageView iv = layout.findViewById(R.id.color_image_view);
                cpv.setColor(colorShades[i]);
                cpv.setTag(false);
                iv.setImageDrawable(null);
            }
            return;
        }

        final int horizontalPadding = getResources().getDimensionPixelSize(R.dimen.item_horizontal_padding);

        for (final int colorShade : colorShades) {
            int layoutResId;
            if (colorShape == ColorShape.SQUARE) {
                layoutResId = R.layout.color_item_square;
            } else {
                layoutResId = R.layout.color_item_circle;
            }

            final View view = View.inflate(getActivity(), layoutResId, null);
            final ColorPanelView colorPanelView = view.findViewById(R.id.color_panel_view);

            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) colorPanelView.getLayoutParams();
            params.leftMargin = params.rightMargin = horizontalPadding;
            colorPanelView.setLayoutParams(params);
            colorPanelView.setColor(colorShade);
            shadesLayout.addView(view);

            colorPanelView.post(new Runnable() {
                @Override
                public void run() {
                    // The color is black when rotating the dialog. This is a dirty fix. WTF!?
                    colorPanelView.setColor(colorShade);
                }
            });

            colorPanelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() instanceof Boolean && (Boolean) v.getTag()) {
                        onColorSelected(ColorPickerDialog.this.color);
                        dismiss();
                        return; // already selected
                    }
                    ColorPickerDialog.this.color = colorPanelView.getColor();
                    adapter.selectNone();
                    for (int i = 0; i < shadesLayout.getChildCount(); i++) {
                        FrameLayout layout = (FrameLayout) shadesLayout.getChildAt(i);
                        ColorPanelView cpv = layout.findViewById(R.id.color_panel_view);
                        ImageView iv = layout.findViewById(R.id.color_image_view);
                        iv.setImageResource(cpv == v ? R.drawable.preset_checked : 0);
                        if (cpv == v && ColorUtils.calculateLuminance(cpv.getColor()) >= 0.65
                                || Color.alpha(cpv.getColor()) <= ALPHA_THRESHOLD) {
                            iv.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                        } else {
                            iv.setColorFilter(null);
                        }
                        cpv.setTag(cpv == v);
                    }
                }
            });
            colorPanelView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    colorPanelView.showHint();
                    return true;
                }
            });
        }
    }

    private void onColorSelected(int color) {
        if (colorPickerDialogListener != null) {
            Log.w(TAG, "Using deprecated listener which may be remove in future releases");
            colorPickerDialogListener.onColorSelected(dialogId, color);
            return;
        }
        Activity activity = getActivity();
        if (activity instanceof ColorPickerDialogListener) {
            ((ColorPickerDialogListener) activity).onColorSelected(dialogId, color);
        } else {
            throw new IllegalStateException("The activity must implement ColorPickerDialogListener");
        }
    }

    private void onDialogDismissed() {
        if (colorPickerDialogListener != null) {
            Log.w(TAG, "Using deprecated listener which may be remove in future releases");
            colorPickerDialogListener.onDialogDismissed(dialogId);
            return;
        }
        Activity activity = getActivity();
        if (activity instanceof ColorPickerDialogListener) {
            ((ColorPickerDialogListener) activity).onDialogDismissed(dialogId);
        }
    }

    private int shadeColor(@ColorInt int color, double percent) {
        String hex = String.format("#%06X", (0xFFFFFF & color));
        long f = Long.parseLong(hex.substring(1), 16);
        double t = percent < 0 ? 0 : 255;
        double p = percent < 0 ? percent * -1 : percent;
        long R = f >> 16;
        long G = f >> 8 & 0x00FF;
        long B = f & 0x0000FF;
        int alpha = Color.alpha(color);
        int red = (int) (Math.round((t - R) * p) + R);
        int green = (int) (Math.round((t - G) * p) + G);
        int blue = (int) (Math.round((t - B) * p) + B);
        return Color.argb(alpha, red, green, blue);
    }

    private int[] getColorShades(@ColorInt int color) {
        return new int[]{
                shadeColor(color, 0.9), shadeColor(color, 0.7), shadeColor(color, 0.5), shadeColor(color, 0.333),
                shadeColor(color, 0.166), shadeColor(color, -0.125), shadeColor(color, -0.25), shadeColor(color, -0.375),
                shadeColor(color, -0.5), shadeColor(color, -0.675), shadeColor(color, -0.7), shadeColor(color, -0.775),
        };
    }

    private void setupTransparency() {
        int progress = 255 - Color.alpha(color);
        transparencySeekBar.setMax(255);
        transparencySeekBar.setProgress(progress);
        int percentage = (int) ((double) progress * 100 / 255);
        transparencyPercText.setText(String.format(Locale.ENGLISH, "%d%%", percentage));
        transparencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int percentage = (int) ((double) progress * 100 / 255);
                transparencyPercText.setText(String.format(Locale.ENGLISH, "%d%%", percentage));
                int alpha = 255 - progress;
                // update items in GridView:
                for (int i = 0; i < adapter.colors.length; i++) {
                    int color = adapter.colors[i];
                    int red = Color.red(color);
                    int green = Color.green(color);
                    int blue = Color.blue(color);
                    adapter.colors[i] = Color.argb(alpha, red, green, blue);
                }
                adapter.notifyDataSetChanged();
                // update shades:
                for (int i = 0; i < shadesLayout.getChildCount(); i++) {
                    FrameLayout layout = (FrameLayout) shadesLayout.getChildAt(i);
                    ColorPanelView cpv = layout.findViewById(R.id.color_panel_view);
                    ImageView iv = layout.findViewById(R.id.color_image_view);
                    if (layout.getTag() == null) {
                        // save the original border color
                        layout.setTag(cpv.getBorderColor());
                    }
                    int color = cpv.getColor();
                    color = Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
                    if (alpha <= ALPHA_THRESHOLD) {
                        cpv.setBorderColor(color | 0xFF000000);
                    } else {
                        cpv.setBorderColor((int) layout.getTag());
                    }
                    if (cpv.getTag() != null && (Boolean) cpv.getTag()) {
                        // The alpha changed on the selected shaded color. Update the checkmark color filter.
                        if (alpha <= ALPHA_THRESHOLD) {
                            iv.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                        } else {
                            if (ColorUtils.calculateLuminance(color) >= 0.65) {
                                iv.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                            } else {
                                iv.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                            }
                        }
                    }
                    cpv.setColor(color);
                }
                // update color:
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);
                color = Color.argb(alpha, red, green, blue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private int[] unshiftIfNotExists(int[] array, int value) {
        boolean present = false;
        for (int i : array) {
            if (i == value) {
                present = true;
                break;
            }
        }
        if (!present) {
            int[] newArray = new int[array.length + 1];
            newArray[0] = value;
            System.arraycopy(array, 0, newArray, 1, newArray.length - 1);
            return newArray;
        }
        return array;
    }

    private int[] pushIfNotExists(int[] array, int value) {
        boolean present = false;
        for (int i : array) {
            if (i == value) {
                present = true;
                break;
            }
        }
        if (!present) {
            int[] newArray = new int[array.length + 1];
            newArray[newArray.length - 1] = value;
            System.arraycopy(array, 0, newArray, 0, newArray.length - 1);
            return newArray;
        }
        return array;
    }

    private int getSelectedItemPosition() {
        for (int i = 0; i < presets.length; i++) {
            if (presets[i] == color) {
                return i;
            }
        }
        return -1;
    }

    @IntDef({TYPE_CUSTOM, TYPE_PRESETS})
    public @interface DialogType {

    }

    public static final class Builder {

        ColorPickerDialogListener colorPickerDialogListener;
        @StringRes
        int dialogTitle = R.string.default_title;
        @StringRes
        int presetsButtonText = R.string.presets;
        @StringRes
        int customButtonText = R.string.custom;
        @StringRes
        int selectedButtonText = R.string.select;
        @DialogType
        int dialogType = TYPE_PRESETS;
        int[] presets = MATERIAL_COLORS;
        @ColorInt
        int color = Color.BLACK;
        int dialogId = 0;
        boolean showAlphaSlider = false;
        boolean allowPresets = true;
        boolean allowCustom = true;
        boolean showColorShades = true;
        @ColorShape
        int colorShape = ColorShape.CIRCLE;

        Builder() {

        }

        public Builder setDialogTitle(@StringRes int dialogTitle) {
            this.dialogTitle = dialogTitle;
            return this;
        }

        public Builder setSelectedButtonText(@StringRes int selectedButtonText) {
            this.selectedButtonText = selectedButtonText;
            return this;
        }

        public Builder setPresetsButtonText(@StringRes int presetsButtonText) {
            this.presetsButtonText = presetsButtonText;
            return this;
        }

        public Builder setCustomButtonText(@StringRes int customButtonText) {
            this.customButtonText = customButtonText;
            return this;
        }

        public Builder setDialogType(@DialogType int dialogType) {
            this.dialogType = dialogType;
            return this;
        }

        public Builder setPresets(@NonNull int[] presets) {
            this.presets = presets;
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setDialogId(int dialogId) {
            this.dialogId = dialogId;
            return this;
        }

        public Builder setShowAlphaSlider(boolean showAlphaSlider) {
            this.showAlphaSlider = showAlphaSlider;
            return this;
        }

        public Builder setAllowPresets(boolean allowPresets) {
            this.allowPresets = allowPresets;
            return this;
        }

        public Builder setAllowCustom(boolean allowCustom) {
            this.allowCustom = allowCustom;
            return this;
        }

        public Builder setShowColorShades(boolean showColorShades) {
            this.showColorShades = showColorShades;
            return this;
        }

        public Builder setColorShape(int colorShape) {
            this.colorShape = colorShape;
            return this;
        }

        public ColorPickerDialog create() {
            ColorPickerDialog dialog = new ColorPickerDialog();
            Bundle args = new Bundle();
            args.putInt(ARG_ID, dialogId);
            args.putInt(ARG_TYPE, dialogType);
            args.putInt(ARG_COLOR, color);
            args.putIntArray(ARG_PRESETS, presets);
            args.putBoolean(ARG_ALPHA, showAlphaSlider);
            args.putBoolean(ARG_ALLOW_CUSTOM, allowCustom);
            args.putBoolean(ARG_ALLOW_PRESETS, allowPresets);
            args.putInt(ARG_DIALOG_TITLE, dialogTitle);
            args.putBoolean(ARG_SHOW_COLOR_SHADES, showColorShades);
            args.putInt(ARG_COLOR_SHAPE, colorShape);
            args.putInt(ARG_PRESETS_BUTTON_TEXT, presetsButtonText);
            args.putInt(ARG_CUSTOM_BUTTON_TEXT, customButtonText);
            args.putInt(ARG_SELECTED_BUTTON_TEXT, selectedButtonText);
            dialog.setArguments(args);
            return dialog;
        }

        public void show(FragmentActivity activity) {
            create().show(activity.getSupportFragmentManager(), "color-picker-dialog");
        }
    }
}
