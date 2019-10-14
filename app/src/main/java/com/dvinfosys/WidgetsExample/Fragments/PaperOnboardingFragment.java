package com.dvinfosys.WidgetsExample.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.paperonboarding.PaperOnboardingEngine;
import com.dvinfosys.widgets.paperonboarding.PaperOnboardingPage;
import com.dvinfosys.widgets.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.dvinfosys.widgets.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class PaperOnboardingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.onboarding_main_layout, container, false);

        PaperOnboardingEngine engine = new PaperOnboardingEngine(v.findViewById(R.id.onboardingRootView), getDataForOnboarding(), getContext());
        engine.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldElementIndex, int newElementIndex) {
                Toast.makeText(getContext(), "Swiped from " + oldElementIndex + " to " + newElementIndex, Toast.LENGTH_SHORT).show();
            }
        });

        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                // Probably here will be your exit action
                Toast.makeText(getContext(), "Swiped out right", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Hotels", "All hotels and hostels are sorted by hospitality rating",
                Color.parseColor("#678FB4"), R.drawable.hotels, R.drawable.key);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Banks", "We carefully verify all banks before add them into the app",
                Color.parseColor("#65B0B4"), R.drawable.banks, R.drawable.wallet);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("Stores", "All local stores are categorized for your convenience",
                Color.parseColor("#9B90BC"), R.drawable.stores, R.drawable.shopping_cart);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Paper On Boarding Example");
    }
}
