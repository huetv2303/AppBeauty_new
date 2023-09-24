package com.hoangtien2k3.foody_order_app.activity.ActivityImpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.HomeActivityImpl;
import com.hoangtien2k3.foody_order_app.repository.DAO;
import com.hoangtien2k3.foody_order_app.fragments.HomeFragment;
import com.hoangtien2k3.foody_order_app.fragments.CartFragment;
import com.hoangtien2k3.foody_order_app.fragments.NotifyFragment;
import com.hoangtien2k3.foody_order_app.fragments.ProfileFragment;
import com.hoangtien2k3.foody_order_app.fragments.SavedFragment;
import com.hoangtien2k3.foody_order_app.model.User;

public class HomeActivity extends AppCompatActivity implements HomeActivityImpl {
    public static DAO dao;
    public static User user;
    private Fragment homeFragment, savedFragment, notifyFragment, profileFragment, informationFragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private static int clickToLogout;
    private static int stackLayout = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Integer userID = user.getId(); // dữ liệu user đã được truyền vào ở phần đăng nhập tài khoản rồi
        FoodDetailsActivity.userID = userID; // truyền userID của người dùng qua FoodDetailsActivity
        ViewOrderActivity.userID = userID;   // truyền userID của người dùng qua ViewOrderActivity
        dao = new DAO(this);

        initializeUI();

        stackLayout++;
        clickToLogout = 0;

        clickButtonNavigation();
    }

    @Override
    public void initializeUI() {
        homeFragment = new HomeFragment();
        savedFragment = new SavedFragment();
        notifyFragment = new NotifyFragment();
        profileFragment = new ProfileFragment();
        informationFragment = new CartFragment();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void clickButtonNavigation() {
        loadFragment(homeFragment); // setting: load mặc định - HomeFragment lên trên HomeActivity
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.action_home) {
                    loadFragment(homeFragment);
                } else if (item.getItemId() == R.id.action_favorites) {
                    loadFragment(savedFragment);
                } else if (item.getItemId() == R.id.action_category) {
                    Intent intent = new Intent(HomeActivity.this, CartViewPagerActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.action_notify) {
                    loadFragment(notifyFragment);
                } else if (item.getItemId() == R.id.action_profile) {
                    loadFragment(profileFragment);
                } else {
                    return true;
                }

                return true;
            }
        });
    }

    // load Fragment
    private void loadFragment(Fragment fragmentReplace) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragmentReplace)
                .commit();
    }


    @Override
    public void onBackPressed() {
        System.out.println(stackLayout);
        if(stackLayout < 2){
            clickToLogout++;

            if(clickToLogout > 1){
                finish();
                stackLayout--;
            } else {
                Toast.makeText(this, "Click thêm lần nữa để đăng xuất!", Toast.LENGTH_SHORT).show();
            }

            new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    clickToLogout = 0;
                }
            }.start();
        } else {
            stackLayout--;
            super.onBackPressed();
        }
    }

}