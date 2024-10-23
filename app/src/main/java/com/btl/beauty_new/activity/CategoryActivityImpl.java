package com.btl.beauty_new.activity;

public interface CategoryActivityImpl {
    void initializeUI();
    void setupSearchBar();
    void loadRestaurantInformation();
    void loadFoodData(String nameFoodOfThisRestaurant);
}