package com.hoangtien2k3.foody_order_app.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.exception.PhoneNumberException;
import com.hoangtien2k3.foody_order_app.fragments.CartFragment;
import com.hoangtien2k3.foody_order_app.repository.DAO;
import com.hoangtien2k3.foody_order_app.model.Notify;
import com.hoangtien2k3.foody_order_app.model.NotifyToUser;
import com.hoangtien2k3.foody_order_app.model.Order;
import com.hoangtien2k3.foody_order_app.model.OrderDetail;
import com.hoangtien2k3.foody_order_app.model.User;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    private String name, phone, address, dateOfOrder;
    private DAO dao;
    private Intent intent;
    private static double sum; // tổng hóa đơn
    public static User user;   // người đặt hóa đơn
    private final String regexDigitPhoneNumber = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        intent = getIntent();
        dao = new DAO(this);

        try {
            referencesComponents();
        } catch (PhoneNumberException e) {
            throw new RuntimeException(e);
        }
    }

    private void referencesComponents() throws PhoneNumberException {
        TextView tvUser_name = findViewById(R.id.editText_payment_name);
        TextView tvUserPhone = findViewById(R.id.editText_payment_phone);
        TextView tvUserAddress = findViewById(R.id.editText_payment_address);
        TextView tvTotalValue = findViewById(R.id.tv_total_values);
        Button btnThanhToan = findViewById(R.id.btnThanhToanThanhToan);

        tvUser_name.setText(HomeActivity.user.getName());
        tvUserPhone.setText(HomeActivity.user.getPhone());

        // get order
        Integer orderId = intent.getIntExtra("orderId", 0);
        ArrayList<OrderDetail> orderDetailArrayList = dao.getCartDetailList(orderId);
        sum = 0;
        for (OrderDetail orderDetail : orderDetailArrayList) {
            sum += orderDetail.getPrice() * orderDetail.getQuantity();
        }
        tvTotalValue.setText(String.format("%s VNĐ", sum)); // hiển thị giá tiền tất cả các sản phẩm


        // thanh toán hóa đơn
        btnThanhToan.setOnClickListener(view -> {
            name = tvUser_name.getText().toString().trim();
            phone = tvUserPhone.getText().toString().trim();
            address = tvUserAddress.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, getResources().getString(R.string.writer_full_information), Toast.LENGTH_SHORT).show();
                return;
            }

            // kiểm tra số điện thoại có đúng định dạng hay không
            if (phone.matches(regexDigitPhoneNumber)) {
                try {
                    throw new PhoneNumberException(getResources().getString(R.string.phoneNumber_Error));
                } catch (PhoneNumberException e) {
                    throw new RuntimeException(e);
                }
            }

            dateOfOrder = dao.getDate(); // lấy ra ngày hiện tại theo thời gian thực

            Order order = new Order(orderId, user.getId(), address, dateOfOrder, sum, "Coming");
            dao.updateOrder(order);

            // in thông báo thanh toán tiền thành công lên màn hình
            Toast.makeText(this, getResources().getString(R.string.payment_successfully), Toast.LENGTH_SHORT).show();

            // xóa thông tin sản phẩm đã thanh toán đó đi trong giỏ hàng, lịch sử, và vận chuyển
            CartFragment.cartContainer.removeAllViews();


            // User Notify
            String content = "Đơn hàng của bạn đang được giao!\nTổng giá trị đơn hàng là " + sum + " VNĐ";
            dao.addNotify(new Notify(1, "Thông báo về đơn hàng!",
                    content, dateOfOrder));
            dao.addNotifyToUser(new NotifyToUser(dao.getNewestNotifyId(), user.getId()));

            finish(); // hoàn thành và thoát khỏi Activity thanh toán tiền
        });

        Button btnCancel = findViewById(R.id.btnThanhToanTroLai);
        btnCancel.setOnClickListener(view -> finish());
    }
}