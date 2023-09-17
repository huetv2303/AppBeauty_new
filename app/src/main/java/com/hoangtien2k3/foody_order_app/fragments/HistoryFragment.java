package com.hoangtien2k3.foody_order_app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.ViewOrderActivity;
import com.hoangtien2k3.foody_order_app.components.OrderCard;
import com.hoangtien2k3.foody_order_app.model.Order;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private View mainView;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout cartContainer;


    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        mainView = inflater.inflate(R.layout.fragment_history, container, false);
        cartContainer = mainView.findViewById(R.id.cartContainerHistory);

        referencesComponent();
        LoadOrder();

        return mainView;
    }


    ////////////////////////////
    public void referencesComponent() {
        LoadOrder();

        LinearLayout btnUpdateHistory = mainView.findViewById(R.id.btnUpdateHistory);
        btnUpdateHistory.setOnClickListener(v -> {
            LoadOrder();
            Toast.makeText(mainView.getContext(), getResources().getString(R.string.load_data), Toast.LENGTH_SHORT).show();
        });
    }

    private void LoadOrder() {
        cartContainer.removeAllViews();

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
    }

}