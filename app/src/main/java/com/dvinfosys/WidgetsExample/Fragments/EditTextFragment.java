package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.EditText.EditTextSpinner;
import com.dvinfosys.widgets.EditText.ZoomEditTextView;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class EditTextFragment extends Fragment {

    private ZoomEditTextView edtZoom;
    private EditTextSpinner edtSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_text, container, false);
        edtZoom = view.findViewById(R.id.edt_zoom_edittext);
        edtSpinner=view.findViewById(R.id.edt_editSpinner);

        edtZoom.setOnFocusChangeListener(new MyFocusChangeListener());
        edtZoom.setFocusableInTouchMode(true);
        edtZoom.setTextIsSelectable(true);
        edtZoom.setRawInputType(InputType.TYPE_CLASS_TEXT);

        ArrayList<String> options = new ArrayList<>();
        options.add("Option 1");
        options.add("Option 2");
        options.add("Option 3");
        options.add("Option 4");

        edtSpinner.setOptions(options);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("EditText Example");
    }

    private class MyFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            if (hasFocus) {
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                v.requestFocus();
                inputMethodManager.showSoftInput(v, 0);
            }
        }
    }
}
