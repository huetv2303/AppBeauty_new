package com.btl.beauty_new.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.fragments.SavedFragment;
import com.btl.beauty_new.model.Store;
import com.btl.beauty_new.model.StoreSaved;

public class RestaurantCard extends LinearLayout implements BaseComponent{
    private Store restaurant;
    private boolean isSaved;
    private ImageView image;
    private TextView tvRestaurantName, tvRestaurantAddress;

//    private LinearLayout btnSavedShop;
    private CheckBox btnSavedShop;

    private TextView textViewSaveShop;

    public RestaurantCard(Context context, Store restaurant, boolean isSaved) {
        super(context);
        this.restaurant = restaurant;
        this.isSaved = isSaved;
        initControl(context);
    }

    public RestaurantCard(Context context){
        super(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageRestaurant);
        tvRestaurantName = findViewById(R.id.tvRestaurantName_res_cart);
        tvRestaurantAddress = findViewById(R.id.tvRestaurantAddress_res_cart);
//        btnSavedShop = findViewById(R.id.btnSavedShop);
        btnSavedShop = findViewById(R.id.btnSavedShop);

        textViewSaveShop = findViewById(R.id.textViewSaveShop);
    }

    @Override
    public void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_store_card, this);

        initUI();

        if(isSaved){
            textViewSaveShop.setText("BỎ LƯU");
        }
        btnSavedShop.setOnClickListener(view ->{
            if(isSaved){
                if(HomeActivity.dao.deleteStoreSaved(new StoreSaved(restaurant.getId(), HomeActivity.user.getId()))){
                    Toast.makeText(context, "Đã bỏ lưu thông tin nhà hàng!", Toast.LENGTH_SHORT).show();
                    SavedFragment.saved_container.removeView(this);
                } else {
                    Toast.makeText(context, "Có lỗi khi xóa thông tin!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(HomeActivity.dao.addStoreSaved(new StoreSaved(restaurant.getId(), HomeActivity.user.getId()))){
                    Toast.makeText(context, "Lưu thông tin nhà hàng thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Bạn đã lưu thông tin nhà hàng này rồi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // set information
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(restaurant.getImage()));
        tvRestaurantName.setText(restaurant.getName());
        tvRestaurantAddress.setText(restaurant.getAddress());
    }
}

