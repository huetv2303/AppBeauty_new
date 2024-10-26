package com.btl.beauty_new.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private String name, phone, address, dateOfOrder;
    private DAO dao;
    private Intent intent;
    private static double sum;
    public static User user;
    public static int userID;
    private Spinner getAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        dao = new DAO(this);
        intent = getIntent();
        referencesComponents();

        // Lấy Spinner và thiết lập Adapter
        Spinner spinnerAddress = findViewById(R.id.getAddress);
        List<String> addressList = dao.getAllAddressNames(userID);

        // Kiểm tra danh sách địa chỉ
        if (addressList.isEmpty()) {
            Toast.makeText(this, "Không có địa chỉ nào được tìm thấy.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Địa chỉ: " + addressList, Toast.LENGTH_SHORT).show();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, addressList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAddress.setAdapter(adapter);
        }
    }

    private void referencesComponents() {
        TextView tvUser_name = findViewById(R.id.editText_payment_name);
        TextView tvUserPhone = findViewById(R.id.editText_payment_phone);
        TextView tvTotalValue = findViewById(R.id.tv_total_values);
        getAddress = findViewById(R.id.getAddress);

        tvUser_name.setText(HomeActivity.user.getName());
        tvUserPhone.setText(HomeActivity.user.getPhone());

        // Lấy thông tin đơn hàng
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
            address = getAddress.getSelectedItem().toString();  // Lấy địa chỉ từ Spinner

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.fill_full_the_information), Toast.LENGTH_SHORT).show();
                return;
            }
            dateOfOrder = dao.getDate();

            Order order = new Order(orderId, user.getId(), address, dateOfOrder, sum, "Coming");
            dao.updateOrder(order);

            Toast.makeText(this, getResources().getString(R.string.payment_successfully), Toast.LENGTH_SHORT).show();
            ChatFragment.cartContainer.removeAllViews();

            // Gửi thông báo cho người dùng
            String content = getResources().getString(R.string.information_order_item) + sum + " VNĐ";
            dao.addNotify(new Notify(1, getResources().getString(R.string.information_order), content, dateOfOrder));
            dao.addNotifyToUser(new NotifyToUser(dao.getNewestNotifyId(), user.getId()));

            finish();
        });

        Button btnCancel = findViewById(R.id.btnThanhToanTroLai);
        btnCancel.setOnClickListener(view -> finish());
    }
}
