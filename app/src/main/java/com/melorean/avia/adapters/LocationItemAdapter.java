package com.melorean.avia.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melorean.avia.R;
import com.melorean.avia.model.LocationItem;

import java.util.List;

public class LocationItemAdapter extends RecyclerView.Adapter<LocationItemAdapter.LocationItemHolder> {

    private List<LocationItem> locationItems;

    public LocationItemAdapter(List<LocationItem> locationItems) {
        this.locationItems = locationItems;
    }

    @NonNull
    @Override
    public LocationItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_item_layout, viewGroup, false);
        return new LocationItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationItemHolder locationItemHolder, int i) {
        locationItemHolder.bind(locationItems.get(i));
    }

    @Override
    public int getItemCount() {
        return locationItems.size();
    }

    class LocationItemHolder extends RecyclerView.ViewHolder {
        TextView tvLocName;
        TextView tvLocCode;

        LocationItemHolder(@NonNull View itemView) {
            super(itemView);
            tvLocName = itemView.findViewById(R.id.location_item_layout___loc_name);
            tvLocCode = itemView.findViewById(R.id.location_item_layout___loc_code);
        }

        void bind(LocationItem locationItem) {
            tvLocName.setText(locationItem.getLocName() == null ? locationItem.getNameTranslation().getEn() : locationItem.getLocName());
            tvLocCode.setText(locationItem.getLocCode());
        }
    }


}
