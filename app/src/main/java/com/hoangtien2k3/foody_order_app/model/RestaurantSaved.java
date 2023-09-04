package com.hoangtien2k3.foody_order_app.model;

public class RestaurantSaved {
    private Integer restaurantId;
    private Integer userId;

    public RestaurantSaved(Integer restaurantId, Integer userId) {
        this.restaurantId = restaurantId;
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
