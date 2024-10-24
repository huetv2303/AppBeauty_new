package com.btl.beauty_new.components;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.R;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.fragments.SavedFragment;
import com.btl.beauty_new.model.Cosmetic;
import com.btl.beauty_new.model.CosmeticSize;

@SuppressLint("ViewConstructor")
public class CosmeticSavedCard extends LinearLayout implements BaseComponent{
    private final Cosmetic cosmetic;
    private final String restaurantName;
    private final CosmeticSize cosmeticSize;
    private ImageView image;
    private TextView tvName, tvSize, tvrestaurantName, tvPrice;
    private Button btnDelete;

    public CosmeticSavedCard(Context context, Cosmetic cosmetic, String restaurantName, CosmeticSize cosmeticSize) {
        super(context);
        this.cosmetic = cosmetic;
        this.restaurantName = restaurantName;
        this.cosmeticSize = cosmeticSize;
        initControl(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageSavedCosmetic);
        tvName = findViewById(R.id.tvCosmeticNameSaved);
        tvSize = findViewById(R.id.tvCosmeticSavedSize);
        tvrestaurantName = findViewById(R.id.tvCosmeticSavedStoreName);
        tvPrice = findViewById(R.id.tvCosmeticSavedPrice);
        btnDelete = findViewById(R.id.btnDeleteSaveCardItem);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_cosmetic_saved_card, this);

        initUI();

        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setMessage("Bạn có muốn xóa sản phầm " + cosmetic.getName() + " không?");
            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                HomeActivity.dao.deleteCosmeticSavedByCosmeticIdAndSize(cosmeticSize.getCosmeticId(), cosmeticSize.getSize());
                SavedFragment.saved_container.removeView(this);
            });
            dialog.setNegativeButton("Không", (dialogInterface, i) -> {
            });
            dialog.show();
        });


        // Set information for cart card
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(cosmetic.getImage()));
        tvName.setText(cosmetic.getName());
        switch (cosmeticSize.getSize()) {
            case 1:
                tvSize.setText("Size S");
                break;
            case 2:
                tvSize.setText("Size M");
                break;
            case 3:
                tvSize.setText("Size L");
                break;
        }
        tvrestaurantName.setText(restaurantName);
        tvPrice.setText(getRoundPrice(cosmeticSize.getPrice()));
    }

    private String getRoundPrice(Double price) {
        return Math.round(price) + " VNĐ";
    }
}
