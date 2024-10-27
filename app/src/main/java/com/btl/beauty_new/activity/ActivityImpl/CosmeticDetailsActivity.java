package com.btl.beauty_new.activity.ActivityImpl;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.CosmeticDetailsActivityImpl;
import com.btl.beauty_new.model.Cosmetic;
import com.btl.beauty_new.model.StoreSaved;
import com.btl.beauty_new.repository.DAO;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.model.CosmeticSaved;
import com.btl.beauty_new.model.CosmeticSize;
import com.btl.beauty_new.model.Order;
import com.btl.beauty_new.model.OrderDetail;
import com.btl.beauty_new.model.Store;

import java.util.ArrayList;

public class CosmeticDetailsActivity extends AppCompatActivity implements CosmeticDetailsActivityImpl {
    private ImageView image, btnAddQuantity, btnSubQuantity;
    private LinearLayout layout_sizeS, layout_sizeM, layout_sizeL, btnAddToCart, btnSavedCosmetic;
    private CheckBox checkBoxFavorite, checkBoxCart;
    private TextView tvName, tvDescription, tvPrice,
            tvStoreName, tvStoreAddress,
            tvPriceSizeS, tvPriceSizeM, tvPriceSizeL,
            tvQuantity;

    //    private Button btnAddToCart, btnSavedCosmetic;
    public static Integer userID; // lấy ra userId
    private static int quantity;
    public static CosmeticSize cosmeticSize;    // lấy ra gias cả và kích thước
    private static DAO dao;    // kết nối với CSDL

    private void updateCheckBoxFavoriteStatus() {
        boolean isSaved = CosmeticDetailsActivity.dao.isCosmeticSaved(new CosmeticSaved(cosmeticSize.getCosmeticId(), cosmeticSize.getSize(), userID));
        Log.d("CosmeticDetailsActivity", "updateCheckBoxFavoriteStatus: isSaved = " + isSaved); // Thêm log để kiểm tra trạng thái

        int colorChecked = Color.parseColor("#FF6200EE"); // Màu đậm
        int colorUnchecked = Color.parseColor("#A3A3A3"); // Màu nhạt

        checkBoxFavorite.setChecked(isSaved);
        checkBoxFavorite.setButtonTintList(ColorStateList.valueOf(isSaved ? colorChecked : colorUnchecked));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosmetic_details);

        quantity = 1;
        dao = new DAO(this);

        initializeUI();
        // quay về màn hình trước đó.
        findViewById(R.id.btnBack).setOnClickListener(view -> this.finish());

        btnAddToCart.setOnClickListener(view -> {
            addCartProduct();
        });


