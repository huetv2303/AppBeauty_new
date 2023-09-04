package com.hoangtien2k3.foody_order_app.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.adapter.CategoryAdapter;
import com.hoangtien2k3.foody_order_app.adapter.PopularAdapter;
import com.hoangtien2k3.foody_order_app.repository.DAO;
import com.hoangtien2k3.foody_order_app.model.Category;
import com.hoangtien2k3.foody_order_app.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PopularAdapter popularAdapterRestaurant;
    private CategoryAdapter categoryAdapter;
    private RecyclerView recyclerViewPopularRestaurant, recyclerViewCategory;
    private static DAO dao;
    private Fragment homeFragment, savedFragment, chatFragment, notifyFragment, profileFragment;
    private LinearLayout homeLayout;
    private FrameLayout frameLayoutHome;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new DAO(this);
        // ảnh xạ sang đối tượng khác
        ClickNextLayout();

        setRecyclerViewCategory();
        setRecyclerViewPopularRestaurant();
    }

    public void ClickNextLayout() {
        homeLayout = findViewById(R.id.homeLayout);
        homeLayout.setOnClickListener(view -> startActivity(new Intent(this, HomeActivity.class)));
    }

    private void setRecyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory = findViewById(R.id.recyclerView1);
        recyclerViewCategory.setLayoutManager(linearLayoutManager);

        List<Category> listCategory = new ArrayList<>();
        listCategory.add(new Category("Đồ uống", null));
        listCategory.add(new Category("Nước Lọc", null));
        listCategory.add(new Category("CoCa-CoLa", null));
        listCategory.add(new Category("Bánh Mỳ", null));
        listCategory.add(new Category("Dưa leo", null));
        listCategory.add(new Category("Chuối Chiên", null));
        listCategory.add(new Category("Ngũ Cốc", null));
        listCategory.add(new Category("Hoa quả", null));
        listCategory.add(new Category("Chè bưởi", null));

        categoryAdapter = new CategoryAdapter(listCategory);
        recyclerViewCategory.setAdapter(categoryAdapter);
    }

    private void setRecyclerViewPopularRestaurant() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularRestaurant = findViewById(R.id.recyclerView2);
        recyclerViewPopularRestaurant.setLayoutManager(linearLayoutManager);

        // region Restaurant
        ArrayList<Restaurant> restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(1, "Quán bánh", "24/63 Phùng Khoang",
                "0631335935", null));restaurantList.add(new Restaurant(1, "Quán bánh", "24/63 Phùng Khoang",
                "0631335935", null));restaurantList.add(new Restaurant(1, "Quán bánh", "24/63 Phùng Khoang",
                "0631335935", null));restaurantList.add(new Restaurant(1, "Quán bánh", "24/63 Phùng Khoang",
                "0631335935", null));

        popularAdapterRestaurant = new PopularAdapter(restaurantList);
        recyclerViewPopularRestaurant.setAdapter(popularAdapterRestaurant);
    }
}