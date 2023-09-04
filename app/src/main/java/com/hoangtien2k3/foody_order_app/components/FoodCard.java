package com.hoangtien2k3.foody_order_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.repositoryInit.DatabaseHandler;
import com.hoangtien2k3.foody_order_app.model.Food;


public class FoodCard extends LinearLayout implements BaseComponent{
    private Food food;
    private Double defaultPrice;
    private String restaurantName;
    private ImageView image;
    private TextView tvName, tvPrice, tvRestaurantName;

    public FoodCard(Context context, Food food, Double defaultPrice, String restaurantName){
        super(context);
        this.food = food;
        this.defaultPrice = defaultPrice;
        this.restaurantName = restaurantName;
        initControl(context);
    }

    public FoodCard(Context context) {
        super(context);
        initControl(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageFood);
        tvName = findViewById(R.id.tvNameFood);
        tvPrice = findViewById(R.id.tvPriceFood);
        tvRestaurantName = findViewById(R.id.tvFoodRestaurantName);
    }

    @Override
    public void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_food_card, this);

        initUI();

        // Set information for food cart
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(food.getImage()));
        tvName.setText(food.getName());
        tvPrice.setText(String.format("%s VNĐ", Math.round(defaultPrice)));
        tvRestaurantName.setText(restaurantName);
    }
}