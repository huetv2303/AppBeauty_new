package com.hoangtien2k3.foody_order_app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.CategoryActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.FoodDetailsActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.hoangtien2k3.foody_order_app.components.FoodSavedCard;
import com.hoangtien2k3.foody_order_app.components.RestaurantCard;
import com.hoangtien2k3.foody_order_app.model.Food;
import com.hoangtien2k3.foody_order_app.model.FoodSaved;
import com.hoangtien2k3.foody_order_app.model.FoodSize;
import com.hoangtien2k3.foody_order_app.model.Restaurant;
import com.hoangtien2k3.foody_order_app.model.RestaurantSaved;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static LinearLayout saved_container;
    private LinearLayout btn_saved_food, btn_saved_restaurant;
    private TextView tv_saved_food, tv_saved_restaurant;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public SavedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_saved, container, false);

        saved_container = mainView.findViewById(R.id.layout_saved);
        btn_saved_food = mainView.findViewById(R.id.btn_saved_food);
        tv_saved_food = mainView.findViewById(R.id.tv_saved_food);
        btn_saved_restaurant = mainView.findViewById(R.id.btn_saved_restaurant);
        tv_saved_restaurant = mainView.findViewById(R.id.tv_saved_restaurant);

        btn_saved_food.setOnClickListener(view -> {
            btn_saved_food.setBackground(ContextCompat.getDrawable(requireContext(),R.color.silver));
            tv_saved_food.setTextColor(Color.WHITE);
            btn_saved_restaurant.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_white));
            tv_saved_restaurant.setTextColor(Color.BLACK);

            LoadSavedCard("food");
        });

        btn_saved_restaurant.setOnClickListener(view -> {
            btn_saved_food.setBackground(ContextCompat.getDrawable(requireContext(),R.drawable.bg_white));
            tv_saved_food.setTextColor(Color.BLACK);
            btn_saved_restaurant.setBackground(ContextCompat.getDrawable(requireContext(),R.color.silver));
            tv_saved_restaurant.setTextColor(Color.WHITE);

            LoadSavedCard("restaurant");
        });

        LoadSavedCard("food");

        return mainView;
    }

    private void LoadSavedCard(String type){
        saved_container.removeAllViews();

        if(type.equals("food")){
            ArrayList<FoodSaved> foodSavedArrayList = HomeActivity.dao.getFoodSaveList(HomeActivity.user.getId());

            if(foodSavedArrayList.size() > 0){

                for(FoodSaved foodSaved : foodSavedArrayList){
                    Food food = HomeActivity.dao.getFoodById(foodSaved.getFoodId());
                    Restaurant restaurant = HomeActivity.dao.getRestaurantInformation(food.getRestaurantId());
                    FoodSize foodSize = HomeActivity.dao.getFoodSize(foodSaved.getFoodId(), foodSaved.getSize());

                    FoodSavedCard savedCard = new FoodSavedCard(getContext(), food, restaurant.getName(), foodSize);
                    savedCard.setOnClickListener(view -> {
                        FoodDetailsActivity.foodSize = foodSize;
                        Intent intent = new Intent(getContext(), FoodDetailsActivity.class);
                        intent.putExtra("food", food);
                        try {
                            startActivity(intent);
                        } catch (Exception e){
                            Toast.makeText(getContext(), "Không thể hiển thị thông tin!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    saved_container.addView(savedCard);
                }

            }
        } else {

            ArrayList<RestaurantSaved> restaurantSavedArrayList = HomeActivity.dao.getRestaurantSavedList(HomeActivity.user.getId());

            for(RestaurantSaved restaurantSaved : restaurantSavedArrayList){
                Restaurant restaurant = HomeActivity.dao.getRestaurantInformation(restaurantSaved.getRestaurantId());
                RestaurantCard card = new RestaurantCard(getContext(), restaurant, true);
                card.setOnClickListener(view -> {
                    Intent intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("restaurantId", restaurant.getId());
                    startActivity(intent);
                });
                saved_container.addView(card);
            }

        }
    }
}