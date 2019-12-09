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
import com.dvinfosys.widgets.InputCode.InputCode;

import java.util.Arrays;

public class InputCodeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input_code, container, false);
        InputCode cInput = v.findViewById(R.id.pairing);
        cInput.setCodeReadyListener(new InputCode.codeReadyListener() {
            @Override
            public void onCodeReady(Character[] code) {
                Toast.makeText(getContext(), "code entered is : " + Arrays.toString(code), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("InputCode Example");
    }

}
