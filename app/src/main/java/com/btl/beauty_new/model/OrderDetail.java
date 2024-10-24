package com.btl.beauty_new.model;

public class OrderDetail {
    private Integer orderId;
    private Integer cosmeticId;
    private Integer size;
    private Double price;
    private Integer quantity;

    public OrderDetail(Integer orderId, Integer cosmeticId, Integer size, Double price) {
        this.orderId = orderId;
        this.cosmeticId = cosmeticId;
        this.size = size;
        this.price = price;
        this.quantity = 1;
    }

    public OrderDetail(Integer orderId, Integer cosmeticId, Integer size, Double price, Integer quantity) {
        this.orderId = orderId;
        this.cosmeticId = cosmeticId;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
