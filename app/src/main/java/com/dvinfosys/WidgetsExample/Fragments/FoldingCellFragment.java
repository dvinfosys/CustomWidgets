package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dvinfosys.WidgetsExample.Adapter.FoldingCellListAdapter;
import com.dvinfosys.WidgetsExample.Model.Item;
import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.foldingcell.FoldingCell;

import java.util.ArrayList;

public class FoldingCellFragment extends Fragment {

    public FoldingCellFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_folding_cell, container, false);
        ListView theListView = v.findViewById(R.id.mainListView);
        final ArrayList<Item> items = Item.getTestingList();
        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });

        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getContext(), items);

        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });
        theListView.setAdapter(adapter);
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                ((FoldingCell) view).toggle(false);

                adapter.registerToggle(pos);
            }
        });

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Folding Cell Example");
    }
}
