package com.hoangtien2k3.foody_order_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.model.Restaurant;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private final List<Restaurant> restaurantList;

    public PopularAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_popular, parent, false);

        return new PopularAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
//        holder.pic.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(restaurant.getImage()));
        holder.title.setText(restaurant.getName());
        holder.address.setText(restaurant.getAddress());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}
