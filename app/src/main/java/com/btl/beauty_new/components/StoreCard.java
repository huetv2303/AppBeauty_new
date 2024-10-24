package com.btl.beauty_new.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.fragments.SavedFragment;
import com.btl.beauty_new.model.Store;
import com.btl.beauty_new.model.StoreSaved;

public class StoreCard extends LinearLayout implements BaseComponent{
    private Store store;
    private boolean isSaved;
    private ImageView image;
    private TextView tvStoreName, tvStoreAddress;

//    private LinearLayout btnSavedShop;
    private CheckBox btnSavedShop;

    private TextView textViewSaveShop;

    public StoreCard(Context context, Store store, boolean isSaved) {
        super(context);
        this.store = store;
        this.isSaved = isSaved;
        initControl(context);
    }

    public StoreCard(Context context){
        super(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageStore);
        tvStoreName = findViewById(R.id.tvStoreName_res_cart);
        tvStoreAddress = findViewById(R.id.tvStoreAddress_res_cart);
//        btnSavedShop = findViewById(R.id.btnSavedShop);
        btnSavedShop = findViewById(R.id.btnSavedShop);

        textViewSaveShop = findViewById(R.id.textViewSaveShop);
    }

    @Override
    public void initControl(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_store_card, this);

        initUI();

        if(isSaved){
            textViewSaveShop.setText("BỎ LƯU");
        }
        btnSavedShop.setOnClickListener(view ->{
            if(isSaved){
                if(HomeActivity.dao.deleteStoreSaved(new StoreSaved(store.getId(), HomeActivity.user.getId()))){
                    Toast.makeText(context, "Đã bỏ lưu thông tin cửa hàng!", Toast.LENGTH_SHORT).show();
                    SavedFragment.saved_container.removeView(this);
                } else {
                    Toast.makeText(context, "Có lỗi khi xóa thông tin!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(HomeActivity.dao.addStoreSaved(new StoreSaved(store.getId(), HomeActivity.user.getId()))){
                    Toast.makeText(context, "Lưu thông tin cửa hàng thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Bạn đã lưu thông tin cửa hàng này rồi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // set information
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(store.getImage()));
        tvStoreName.setText(store.getName());
        tvStoreAddress.setText(store.getAddress());
    }
}

