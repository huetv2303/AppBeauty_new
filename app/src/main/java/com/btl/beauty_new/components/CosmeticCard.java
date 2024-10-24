package com.btl.beauty_new.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btl.beauty_new.R;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.model.Cosmetic;


public class CosmeticCard extends LinearLayout implements BaseComponent{
    private Cosmetic cosmetic;
    private Double defaultPrice;
    private String storeName;
    private ImageView image;
    private TextView tvName, tvPrice, tvStoreName;

    public CosmeticCard(Context context, Cosmetic cosmetic, Double defaultPrice, String storeName){
        super(context);
        this.cosmetic = cosmetic;
        this.defaultPrice = defaultPrice;
        this.storeName = storeName;
        initControl(context);
    }

    public CosmeticCard(Context context) {
        super(context);
        initControl(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageCosmetic);
        tvName = findViewById(R.id.tvNameCosmetic);
        tvPrice = findViewById(R.id.tvPriceCosmetic);
        tvStoreName = findViewById(R.id.tvCosmeticStoreName);
    }

    @Override
    public void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_cosmetic_card, this);

        initUI();

        // Set information for food cart
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(cosmetic.getImage()));
        tvName.setText(cosmetic.getName());
        tvPrice.setText(String.format("%s VNĐ", Math.round(defaultPrice)));
        tvStoreName.setText(storeName);
    }
}