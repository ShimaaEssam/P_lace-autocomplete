package com.shimaa.placeautocomplete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PlacesRecyclerViewAdapter extends RecyclerView.Adapter<PlacesRecyclerViewAdapter.PlacesViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    PlacesRecyclerViewAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        Log.d("LIST", data.size()+"");
    }

    // inflates the row layout from xml when needed
    @Override
    public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.selectedplaces_row, parent, false);
        PlacesViewHolder holder =new PlacesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        String place = mData.get(position);
        holder.myTextView.setText(place);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class PlacesViewHolder extends RecyclerView.ViewHolder  {
        TextView myTextView;

        PlacesViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.placeitem);
        }
    }
}