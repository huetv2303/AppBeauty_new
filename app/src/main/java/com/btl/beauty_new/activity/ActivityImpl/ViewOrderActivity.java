package com.btl.beauty_new.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.components.CartCard;
import com.btl.beauty_new.repository.DAO;
import com.btl.beauty_new.model.Food;
import com.btl.beauty_new.model.FoodSize;
import com.btl.beauty_new.model.Notify;
import com.btl.beauty_new.model.NotifyToUser;
import com.btl.beauty_new.model.Order;
import com.btl.beauty_new.model.OrderDetail;
import com.btl.beauty_new.model.Restaurant;

import java.util.ArrayList;

public class ViewOrderActivity extends AppCompatActivity {
    private LinearLayout layout_container;
    private TextView tvDate, tvPrice, tvAddress, tvStatus;
    private DAO dao;
    private Order order;
    public static Integer userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        dao = new DAO(this);
        Intent intent = getIntent();
        order = (Order) intent.getSerializableExtra("order");
        referencesComponent();
        LoadData();
    }

    public void referencesComponent() {
        layout_container = findViewById(R.id.layout_container_order_detail);

        tvDate = findViewById(R.id.tvDateMakeOrderView);
        tvPrice = findViewById(R.id.tvOrderPriceView);
        tvAddress = findViewById(R.id.tvOrderAddressView);
        tvStatus = findViewById(R.id.tvOrderStatusView);

        Button btnDeleteOrder = findViewById(R.id.btnDeleteOrder);
        if (order.getStatus().equals("Delivered") || order.getStatus().equals("Canceled")) {
            btnDeleteOrder.setEnabled(false);
            btnDeleteOrder.setBackgroundColor(Color.GRAY);
        }

        btnDeleteOrder.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getResources().getString(R.string.If_You_Delete_Cart));
            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
                order.setStatus("Canceled");
                dao.updateOrder(order);
                Toast.makeText(this, getResources().getString(R.string.order_cancel), Toast.LENGTH_SHORT).show();

                // User Notify
                String content = getResources().getString(R.string.information_order_cancel) + order.getTotalValue()
                        + getResources().getString(R.string.contact_information);
                dao.addNotify(new Notify(1, getResources().getString(R.string.information_order),
                        content, dao.getDate()));
                dao.addNotifyToUser(new NotifyToUser(dao.getNewestNotifyId(), userID));
                finish();
            });
            dialog.setNegativeButton("Không", (dialogInterface, i) -> {
            });
            dialog.show();

        });

        Button btnCancel = findViewById(R.id.btnCancelOrderView);
        btnCancel.setOnClickListener(view -> finish());
    }

    private void LoadData() {
        tvDate.setText(String.format("+ Ngày Đặt Sản Phẩm: %s", order.getDateOfOrder()));
        tvAddress.setText(String.format("+ Địa Chỉ Giao Hàng: %s", order.getAddress()));
        tvPrice.setText(String.format("+ Tổng Tiền Sản Phẩm: %s", getRoundPrice(order.getTotalValue())));
        tvStatus.setText(String.format("+ Trạng Thái Giao Hàng: %s", order.getStatus()));

        ArrayList<OrderDetail> orderDetailArrayList = dao.getCartDetailList(order.getId());
        if (orderDetailArrayList.size() > 0) {
            for (OrderDetail orderDetail : orderDetailArrayList) {
                Food food = dao.getFoodById(orderDetail.getFoodId());
                Restaurant restaurant = dao.getRestaurantInformation(food.getRestaurantId());
                FoodSize foodSize = dao.getFoodSize(orderDetail.getFoodId(), orderDetail.getSize());

                CartCard card = new CartCard(this, food, restaurant.getName(), orderDetail, false);
                card.setOnClickListener(view -> {
                    FoodDetailsActivity.foodSize = foodSize;
                    Intent intent = new Intent(this, FoodDetailsActivity.class);
                    intent.putExtra("food", food);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(this, getResources().getString(R.string.not_found_information), Toast.LENGTH_SHORT).show();
                    }
                });

                layout_container.addView(card);
            }
        }

    }

    private String getRoundPrice(Double price) {
        return Math.round(price) + " VNĐ";
    }
}