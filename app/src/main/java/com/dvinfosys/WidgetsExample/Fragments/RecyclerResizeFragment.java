package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvinfosys.WidgetsExample.Adapter.RecyclerResizeAdapter;
import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.Recycler.ResizeScrollListener;

import java.util.ArrayList;

public class RecyclerResizeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_resize, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerResizeAdapter customAdapter = new RecyclerResizeAdapter(getContext(), 80);
        customAdapter.addItems(addItems());
        if (recyclerView != null) {
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(customAdapter);
            recyclerView.addOnScrollListener(new ResizeScrollListener<>(customAdapter, linearLayoutManager));
        }
        return v;
    }

    private ArrayList<String> addItems() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("" + (i + 1));
        }
        return list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Recycler Resize Example");
    }

}
