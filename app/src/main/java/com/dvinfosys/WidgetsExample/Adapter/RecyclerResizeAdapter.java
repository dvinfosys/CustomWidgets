package com.dvinfosys.WidgetsExample.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.Recycler.ResizeAdapter;

import java.util.ArrayList;

public class RecyclerResizeAdapter extends ResizeAdapter<RecyclerView.ViewHolder> {

    private ArrayList<String> list;

    public RecyclerResizeAdapter(Context context, int height) {
        super(context, height);
        list = new ArrayList<>();
    }

    public void addItems(ArrayList<String> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    public void onItemBigResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {

    }

    @Override
    public void onItemBigResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {

    }

    @Override
    public void onItemSmallResizeScrolled(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {

    }

    @Override
    public void onItemSmallResize(RecyclerView.ViewHolder viewHolder, int position, int dyAbs) {

    }

    @Override
    public void onItemInit(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    public int getFooterItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_resize_item, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        setData((CustomViewHolder) holder,list.get(position));
    }
    public void setData(CustomViewHolder holder,String data){
        holder.titleTextView.setText(data);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        public CustomViewHolder(View v) {
            super(v);
            titleTextView = v.findViewById(R.id.title_custom_item);
        }
    }
}
