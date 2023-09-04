package com.hoangtien2k3.foody_order_app.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.CategoryActivityImpl;
import com.hoangtien2k3.foody_order_app.components.FoodCard;
import com.hoangtien2k3.foody_order_app.repository.DAO;
import com.hoangtien2k3.foody_order_app.repositoryInit.DatabaseHandler;
import com.hoangtien2k3.foody_order_app.model.Food;
import com.hoangtien2k3.foody_order_app.model.FoodSize;
import com.hoangtien2k3.foody_order_app.model.Restaurant;


import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryActivityImpl {
    private LinearLayout foodCartContainer;
    private DAO dao;
    private Intent intent_get_data;
    private Integer restaurantId;

    private ImageView image, imageSync, restaurantImage;
    private TextView tvRestaurantName, tvRestaurantAddress, tvRestaurantPhone;
    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        intent_get_data = getIntent();

        dao = new DAO(this);

        initializeUI();
//        setupSearchBar();
        loadRestaurantInformation();
        loadFoodData(null);
    }


    @Override
    public void initializeUI() {
        image = findViewById(R.id.imageCartC);
        imageSync = findViewById(R.id.imageSync);
        restaurantImage = findViewById(R.id.imageRestaurant_category);
        tvRestaurantName = findViewById(R.id.tvRestaurantName_category);
        tvRestaurantAddress = findViewById(R.id.tvRestaurantAddress_category);
        tvRestaurantPhone = findViewById(R.id.tvRestaurantPhone_category);
        foodCartContainer = findViewById(R.id.foodCartContainer);
//        searchBar = findViewById(R.id.search_bar);
    }


    // tìm kiếm thông tin về đồ ăn trên danh sách đồ ăn.
//    @Override
//    public void setupSearchBar() {
//        // thanh tìm kiếm thông tin
//        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                String nameFoodOfThisRestaurant = searchBar.getQuery().toString();
//                loadFoodData(nameFoodOfThisRestaurant);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });
//    }


    @Override
    public void loadRestaurantInformation() {
        // thoat về trang chủ
        image.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, HomeActivity.class);
            intent.putExtra("request","cart");
            startActivity(intent);
        });

        // load lại tất cả thông tin
        imageSync.setOnClickListener(view -> loadFoodData(null));

        // Restaurant data: đẩy thông tin lên
        LinearLayout layoutRestaurant = findViewById(R.id.layout_restaurantInformation);
        restaurantId = intent_get_data.getIntExtra("restaurantId", -1);
        if(restaurantId != -1){
            Restaurant restaurant = dao.getRestaurantInformation(restaurantId);
            restaurantImage.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(restaurant.getImage()));
            tvRestaurantName.setText(restaurant.getName());
            tvRestaurantAddress.setText(String.format("\t+ Địa Chỉ: %s", restaurant.getAddress()));
            tvRestaurantPhone.setText(String.format("\t+ Số Điện Thoại: %s", restaurant.getPhone()));
        } else {
            layoutRestaurant.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFoodData(String nameFoodOfThisRestaurant) {
        foodCartContainer.removeAllViews();
        // Add food cart to layout container
        ArrayList<Food> foodArrayList;

        if(nameFoodOfThisRestaurant == null) {
            int getRestaurantId = intent_get_data.getIntExtra("restaurantId", -1);
            System.out.println(getRestaurantId);
            if (getRestaurantId == -1){
                String foodKeyword = intent_get_data.getStringExtra("nameFood");
                foodArrayList = dao.getFoodByKeyWord(foodKeyword, null);
                System.out.println(foodArrayList);
            } else {
                foodArrayList = dao.getFoodByRestaurant(getRestaurantId);
            }
        } else {
            foodArrayList = dao.getFoodByKeyWord(nameFoodOfThisRestaurant, restaurantId);
        }

        // duyệt qua danh sách sản phẩm
        for(Food food : foodArrayList){
            Restaurant restaurant = dao.getRestaurantInformation(food.getRestaurantId());
            FoodSize foodSize = dao.getFoodDefaultSize(food.getId());

            FoodCard foodCard = new FoodCard(this, food, foodSize.getPrice(), restaurant.getName());

            // click đê hiển thị thông tin chi tiết
            foodCard.setOnClickListener(view -> {
                FoodDetailsActivity.foodSize = foodSize;
                Intent intent = new Intent(this, FoodDetailsActivity.class);
                intent.putExtra("food", food);
                try {
                    startActivity(intent);
                } catch (Exception e){
                    Toast.makeText(this, "Không thể hiển thị thông tin!", Toast.LENGTH_SHORT).show();
                }
            });

            // thêm thông tin
            foodCartContainer.addView(foodCard);
        }
    }
}