package com.btl.beauty_new.model;

import java.io.Serializable;

public class CosmeticSize implements Serializable {
    private Integer cosmeticId;
    private Integer size;
    private Double price;

    public CosmeticSize() {
    }

    public CosmeticSize(Integer cosmeticId, Integer size, Double price) {
        this.cosmeticId = cosmeticId;
        this.size = size;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
