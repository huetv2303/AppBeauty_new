package com.btl.beauty_new.model;

public class CosmeticSaved {
    private Integer cosmeticId;
    private Integer size;
    private Integer userId;

    public CosmeticSaved(Integer cosmeticId, Integer size, Integer userId) {
        this.cosmeticId = cosmeticId;
        this.size = size;
        this.userId = userId;
    }

    public Integer getCosmeticId() {
        return cosmeticId;
    }

    public void setCosmeticId(Integer cosmeticId) {
        this.cosmeticId = cosmeticId;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
