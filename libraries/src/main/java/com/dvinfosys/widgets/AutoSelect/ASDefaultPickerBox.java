package com.dvinfosys.widgets.AutoSelect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvinfosys.widgets.R;

public class ASDefaultPickerBox extends ASAbstractPickerBox<String> {

    private TextView textView;
    private ViewGroup cellRoot;

    public ASDefaultPickerBox(@NonNull Context context) {
        this(context, null);

    }

    public ASDefaultPickerBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ASDefaultPickerBox(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert mInflater != null;
        mInflater.inflate(R.layout.as_default_picker_box, this, true);
        this.setClipChildren(false);
        this.setClipToPadding(false);
    }

    void setCellTextSize(int cellTextSize) {
        if (cellTextSize > 0)
            this.textView.setTextSize(cellTextSize);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.textView = findViewById(R.id.ds_default_cell_text);
        this.cellRoot = findViewById(R.id.ds_default_cell_root);
        this.cellRoot.setMinimumHeight(this.getHeight());
    }

    @Override
    public void onSelect(String value, int index) {
        this.textView.setText(value);
    }

    @Override
    public View getCellRoot() {
        return this.cellRoot;
    }

}