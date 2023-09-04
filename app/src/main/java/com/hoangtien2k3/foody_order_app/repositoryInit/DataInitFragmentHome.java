package com.hoangtien2k3.foody_order_app.repositoryInit;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.image.Photo;

import java.util.ArrayList;
import java.util.List;

public class DataInitFragmentHome {
    // list ảnh hiển thị trong home slider
    public static final List<Photo> listPhoto = new ArrayList<>();
    static {
        listPhoto.add(new Photo(R.drawable.image_1));
        listPhoto.add(new Photo(R.drawable.image_2));
        listPhoto.add(new Photo(R.drawable.image_3));
        listPhoto.add(new Photo(R.drawable.image_4));
        listPhoto.add(new Photo(R.drawable.banhbonglan_socola));
        listPhoto.add(new Photo(R.drawable.banhdauxanh_cotdua));
    }
}
