package com.btl.beauty_new.components;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.fragments.ChatFragment;
import com.btl.beauty_new.model.Cosmetic;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.model.OrderDetail;

public class CartCard extends LinearLayout implements BaseComponent {
    private Cosmetic cosmetic;
    private String restaurantName;
    private OrderDetail card;
    private boolean activateControl;
    private int quantity;
    private ImageView image;
    private TextView tvName, tvSize, tvRestaurantName, tvPrice, tvQuantity;
    private ImageView btnSub, btnAdd, btnDelete;
    private LinearLayout layout;


    public CartCard(Context context) {
        super(context);
        initControl(context);
    }

    public CartCard(Context context, Cosmetic cosmetic, String restaurantName, OrderDetail card) {
        super(context);
        this.cosmetic = cosmetic;
        this.restaurantName = restaurantName;
        this.card = card;
        this.activateControl = true;
        initControl(context);
    }

    public CartCard(Context context, Cosmetic cosmetic, String restaurantName, OrderDetail card, boolean activateControl) {
        super(context);
        this.cosmetic = cosmetic;
        this.restaurantName = restaurantName;
        this.card = card;
        this.activateControl = activateControl;
        initControl(context);
    }

    @Override
    public void initUI() {
        image = findViewById(R.id.imageCartFood);
        tvName = findViewById(R.id.tvFoodNameCart);
        tvSize = findViewById(R.id.tvFoodSizeCart);
        tvRestaurantName = findViewById(R.id.tvRestaurantNameCart);
        tvPrice = findViewById(R.id.tvFoodPriceCart);
        tvQuantity = findViewById(R.id.tvFoodQuantity_Cart); // Số lượng món ăn
        btnSub = findViewById(R.id.btnSubQuantity_Cart);
        btnAdd = findViewById(R.id.btnAddQuantity_Cart);
        btnDelete = findViewById(R.id.btnDeleteCartItem);
        layout = findViewById(R.id.layout_btn_delete);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void initControl(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_cart_card, this);

        initUI(); // init UI
        quantity = card.getQuantity();

        // giảm số lượng món ăn xuống
        btnSub.setOnClickListener(view -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText("" + quantity);
                card.setQuantity(quantity);
                HomeActivity.dao.updateQuantity(card);
            }
        });

        // tăng số lượng món ăn lên
        btnAdd.setOnClickListener(view -> {
            quantity++;
            tvQuantity.setText("" + quantity);
            card.setQuantity(quantity);
            HomeActivity.dao.updateQuantity(card);
        });


        // xóa món ăn đó khỏi giỏ hàng
        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext()); // khai báo 1 dialog
            dialog.setMessage("Bạn có muốn xóa món " + cosmetic.getName() + " không ?");
            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                if (HomeActivity.dao.deleteOrderDetailByOrderIdAndCosmeticId(card.getOrderId(), cosmetic.getId())) {
//                    ChatFragment.cartContainer.removeView(this);
                    ChatFragment.cartContainer.removeView(this); // xóa view đó (hay là xóa món ăn đó khỏi giỏ hàng)

                    Toast.makeText(context, "Đã xóa thông tin thành công.", Toast.LENGTH_SHORT).show(); // đẩy ra thông báo
                } else {
                    Toast.makeText(context, "Gặp lỗi khi xóa thông tin!!!!", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.setNegativeButton("Không", (dialogInterface, i) -> {
            });
            dialog.show();
        });


        if (!activateControl) {
            btnDelete.setVisibility(GONE);
            layout.setBackgroundColor(Color.rgb(255, 70, 70));
            btnAdd.setEnabled(activateControl);
            btnSub.setEnabled(activateControl);
        }

        // Set information for cart card
        image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(cosmetic.getImage()));
        tvName.setText(cosmetic.getName());
        switch (card.getSize()) {
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
        tvRestaurantName.setText(restaurantName);
        tvPrice.setText(getRoundPrice(card.getPrice()));
        tvQuantity.setText("" + quantity);
    }

    private String getRoundPrice(Double price) {
        return Math.round(price) + " VNĐ";
    }
}
