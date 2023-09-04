package com.hoangtien2k3.foody_order_app.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        referencesComponents();
        getFoodData(null);
    }

    protected abstract void referencesComponents();
    protected abstract void getFoodData(String nameFoodOfThisRestaurant);


}