        ///Huế làm
        checkBoxFavorite.setOnClickListener(view -> {
            CosmeticSaved cosmeticSaved = new CosmeticSaved(cosmeticSize.getCosmeticId(), cosmeticSize.getSize(), userID);
            boolean isSaved = dao.isCosmeticSaved(cosmeticSaved);

            if (isSaved) {
                // Bỏ lưu
                if (dao.deleteCosmeticSaved(cosmeticSaved)) {
                    Toast.makeText(this, "Đã bỏ lưu mỹ phẩm!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Không thể bỏ lưu mỹ phẩm!", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Lưu
                if (dao.addCosmeticSaved(cosmeticSaved)) {
                    Toast.makeText(this, "Lưu thông tin mỹ phẩm thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Bạn đã lưu thông tin mỹ phẩm này rồi!", Toast.LENGTH_SHORT).show();
                }
            }

            updateCheckBoxFavoriteStatus(); // Cập nhật lại giao diện checkbox sau khi lưu hoặc bỏ lưu
        });

        // tăng số lượng mỹ phẩm
        btnAddQuantity.setOnClickListener(view -> {
            quantity++;
            tvQuantity.setText(String.format("%s", quantity));
            tvPrice.setText(getTotalPrice());
        });

        // giảm số lượng mỹ phẩm
        btnSubQuantity.setOnClickListener(view -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.format("%s", quantity));
                tvPrice.setText(getTotalPrice());
            }
        });

        LoadData();
    }



    @NonNull
    private String getRoundPrice(Double price) {
        return Math.round(price) + " VNĐ";
    }

    private String getTotalPrice() {
        return Math.round(cosmeticSize.getPrice() * quantity) + " VNĐ";
    }

    @Override
    public void initializeUI() {
        // initUI
        tvName = findViewById(R.id.tvCosmeticName);
        tvDescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        image = findViewById(R.id.image);

        layout_sizeS = findViewById(R.id.layout_size_S);
        layout_sizeM = findViewById(R.id.layout_size_M);
        layout_sizeL = findViewById(R.id.layout_size_L);

        tvPriceSizeS = findViewById(R.id.tvPriceSizeS);
        tvPriceSizeM = findViewById(R.id.tvPriceSizeM);
        tvPriceSizeL = findViewById(R.id.tvPriceSizeL);

        tvQuantity = findViewById(R.id.tvCosmeticQuantity_Cosmetic);

        tvStoreName = findViewById(R.id.tvStoreName);
        tvStoreAddress = findViewById(R.id.tvStoreAddress);

        btnAddToCart = findViewById(R.id.btnAddToCart);
        checkBoxFavorite = findViewById(R.id.checkBoxFavorite);

        btnAddQuantity = findViewById(R.id.btnAddQuantity_Cosmetic);
        btnSubQuantity = findViewById(R.id.btnSubQuantity_Cosmetic);
    }



    // đẩy thông tin vào giỏ hàng
    private void addCartProduct() {
        // Make cart if don't have
        Cursor cursor = dao.getCart(userID);

        // (di chuyển con trỏ Cursor đến vị trí đúng trước khi truy cập dữ liệu.)
        // và kiểm tra xem cursor này đang không trỏ đến vị trí đầu tiên không.
        if (!cursor.moveToFirst()) {
            dao.addOrder(new Order(1, userID, "", "", 0d, "Craft")); // ta sẽ add một order và (hay thêm vào phần giỏ hàng)
            cursor = dao.getCart(userID); // lấy ra đối tượng cursor đấy với userID
//            System.out.println("đã được add đối tượng vào đây rồi.");
        }

        // add order detail
        cursor.moveToFirst();   // (di chuyển con trỏ Cursor đến vị trí đúng trước khi truy cập dữ liệu.)

        OrderDetail orderDetail = dao.getExistOrderDetail(cursor.getInt(0), cosmeticSize);
        if (orderDetail != null) {
            orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
            if (dao.updateQuantity(orderDetail)) {
                Toast.makeText(this, getResources().getString(R.string.add_cosmetic_successfully), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.add_error), Toast.LENGTH_SHORT).show();
            }

        } else {
            boolean addOrderDetail = dao.addOrderDetail(new OrderDetail(cursor.getInt(0),
                    cosmeticSize.getCosmeticId(), cosmeticSize.getSize(), cosmeticSize.getPrice(), quantity));

            if (addOrderDetail) {
                Toast.makeText(this, getResources().getString(R.string.add_cosmetic_successfully), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.add_error), Toast.LENGTH_SHORT).show();
            }
        }
    }


    // setting giá mặc định
    private void SetPriceDefault(Double price) {
        tvPrice.setText(getRoundPrice(price));
        quantity = 1;
        tvQuantity.setText("1");
    }

    // load để đẩy tất cả thông tin về sản phẩm lên
    @Override
    public void LoadData() {
        Intent intent = getIntent();
        if (intent != null) {
            Cosmetic cosmetic = (Cosmetic) intent.getSerializableExtra("cosmetic");

            assert cosmetic != null;
            ArrayList<CosmeticSize> cosmeticSizeArrayList = dao.getAllCosmeticSize(cosmetic.getId());

            // size S
            if (cosmeticSizeArrayList.get(0) != null) {
                if (cosmeticSize == null) cosmeticSize = cosmeticSizeArrayList.get(0);
                tvPriceSizeS.setText(String.format("+ %s", cosmeticSizeArrayList.get(0).getPrice()));
                layout_sizeS.setOnClickListener(view -> {
                    SetPriceDefault(cosmeticSizeArrayList.get(0).getPrice());
                    cosmeticSize = cosmeticSizeArrayList.get(0);
                });
            } else {
                layout_sizeS.setVisibility(View.INVISIBLE);
            }

            // size M
            if (cosmeticSizeArrayList.get(1) != null) {
                tvPriceSizeM.setText(String.format("+ %s", cosmeticSizeArrayList.get(1).getPrice()));
                layout_sizeM.setOnClickListener(view -> {
                    SetPriceDefault(cosmeticSizeArrayList.get(1).getPrice());
                    cosmeticSize = cosmeticSizeArrayList.get(1);
                });
            } else {
                layout_sizeM.setVisibility(View.INVISIBLE);
            }

            // Size L
            if (cosmeticSizeArrayList.get(2) != null) {
                tvPriceSizeL.setText(String.format("+ %s", cosmeticSizeArrayList.get(2).getPrice()));
                layout_sizeL.setOnClickListener(view -> {
                    SetPriceDefault(cosmeticSizeArrayList.get(2).getPrice());
                    cosmeticSize = cosmeticSizeArrayList.get(2);
                });
            } else {
                layout_sizeL.setVisibility(View.INVISIBLE);
            }

            // Set information: đẩy lên các thông tin cơ bản (tên, miêu tả, ảnh)
            tvName.setText(cosmetic.getName());
            tvDescription.setText(cosmetic.getDescription());
            image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(cosmetic.getImage()));

            Store store = dao.getStoreInformation(cosmetic.getStoreId());
            tvStoreName.setText(String.format("+ Tên cửa hàng \n \t\t %s", store.getName()));
            tvStoreAddress.setText(String.format("+ Địa chỉ\n \t\t %s", store.getAddress()));

            // set giá tổng
            tvPrice.setText(getRoundPrice(cosmeticSize.getPrice()));

            // Cập nhật trạng thái của CheckBox
            updateCheckBoxFavoriteStatus();
        }
    }
}