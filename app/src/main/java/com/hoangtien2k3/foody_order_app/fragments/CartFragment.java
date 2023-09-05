package com.hoangtien2k3.foody_order_app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.CartViewPagerActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.PaymentActivity;
import com.hoangtien2k3.foody_order_app.components.CartCard;
import com.hoangtien2k3.foody_order_app.model.Food;
import com.hoangtien2k3.foody_order_app.model.OrderDetail;
import com.hoangtien2k3.foody_order_app.model.Restaurant;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    

    private View mainView;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout cartContainer;
    private static String status;


    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Information1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_cart, container, false);
        cartContainer = mainView.findViewById(R.id.cartContainerCart);

        referencesComponent();
        LoadOrder("craft");
        status = "craft";

        return mainView;
    }


    private void referencesComponent() {
        LoadOrder("craft"); // load thông tin giỏ hàng lên LinearLayout

        // nhấn nút trên Fragment để load lại thông tin
        LinearLayout btnUpdateCart = mainView.findViewById(R.id.btnUpdateCart);
        btnUpdateCart.setOnClickListener(v -> {
            LoadOrder("craft");
            Toast.makeText(mainView.getContext(), getResources().getString(R.string.load_data), Toast.LENGTH_SHORT).show();
        });


        // thanh toán đơn hàng
        Button btnThanhToan = mainView.findViewById(R.id.btnOrderFood);
        btnThanhToan.setOnClickListener(view -> {
            if (!status.equals("craft")) return;
            Cursor cursor = HomeActivity.dao.getCart(HomeActivity.user.getId());
            if (!cursor.moveToFirst()) return;

            PaymentActivity.user = HomeActivity.user;
            Intent intent = new Intent(getActivity(), PaymentActivity.class);
            intent.putExtra("orderId", cursor.getInt(0));
            startActivity(intent);

        });

    }

    private void LoadOrder(String type) {
        status = type;
        cartContainer.removeAllViews();

        Cursor cursor = HomeActivity.dao.getCart(HomeActivity.user.getId());
        if (!cursor.moveToFirst()) return;
        cursor.moveToFirst(); // trỏ đến vị trí đầu tiên
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
    }

    private void nextFragment(boolean checkClickPayment, int position) {
        // sau khi thanh toán, sẽ tự động chuyển qua DeliveryFragment
        if (getActivity() instanceof OnOrderButtonClickListener && checkClickPayment) {
            ((OnOrderButtonClickListener) getActivity()).onOrderButtonClicked(position);
        }
    }

}