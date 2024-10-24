package com.btl.beauty_new.model;

import java.io.Serializable;

public class Cosmetic implements Serializable {
    private Integer id;
    private String name;
    private String type;
    private byte[] image; // trong SQLite thì dùng BLOG (binary
    private String description;
    private Integer storeId;

    public Cosmetic() {
    }

    public Cosmetic(Integer id, String name, String type, byte[] image, String description, Integer storeId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.image = image;
        this.description = description;
        this.storeId = storeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
