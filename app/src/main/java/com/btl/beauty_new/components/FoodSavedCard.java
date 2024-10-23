package com.btl.beauty_new.components;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.R;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.fragments.SavedFragment;
import com.btl.beauty_new.model.Food;
import com.btl.beauty_new.model.FoodSize;

@SuppressLint("ViewConstructor")
public class FoodSavedCard extends LinearLayout implements BaseComponent{
    private final Food food;
    private final String restaurantName;
    private final FoodSize foodSize;
    private ImageView image;
    private TextView tvName, tvSize, tvrestaurantName, tvPrice;
    private Button btnDelete;

    public FoodSavedCard(Context context, Food food, String restaurantName, FoodSize foodSize) {
        super(context);
        this.food = food;
        this.restaurantName = restaurantName;
        this.foodSize = foodSize;
        initControl(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageSavedFood);
        tvName = findViewById(R.id.tvFoodNameSaved);
        tvSize = findViewById(R.id.tvFoodSavedSize);
        tvrestaurantName = findViewById(R.id.tvFoodSavedRestaurantName);
        tvPrice = findViewById(R.id.tvFoodSavedPrice);
        btnDelete = findViewById(R.id.btnDeleteSaveCardItem);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_food_saved_card, this);

        initUI();

        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Bạn có muốn xóa món " + food.getName() + " không?");
            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                HomeActivity.dao.deleteFoodSavedByFoodIdAndSize(foodSize.getFoodId(), foodSize.getSize());
                SavedFragment.saved_container.removeView(this);
            });
            dialog.setNegativeButton("Không", (dialogInterface, i) -> {
            });
            dialog.show();
        });


        // Set information for cart card
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(food.getImage()));
        tvName.setText(food.getName());
        switch (foodSize.getSize()) {
            case 1:
                tvSize.setText("Size S");
                break;
            case 2:
                tvSize.setText("Size M");
                break;
            case 3:
                tvSize.setText("Size L");
                break;
        }
        tvrestaurantName.setText(restaurantName);
        tvPrice.setText(getRoundPrice(foodSize.getPrice()));
    }

    private String getRoundPrice(Double price) {
        return Math.round(price) + " VNĐ";
    }
}
