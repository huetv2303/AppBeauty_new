package com.btl.beauty_new.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.beauty_new.activity.ActivityImpl.CategoryActivity;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.R;
import com.btl.beauty_new.model.StoreSaved;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.model.Store;


import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private final List<Store> restaurantList;

    public StoreAdapter(List<Store> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageRestaurant;
        private final TextView tvRestaurantName_res_cart, tvRestaurantAddress_res_cart;
        private CheckBox btnSavedShop;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRestaurant = itemView.findViewById(R.id.imageRestaurant);
            tvRestaurantName_res_cart = itemView.findViewById(R.id.tvRestaurantName_res_cart);
            tvRestaurantAddress_res_cart = itemView.findViewById(R.id.tvRestaurantAddress_res_cart);
            btnSavedShop = itemView.findViewById(R.id.btnSavedShop);
        }
    }

    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_store_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {
        Store restaurant = restaurantList.get(position);
        holder.imageRestaurant.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(restaurant.getImage()));
        holder.tvRestaurantName_res_cart.setText(restaurant.getName());
        holder.tvRestaurantAddress_res_cart.setText(restaurant.getAddress());

        // hiển thị chi tiết FragmentCategory
        holder.imageRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CategoryActivity.class);
            // dùng putExtra để đẩy Id sang Fragment khác cũng là cách hay
            intent.putExtra("restaurantId", restaurant.getId());
            v.getContext().startActivity(intent);
        });

        // lưu thông tin cửa hàng vào danh sách chờ
        holder.btnSavedShop.setOnClickListener(v -> {
            Context context = v.getContext(); // Lấy context từ View v
            if(HomeActivity.dao.addStoreSaved(new StoreSaved(restaurant.getId(), HomeActivity.user.getId()))){
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
