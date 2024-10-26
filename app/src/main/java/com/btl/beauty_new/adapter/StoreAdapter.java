package com.btl.beauty_new.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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
    private final List<Store> storeList;

    public StoreAdapter(List<Store> storeList) {
        this.storeList = storeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageStore;
        private final TextView tvStoreName_res_cart, tvStoreAddress_res_cart;
        private CheckBox btnSavedShop;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageStore = itemView.findViewById(R.id.imageStore);
            tvStoreName_res_cart = itemView.findViewById(R.id.tvStoreName_res_cart);
            tvStoreAddress_res_cart = itemView.findViewById(R.id.tvStoreAddress_res_cart);
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
    public int getItemCount() {
        return storeList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.imageStore.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(store.getImage()));
        holder.tvStoreName_res_cart.setText(store.getName());
        holder.tvStoreAddress_res_cart.setText(store.getAddress());

        // hiển thị chi tiết FragmentCategory
        holder.imageStore.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CategoryActivity.class);
            // dùng putExtra để đẩy Id sang Fragment khác cũng là cách hay
            intent.putExtra("storeId", store.getId());
            v.getContext().startActivity(intent);
        });


        // Màu đậm và nhạt cho checkbox
        int colorChecked = Color.parseColor("#FF6200EE"); // Màu đậm
        int colorUnchecked = Color.parseColor("#A3A3A3"); // Màu nhạt

        // Kiểm tra trạng thái checkbox và thay đổi màu ban đầu
        if (HomeActivity.dao.isStoreSaved(store.getId(), HomeActivity.user.getId())) {
            holder.btnSavedShop.setChecked(true);
            holder.btnSavedShop.setButtonTintList(ColorStateList.valueOf(colorChecked));
        } else {
            holder.btnSavedShop.setChecked(false);
            holder.btnSavedShop.setButtonTintList(ColorStateList.valueOf(colorUnchecked));
        }

        holder.btnSavedShop.setOnClickListener(v -> {
            Context context = v.getContext();
            int storeId = store.getId();
            int userId = HomeActivity.user.getId();

            if (HomeActivity.dao.isStoreSaved(storeId, userId)) {
                // Bỏ lưu và cập nhật màu
                if (HomeActivity.dao.deleteStoreSaved(new StoreSaved(storeId, userId))) {
                    Toast.makeText(context, "Đã bỏ lưu cửa hàng!", Toast.LENGTH_SHORT).show();
                    holder.btnSavedShop.setChecked(false);
                    holder.btnSavedShop.setButtonTintList(ColorStateList.valueOf(colorUnchecked)); // Đổi sang màu nhạt
                } else {
                    Toast.makeText(context, "Không thể bỏ lưu cửa hàng error!", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Lưu và cập nhật màu
                if (HomeActivity.dao.addStoreSaved(new StoreSaved(storeId, userId))) {
                    Toast.makeText(context, "Lưu thông tin cửa hàng thành công!", Toast.LENGTH_SHORT).show();
                    holder.btnSavedShop.setChecked(true);
                    holder.btnSavedShop.setButtonTintList(ColorStateList.valueOf(colorChecked)); // Đổi sang màu đậm
                } else {
                    Toast.makeText(context, "Bạn đã lưu thông tin nhà hàng này rồi!", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

}
