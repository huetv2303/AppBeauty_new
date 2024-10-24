package com.btl.beauty_new.model;

public class StoreSaved {
    private Integer storeId;
    private Integer userId;

    public StoreSaved(Integer storeId, Integer userId) {
        this.storeId = storeId;
        this.userId = userId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
