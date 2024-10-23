package com.btl.beauty_new.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.fragments.ChatFragment;
import com.btl.beauty_new.repository.DAO;
import com.btl.beauty_new.model.Notify;
import com.btl.beauty_new.model.NotifyToUser;
import com.btl.beauty_new.model.Order;
import com.btl.beauty_new.model.OrderDetail;
import com.btl.beauty_new.model.User;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private String name, phone, address, dateOfOrder;
    private DAO dao;
    private Intent intent;
    private static double sum;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        intent = getIntent();
        dao = new DAO(this);
        referencesComponents();
    }

    private void referencesComponents() {
        TextView tvUser_name = findViewById(R.id.editText_payment_name);
        TextView tvUserPhone = findViewById(R.id.editText_payment_phone);
        TextView tvUserAddress = findViewById(R.id.editText_payment_address);
        TextView tvTotalValue = findViewById(R.id.tv_total_values);

        tvUser_name.setText(HomeActivity.user.getName());
        tvUserPhone.setText(HomeActivity.user.getPhone());

        // get order
        Integer orderId = intent.getIntExtra("orderId", 0);
        ArrayList<OrderDetail> orderDetailArrayList = dao.getCartDetailList(orderId);
        sum = 0;
        for (OrderDetail orderDetail : orderDetailArrayList) {
            sum += orderDetail.getPrice() * orderDetail.getQuantity();
        }
        tvTotalValue.setText(String.format("%s VNĐ", sum));

        Button btnThanhToan = findViewById(R.id.btnThanhToanThanhToan);
        btnThanhToan.setOnClickListener(view -> {
            name = tvUser_name.getText().toString();
            phone = tvUserPhone.getText().toString();
            address = tvUserAddress.getText().toString();
            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.fill_full_the_information), Toast.LENGTH_SHORT).show();
                return;
            }
            dateOfOrder = dao.getDate();

            Order order = new Order(orderId, user.getId(), address, dateOfOrder, sum, "Coming");
            dao.updateOrder(order);

            Toast.makeText(this, getResources().getString(R.string.payment_successfully), Toast.LENGTH_SHORT).show();
            ChatFragment.cartContainer.removeAllViews();

            // User Notify
            String content = getResources().getString(R.string.information_order_item) + sum + " VNĐ";
            dao.addNotify(new Notify(1, getResources().getString(R.string.information_order), content, dateOfOrder));
            dao.addNotifyToUser(new NotifyToUser(dao.getNewestNotifyId(), user.getId()));

            finish();
        });

        Button btnCancel = findViewById(R.id.btnThanhToanTroLai);
        btnCancel.setOnClickListener(view -> finish());
    }
}