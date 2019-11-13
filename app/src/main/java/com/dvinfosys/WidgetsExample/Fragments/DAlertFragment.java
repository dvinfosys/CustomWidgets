package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.AlertDialog.DAlertDialog;

public class DAlertFragment extends Fragment implements View.OnClickListener {

    private int i = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dalert, container, false);

        v.findViewById(R.id.basic_test).setOnClickListener(this);
        v.findViewById(R.id.under_text_test).setOnClickListener(this);
        v.findViewById(R.id.error_text_test).setOnClickListener(this);
        v.findViewById(R.id.success_text_test).setOnClickListener(this);
        v.findViewById(R.id.warning_confirm_test).setOnClickListener(this);
        v.findViewById(R.id.warning_cancel_test).setOnClickListener(this);
        v.findViewById(R.id.custom_img_test).setOnClickListener(this);
        v.findViewById(R.id.progress_dialog).setOnClickListener(this);
        v.findViewById(R.id.checkbox1).setOnClickListener(this);

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basic_test:
                DAlertDialog sd = new DAlertDialog(getContext());
                sd.setTitleText("Title");
                sd.setContentText("Content");
                sd.setCancelable(true);
                sd.setCanceledOnTouchOutside(true);
                sd.show();
                break;
            case R.id.under_text_test:
                new DAlertDialog(getContext())
                        .setTitleText("Title Text")
                        .setContentText("It's pretty, isn't it?")
                        //.setContentTextSize(50)
                        .show();

                break;
            case R.id.error_text_test:
                new DAlertDialog(getContext(), DAlertDialog.ERROR_TYPE)
                        .setTitleText("Opps.")
                        .setContentText("Something went wrong!")
                        .show();
                break;
            case R.id.success_text_test:
                new DAlertDialog(getContext(), DAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("You clicked the button!")
                        .show();

                break;
            case R.id.warning_confirm_test:
                new DAlertDialog(getContext(), DAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new DAlertDialog.DAlertClickListener() {
                            @Override
                            public void onClick(DAlertDialog sDialog) {
                                sDialog.setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(DAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                break;
            case R.id.warning_cancel_test:
                new DAlertDialog(getContext(), DAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No,cancel plx!")
                        .setConfirmText("Yes,delete it!")
                        .showCancelButton(true)
                        .setCancelClickListener(new DAlertDialog.DAlertClickListener() {
                            @Override
                            public void onClick(DAlertDialog sDialog) {
                                sDialog.setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(DAlertDialog.ERROR_TYPE);
                            }
                        })
                        .setConfirmClickListener(new DAlertDialog.DAlertClickListener() {
                            @Override
                            public void onClick(DAlertDialog sDialog) {
                                sDialog
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(DAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
                break;
            case R.id.custom_img_test:
                new DAlertDialog(getContext(), DAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("DAlertDialog")
                        .setContentText("Here's a custom image.")
                        .setCustomImage(R.mipmap.ic_launcher)
                        .show();
                break;
            case R.id.progress_dialog:
                final DAlertDialog pDialog = new DAlertDialog(getContext(), DAlertDialog.PROGRESS_TYPE)
                        .setTitleText("Loading");
                pDialog.show();
                pDialog.setCancelable(false);
                new CountDownTimer(800 * 7, 800) {
                    public void onTick(long millisUntilFinished) {
                        i++;
                        switch (i) {
                            case 0:
                                pDialog.getProgressHelper().setBarColor(ContextCompat.getColor
                                        (getContext(), R.color.blue_btn_bg_color));
                                break;
                            case 1:
                                pDialog.getProgressHelper().setBarColor(ContextCompat.getColor
                                        (getContext(), R.color.material_deep_teal_50));
                                break;
                            case 2:
                            case 6:
                                pDialog.getProgressHelper().setBarColor(ContextCompat.getColor
                                        (getContext(), R.color.success_stroke_color));
                                break;
                            case 3:
                                pDialog.getProgressHelper().setBarColor(ContextCompat.getColor
                                        (getContext(), R.color.material_deep_teal_20));
                                break;
                            case 4:
                                pDialog.getProgressHelper().setBarColor(ContextCompat.getColor
                                        (getContext(), R.color.material_blue_grey_80));
                                break;
                            case 5:
                                pDialog.getProgressHelper().setBarColor(ContextCompat.getColor
                                        (getContext(), R.color.warning_stroke_color));
                                break;
                        }
                    }

                    public void onFinish() {
                        i = -1;
                        pDialog.setTitleText("Success!")
                                .setConfirmText("OK")
                                .changeAlertType(DAlertDialog.SUCCESS_TYPE);
                    }
                }.start();
                break;
            case R.id.checkbox1:
                DAlertDialog.DARK_STYLE = ((CheckBox) v).isChecked();
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("DAlert Dialog Example");
    }
}
