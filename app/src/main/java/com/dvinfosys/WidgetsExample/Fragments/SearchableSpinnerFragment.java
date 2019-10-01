package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dvinfosys.WidgetsExample.Adapter.SimpleListAdapter;
import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.SearchableSpinner.SearchableSpinner;
import com.dvinfosys.widgets.SearchableSpinner.interfaces.IStatusListener;
import com.dvinfosys.widgets.SearchableSpinner.interfaces.OnItemSelectedListener;

import java.util.ArrayList;

public class SearchableSpinnerFragment extends Fragment {

    private SearchableSpinner mSearchableSpinner;
    private ArrayList<String> mStrings = new ArrayList<>();
    private SimpleListAdapter mSimpleListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListValues();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_searchable_spinner, container, false);
        mSimpleListAdapter = new SimpleListAdapter(getContext(), mStrings);
        mSearchableSpinner = v.findViewById(R.id.SearchableSpinner);
        mSearchableSpinner.setAdapter(mSimpleListAdapter);
        mSearchableSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                Toast.makeText(getContext(), "Item on position " + position + " : " + mSimpleListAdapter.getItem(position) + " Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
                Toast.makeText(getContext(), "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });
        mSearchableSpinner.setStatusListener(new IStatusListener() {
            @Override
            public void spinnerIsOpening() {
                /*mSearchableSpinner1.hideEdit();
                mSearchableSpinner2.hideEdit();*/
            }

            @Override
            public void spinnerIsClosing() {

            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Searchable Spinner Example");
    }

    private void initListValues() {
        mStrings = new ArrayList<>();
        mStrings.add("Brigida Kurz");
        mStrings.add("Tracy Mckim");
        mStrings.add("Iesha Davids");
        mStrings.add("Ozella Provenza");
        mStrings.add("Florentina Carriere");
        mStrings.add("Geri Eiler");
        mStrings.add("Tammara Belgrave");
        mStrings.add("Ashton Ridinger");
        mStrings.add("Jodee Dawkins");
        mStrings.add("Florine Cruzan");
        mStrings.add("Latia Stead");
        mStrings.add("Kai Urbain");
        mStrings.add("Liza Chi");
        mStrings.add("Clayton Laprade");
        mStrings.add("Wilfredo Mooney");
        mStrings.add("Roseline Cain");
        mStrings.add("Chadwick Gauna");
        mStrings.add("Carmela Bourn");
        mStrings.add("Valeri Dedios");
        mStrings.add("Calista Mcneese");
        mStrings.add("Willard Cuccia");
        mStrings.add("Ngan Blakey");
        mStrings.add("Reina Medlen");
        mStrings.add("Fabian Steenbergen");
        mStrings.add("Edmond Pine");
        mStrings.add("Teri Quesada");
        mStrings.add("Vernetta Fulgham");
        mStrings.add("Winnifred Kiefer");
        mStrings.add("Chiquita Lichty");
        mStrings.add("Elna Stiltner");
        mStrings.add("Carly Landon");
        mStrings.add("Hans Morford");
        mStrings.add("Shawanna Kapoor");
        mStrings.add("Thomasina Naron");
        mStrings.add("Melba Massi");
        mStrings.add("Sal Mangano");
        mStrings.add("Mika Weitzel");
        mStrings.add("Phylis France");
        mStrings.add("Illa Winzer");
        mStrings.add("Kristofer Boyden");
        mStrings.add("Idalia Cryan");
        mStrings.add("Jenni Sousa");
        mStrings.add("Eda Forkey");
        mStrings.add("Birgit Rispoli");
        mStrings.add("Janiece Mcguffey");
        mStrings.add("Barton Busick");
        mStrings.add("Gerald Westerman");
        mStrings.add("Shalanda Baran");
        mStrings.add("Margherita Pazos");
        mStrings.add("Yuk Fitts");
    }

}
