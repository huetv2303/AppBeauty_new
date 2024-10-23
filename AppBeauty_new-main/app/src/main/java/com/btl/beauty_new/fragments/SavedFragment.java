package com.btl.beauty_new.fragments;

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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.ActivityImpl.CategoryActivity;
import com.btl.beauty_new.activity.ActivityImpl.FoodDetailsActivity;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.components.FoodSavedCard;
import com.btl.beauty_new.components.RestaurantCard;
import com.btl.beauty_new.model.Food;
import com.btl.beauty_new.model.FoodSaved;
import com.btl.beauty_new.model.FoodSize;
import com.btl.beauty_new.model.Restaurant;
import com.btl.beauty_new.model.RestaurantSaved;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static LinearLayout saved_container;
    private LinearLayout btn_saved_food, btn_saved_restaurant;
    private TextView tv_saved_food, tv_saved_restaurant;
    private SearchView searchView;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public SavedFragment() {
        // Required empty public constructor
    }

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
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_saved, container, false);

        // Khai báo các thành phần giao diện
        saved_container = mainView.findViewById(R.id.layout_saved);
        btn_saved_food = mainView.findViewById(R.id.btn_saved_food);
        tv_saved_food = mainView.findViewById(R.id.tv_saved_food);
        btn_saved_restaurant = mainView.findViewById(R.id.btn_saved_restaurant);
        tv_saved_restaurant = mainView.findViewById(R.id.tv_saved_restaurant);
        searchView = mainView.findViewById(R.id.searchView); // Thêm dòng này

        // Sự kiện click cho nút "Lưu món ăn"
        btn_saved_food.setOnClickListener(view -> {
            btn_saved_food.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tv_saved_food.setTextColor(Color.BLUE);
            btn_saved_restaurant.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_white));
            tv_saved_restaurant.setTextColor(Color.BLACK);
            LoadSavedCard("food", "");
        });

        // Sự kiện click cho nút "Lưu nhà hàng"
        btn_saved_restaurant.setOnClickListener(view -> {
            btn_saved_food.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_white));
            tv_saved_food.setTextColor(Color.BLACK);
            btn_saved_restaurant.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tv_saved_restaurant.setTextColor(Color.BLUE);

            LoadSavedCard("restaurant", "");
        });

        // Xử lý sự kiện tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Không cần xử lý trong onQueryTextSubmit
                return false;  // Trả về false để cho phép tìm kiếm tiếp tục
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String type = btn_saved_food.getBackground().getConstantState().equals(ContextCompat.getDrawable(requireContext(), R.color.silver).getConstantState()) ? "food" : "restaurant";
                LoadSavedCard(type, newText);  // Xử lý tìm kiếm trong onQueryTextChange
                return true;  // Trả về true để thông báo rằng sự kiện đã được xử lý
            }
        });

        // Tải danh sách món ăn đã lưu lúc đầu
        LoadSavedCard("food", "");

        return mainView;
    }

    //Hàm xử lý dấu tiếng việt
    public static String removeAccents(String input) {
        if (input == null) {
            return null;
        }
        // Chuẩn hóa chuỗi về Unicode, sau đó xóa dấu
        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }
    private void LoadSavedCard(String type, String searchQuery) {
        saved_container.removeAllViews();

        // Loại bỏ dấu khỏi từ khóa tìm kiếm
        searchQuery = removeAccents(searchQuery.toLowerCase());

        if (type.equals("food")) {
            ArrayList<FoodSaved> foodSavedArrayList = HomeActivity.dao.getFoodSaveList(HomeActivity.user.getId());

            if (foodSavedArrayList.size() > 0) {
                for (FoodSaved foodSaved : foodSavedArrayList) {
                    Food food = HomeActivity.dao.getFoodById(foodSaved.getFoodId());
                    // Loại bỏ dấu khỏi tên thực phẩm trước khi so sánh
                    String foodName = removeAccents(food.getName().toLowerCase());
                    if (foodName.contains(searchQuery)) {
                        Restaurant restaurant = HomeActivity.dao.getRestaurantInformation(food.getRestaurantId());
                        FoodSize foodSize = HomeActivity.dao.getFoodSize(foodSaved.getFoodId(), foodSaved.getSize());

                        FoodSavedCard savedCard = new FoodSavedCard(getContext(), food, restaurant.getName(), foodSize);
                        savedCard.setOnClickListener(view -> {
                            FoodDetailsActivity.foodSize = foodSize;
                            Intent intent = new Intent(getContext(), FoodDetailsActivity.class);
                            intent.putExtra("food", food);
                            try {
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Không thể hiển thị thông tin!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        saved_container.addView(savedCard);
                    }
                }
            }
        } else {
            ArrayList<RestaurantSaved> restaurantSavedArrayList = HomeActivity.dao.getRestaurantSavedList(HomeActivity.user.getId());

            for (RestaurantSaved restaurantSaved : restaurantSavedArrayList) {
                Restaurant restaurant = HomeActivity.dao.getRestaurantInformation(restaurantSaved.getRestaurantId());
                // Loại bỏ dấu khỏi tên nhà hàng trước khi so sánh
                String restaurantName = removeAccents(restaurant.getName().toLowerCase());
                if (restaurantName.contains(searchQuery)) {
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
}
