package com.dvinfosys.WidgetsExample.Fragments;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.ToastView.ToastView;

import static android.graphics.Typeface.BOLD_ITALIC;

public class ToastViewFragment extends Fragment {

    private Button btnErrorToastView, btnSuccessToastView, btnInfoToastView, btnWarringToastView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toast_view, container, false);

        btnErrorToastView = view.findViewById(R.id.button_error_toast);
        btnSuccessToastView = view.findViewById(R.id.button_success_toast);
        btnInfoToastView = view.findViewById(R.id.button_info_toast);
        btnWarringToastView = view.findViewById(R.id.button_warning_toast);

        btnErrorToastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.error(getContext(), "This is error ToastView", ToastView.LENGTH_SHORT).show();
            }
        });
        btnWarringToastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.warning(getContext(), "This is warring ToastView", ToastView.LENGTH_SHORT).show();
            }
        });
        btnInfoToastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.info(getContext(), "This is info ToastView", ToastView.LENGTH_SHORT).show();
            }
        });

        btnSuccessToastView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastView.success(getContext(), "This is success ToastView", ToastView.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastView.normal(getContext(), "normal message without icon").show();
            }
        });
        view.findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable icon = getResources().getDrawable(R.drawable.menu);
                ToastView.normal(getContext(), "normal message with icon", icon).show();
            }
        });
        view.findViewById(R.id.button_info_toast_with_formatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastView.info(getContext(), getFormattedMessage()).show();
            }
        });
        view.findViewById(R.id.button_custom_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastView.Config.getInstance()
                        .setToastTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/Smoothy.otf"))
                        .allowQueue(false)
                        .apply();
                ToastView.custom(getContext(), R.string.custom_message, getResources().getDrawable(R.drawable.menu),
                        android.R.color.black, android.R.color.holo_green_light, ToastView.LENGTH_SHORT, true, true).show();
                ToastView.Config.reset(); // Use this if you want to use the configuration above only once
            }
        });

        return view;
    }

    private CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("ToastView Example");
    }

}
