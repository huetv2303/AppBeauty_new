package com.hoangtien2k3.foody_order_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.CategoryActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.model.RestaurantSaved;
import com.hoangtien2k3.foody_order_app.repositoryInit.DatabaseHandler;
import com.hoangtien2k3.foody_order_app.model.Restaurant;


import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private final List<Restaurant> restaurantList;

    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageRestaurant;
        private final TextView tvRestaurantName_res_cart, tvRestaurantAddress_res_cart;
        private final Button btnSavedRestaurant;
        private final LinearLayout linearlayout_restaurant;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRestaurant = itemView.findViewById(R.id.imageRestaurant);
            tvRestaurantName_res_cart = itemView.findViewById(R.id.tvRestaurantName_res_cart);
            tvRestaurantAddress_res_cart = itemView.findViewById(R.id.tvRestaurantAddress_res_cart);
            btnSavedRestaurant = itemView.findViewById(R.id.btnSavedRestaurant);
            linearlayout_restaurant = itemView.findViewById(R.id.linearlayout_restaurant);
        }
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_restaurant_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.imageRestaurant.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(restaurant.getImage()));
        holder.tvRestaurantName_res_cart.setText(restaurant.getName());
        holder.tvRestaurantAddress_res_cart.setText(restaurant.getAddress());

        // hiển thị chi tiết FragmentCategory
        holder.linearlayout_restaurant.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CategoryActivity.class);
            // dùng putExtra để đẩy Id sang Fragment khác cũng là cách hay
            intent.putExtra("restaurantId", restaurant.getId());
            v.getContext().startActivity(intent);
        });

        // lưu thông tin cửa hàng vào danh sách chờ
        holder.btnSavedRestaurant.setOnClickListener(v -> {
            Context context = v.getContext(); // Lấy context từ View v
            if(HomeActivity.dao.addRestaurantSaved(new RestaurantSaved(restaurant.getId(), HomeActivity.user.getId()))){
                Toast.makeText(context, "Lưu thông tin nhà hàng thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Bạn đã lưu thông tin nhà hàng này rồi!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

}
