package com.hoangtien2k3.foody_order_app.model;

public class Category {
    private String title;
    private byte[] pic;

    public Category(String title, byte[] pic) {
        this.title = title;
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}
