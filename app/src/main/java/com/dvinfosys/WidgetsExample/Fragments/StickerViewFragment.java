package com.dvinfosys.WidgetsExample.Fragments;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.Button.CustomButton;
import com.dvinfosys.widgets.Sticker.BitmapStickerIcon;
import com.dvinfosys.widgets.Sticker.DeleteIconEvent;
import com.dvinfosys.widgets.Sticker.DrawableSticker;
import com.dvinfosys.widgets.Sticker.FlipBothDirectionsEvent;
import com.dvinfosys.widgets.Sticker.Sticker;
import com.dvinfosys.widgets.Sticker.StickerView;
import com.dvinfosys.widgets.Sticker.TextSticker;
import com.dvinfosys.widgets.Sticker.ZoomIconEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class StickerViewFragment extends Fragment {

    private StickerView stickerView;
    private CustomButton btnAddSticker, btnAddTextSticker, btnSave;
    private int[] sticker = {R.drawable.test1, R.drawable.test2, R.drawable.test3, R.drawable.test4, R.drawable.test5, R.drawable.test6};
    private String TAG = "StickerExample";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sticker_view, container, false);
        stickerView = v.findViewById(R.id.sv_example);
        btnAddTextSticker = v.findViewById(R.id.btn_add_text_sticker);
        btnAddSticker = v.findViewById(R.id.btn_add_sticker);
        btnSave = v.findViewById(R.id.btn_save);

        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(),
                com.dvinfosys.widgets.R.drawable.ic_remove),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(),
                com.dvinfosys.widgets.R.drawable.ic_zoom),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(getContext(),
                com.dvinfosys.widgets.R.drawable.ic_flip),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipBothDirectionsEvent());
        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon));
        stickerView.setLocked(false);
        stickerView.setConstrained(true);

        btnAddSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                Drawable drawable = ContextCompat.getDrawable(getContext(), sticker[rand.nextInt(sticker.length)]);
                stickerView.addSticker(new DrawableSticker(drawable));
            }
        });

        btnAddTextSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextSticker sticker = new TextSticker(getContext());
                sticker.setText("Hello, world!");
                sticker.setTextColor(Color.BLACK);
                sticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                sticker.resizeText();
                stickerView.addSticker(sticker);
            }
        });

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerAdded");
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                //stickerView.removeAllSticker();
                if (sticker instanceof TextSticker) {
                    ((TextSticker) sticker).setTextColor(Color.RED);
                    stickerView.replace(sticker);
                    stickerView.invalidate();
                }
                Log.d(TAG, "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDragFinished");
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                Log.d(TAG, "onDoubleTapped: double tap will be with two click");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickerView.setLocked(true);
                File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/" + getResources().getString(R.string.app_name));
                if (!wallpaperDirectory.exists()) {
                    wallpaperDirectory.mkdirs();
                }
                String currentDateAndTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                File file = new File(wallpaperDirectory, "Sticker_" + currentDateAndTime + ".png");
                stickerView.save(file);
                Toast.makeText(getContext(), "Image save success! \n Go to :" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("StickerView Example");
    }

}
