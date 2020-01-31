package com.dvinfosys.WidgetsExample.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.TextView.CustomTextView;
import com.dvinfosys.widgets.ToastView.DToast;

public class DToastFragment extends Fragment {

    private CustomTextView tvDToast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dtoast, container, false);
        tvDToast = v.findViewById(R.id.tv_d_toast);
        tvDToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DToast.makeToast((Activity) getContext(), "Some random text here", DToast.LENGTHLONG).show();
            }
        });
        return v;
    }

}
