package com.hoangtien2k3.foody_order_app.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
    private LinearLayout btnHome, btnProfile, btnCart, btnSupport, btnSetting;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Integer userID = user.getId(); // dữ liệu user đã được truyền vào ở phần đăng nhập tài khoản rồi

        FoodDetailsActivity.userID = userID; // truyền userID của người dùng qua FoodDetailsActivity
        ViewOrderActivity.userID = userID;   // truyền userID của người dùng qua ViewOrderActivity

        dao = new DAO(this);

        initializeUI();
        loadFragment(homeFragment); // setting: load mặc định - HomeFragment lên trên HomeActivity
        clickButtonNavigation();
    }

    @Override
    public void initializeUI() {
        homeFragment = new HomeFragment();
        savedFragment = new SavedFragment();
        notifyFragment = new NotifyFragment();
        profileFragment = new ProfileFragment();
        informationFragment = new CartFragment();

        btnHome = findViewById(R.id.homeBtn);
        btnProfile = findViewById(R.id.profileBtn);
        btnCart = findViewById(R.id.cartBtn);
        btnSupport = findViewById(R.id.supportBtn);
        btnSetting = findViewById(R.id.settingBtn);

        fragmentManager = getSupportFragmentManager();
    }


    // click vào bottiom để thay đổi
    @Override
    public void clickButtonNavigation() {
        btnHome.setOnClickListener(v -> loadFragment(homeFragment));
        btnProfile.setOnClickListener(v -> loadFragment(savedFragment));
        btnSupport.setOnClickListener(v -> loadFragment(notifyFragment));
        btnSetting.setOnClickListener(v -> loadFragment(profileFragment));

        // giỏ hàng: dùng ViewPager và Tablayout
        btnCart.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CartViewPagerActivity.class);
            startActivity(intent);
        });
    }

    // load Fragment
    private void loadFragment(Fragment fragmentReplace) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, fragmentReplace)
                .commit();
    }

}