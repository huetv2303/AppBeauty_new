package com.hoangtien2k3.foody_order_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.hoangtien2k3.foody_order_app.repositoryInit.DatabaseHandler;
import com.hoangtien2k3.foody_order_app.fragments.SavedFragment;
import com.hoangtien2k3.foody_order_app.model.Restaurant;
import com.hoangtien2k3.foody_order_app.model.RestaurantSaved;

public class RestaurantCard extends LinearLayout implements BaseComponent{
    private Restaurant restaurant;
    private boolean isSaved;
    private ImageView image;
    private TextView tvRestaurantName, tvRestaurantAddress;
    private Button btnSaved;

    public RestaurantCard(Context context, Restaurant restaurant, boolean isSaved) {
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
        btnSaved = findViewById(R.id.btnSavedRestaurant);
    }

    @Override
    public void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_restaurant_card, this);

        initUI();

        if(isSaved){
            btnSaved.setText("BỎ LƯU");   // Thẻ được lưu
        }
        btnSaved.setOnClickListener(view ->{
            if(isSaved){
                if(HomeActivity.dao.deleteRestaurantSaved(new RestaurantSaved(restaurant.getId(), HomeActivity.user.getId()))){
                    Toast.makeText(context, "Đã bỏ lưu thông tin nhà hàng!", Toast.LENGTH_SHORT).show();
                    SavedFragment.saved_container.removeView(this);
                } else {
                    Toast.makeText(context, "Có lỗi khi xóa thông tin!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(HomeActivity.dao.addRestaurantSaved(new RestaurantSaved(restaurant.getId(), HomeActivity.user.getId()))){
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

