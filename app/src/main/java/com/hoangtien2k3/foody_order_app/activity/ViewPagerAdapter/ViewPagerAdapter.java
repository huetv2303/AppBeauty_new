package com.hoangtien2k3.foody_order_app.activity.ViewPagerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.hoangtien2k3.foody_order_app.fragments.CartFragment;
import com.hoangtien2k3.foody_order_app.fragments.DeliveryFragment;
import com.hoangtien2k3.foody_order_app.fragments.HistoryFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 3; // số lượng Fragment trong TabLayout
    private static final String[] PAGE_TITLES = {"Giỏ Hàng", "Vận Chuyển", "Lịch Sử"};

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new DeliveryFragment();
            case 2:
                return new HistoryFragment();
            default:
                return new CartFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < NUM_PAGES) {
            return PAGE_TITLES[position];
        }
        return super.getPageTitle(position);
    }
}
