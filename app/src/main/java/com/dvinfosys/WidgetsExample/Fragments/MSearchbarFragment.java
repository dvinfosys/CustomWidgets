package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.MaterialSearchbar.MSearchBar;

public class MSearchbarFragment extends Fragment implements MSearchBar.OnSearchActionListener{

    private MSearchBar searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_msearchbar, container, false);
        searchBar = v.findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        searchBar.setCardViewElevation(10);
        return v;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MSearchBar.BUTTON_NAVIGATION:
                break;
            case MSearchBar.BUTTON_SPEECH:
                searchBar.disableSearch();
                break;
            case MSearchBar.BUTTON_BACK:
                searchBar.disableSearch();
                break;
        }
    }
}
