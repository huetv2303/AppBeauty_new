package com.hoangtien2k3.foody_order_app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.PaymentActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.ViewOrderActivity;
import com.hoangtien2k3.foody_order_app.components.CartCard;
import com.hoangtien2k3.foody_order_app.components.OrderCard;
import com.hoangtien2k3.foody_order_app.model.Food;
import com.hoangtien2k3.foody_order_app.model.Order;
import com.hoangtien2k3.foody_order_app.model.OrderDetail;
import com.hoangtien2k3.foody_order_app.model.Restaurant;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View mainView;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout cartContainer;
    private static String status;
    private LinearLayout btnDangDen, btnLichSu, btnGioHang;
    private TextView tvGioHang, tvDangDen, tvLichSu;

    public ChatFragment() {
    }

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_chat, container, false);
        cartContainer = mainView.findViewById(R.id.cartContainer);

        referencesComponent();
        LoadOrder("craft");
        status = "craft";

        return mainView;
    }

    private void referencesComponent() {
        btnGioHang = mainView.findViewById(R.id.btnGioHang);
        btnGioHang.setOnClickListener(view -> {
            resetAttribute();
            btnGioHang.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tvGioHang.setTextColor(Color.BLUE);
            LoadOrder("craft");
        });

        btnDangDen = mainView.findViewById(R.id.btnDangDen);
        btnDangDen.setOnClickListener(view -> {
            resetAttribute();
            btnDangDen.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tvDangDen.setTextColor(Color.BLUE);
            LoadOrder("coming");
        });

        btnLichSu = mainView.findViewById(R.id.btnLichSu);
        btnLichSu.setOnClickListener(view -> {
            resetAttribute();
            btnLichSu.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tvLichSu.setTextColor(Color.BLUE);
            LoadOrder("history");
        });

        tvGioHang = mainView.findViewById(R.id.tvGioHang);
        tvDangDen = mainView.findViewById(R.id.tvDangDen);
        tvLichSu = mainView.findViewById(R.id.tvLichSu);

        Button btnThanhToan = mainView.findViewById(R.id.btnThanhToan);
        btnThanhToan.setOnClickListener(view -> {
            if (!status.equals("craft"))
                return;

            Cursor cursor = HomeActivity.dao.getCart(HomeActivity.user.getId());
            if (!cursor.moveToFirst())
                return;

            PaymentActivity.user = HomeActivity.user;
            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            intent.putExtra("orderId", cursor.getInt(0));
            startActivity(intent);
        });
    }

    private void LoadOrder(String type) {
        status = type;
        cartContainer.removeAllViews();

        switch (type) {
            case "craft":
                Cursor cursor = HomeActivity.dao.getCart(HomeActivity.user.getId());
                if (!cursor.moveToFirst())
                    return;
                cursor.moveToFirst();
                ArrayList<OrderDetail> orderDetailArrayList = HomeActivity.dao.getCartDetailList(cursor.getInt(0));
                if (orderDetailArrayList.size() > 0) {
                    Food food;
                    Restaurant restaurant;
                    for (OrderDetail orderDetail : orderDetailArrayList) {
                        food = HomeActivity.dao.getFoodById(orderDetail.getFoodId());
                        restaurant = HomeActivity.dao.getRestaurantInformation(food.getRestaurantId());
                        CartCard card = new CartCard(getContext(), food, restaurant.getName(), orderDetail);
                        cartContainer.addView(card);
                    }
                }
                break;
            case "coming": {
                ArrayList<Order> orderArrayList = HomeActivity.dao.getOrderOfUser(HomeActivity.user.getId(), "Coming");
                if (orderArrayList.size() > 0) {
                    for (Order order : orderArrayList) {
                        OrderCard card = new OrderCard(getContext(), order);
                        card.setOnClickListener(view -> {
                            Intent intent = new Intent(getContext(), ViewOrderActivity.class);
                            intent.putExtra("order", order);
                            startActivity(intent);
                        });
                        cartContainer.addView(card);
                    }
                }
                break;
            }
            case "history": {
                ArrayList<Order> orderArrayList = HomeActivity.dao.getOrderOfUser(HomeActivity.user.getId(), "Delivered");
                if (orderArrayList.size() > 0) {
                    for (Order order : orderArrayList) {
                        OrderCard card = new OrderCard(getContext(), order);
                        card.setOnClickListener(view -> {
                            Intent intent = new Intent(getContext(), ViewOrderActivity.class);
                            intent.putExtra("order", order);
                            startActivity(intent);
                        });
                        cartContainer.addView(card);
                    }
                }
                break;
            }
        }
    }

    private void resetAttribute() {
        btnGioHang.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_white));
        btnDangDen.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_white));
        btnLichSu.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_white));
        tvGioHang.setTextColor(Color.BLACK);
        tvLichSu.setTextColor(Color.BLACK);
        tvDangDen.setTextColor(Color.BLACK);
    }
}