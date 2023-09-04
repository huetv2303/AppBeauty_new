package com.hoangtien2k3.foody_order_app.activity;

public interface CategoryActivityImpl {
    void initializeUI();
//    void setupSearchBar();
    void loadRestaurantInformation();
    void loadFoodData(String nameFoodOfThisRestaurant);
}