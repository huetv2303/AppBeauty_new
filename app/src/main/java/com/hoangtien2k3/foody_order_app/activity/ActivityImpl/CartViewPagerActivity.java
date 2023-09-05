package com.hoangtien2k3.foody_order_app.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.ViewPagerAdapter.ViewPagerAdapter;
import com.hoangtien2k3.foody_order_app.fragments.DeliveryFragment;
import com.hoangtien2k3.foody_order_app.fragments.OnOrderButtonClickListener;

// dùng để thiết lập hiển thị các Fragment lên Activity
public class CartViewPagerActivity extends AppCompatActivity implements OnOrderButtonClickListener {

    private ViewPager viewPager; // Đặt biến viewPager ở đây

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        TabLayout tableLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager); // Khai báo và gán biến viewPager ở đây

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tableLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onOrderButtonClicked(int position) {
        // Chuyển đến DeliveryFragment khi ấn thanh toán
        viewPager.setCurrentItem(position); // Chọn chỉ số của DeliveryFragment (1 là chuyển sang Fragment thứ 2 trong ViewPager)
    }
}
