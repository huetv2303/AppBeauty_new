package com.hoangtien2k3.foody_order_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.CategoryActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.adapter.RestaurantAdapter;
import com.hoangtien2k3.foody_order_app.imageBanner.Photo;
import com.hoangtien2k3.foody_order_app.imageBanner.PhotoAdapter;
import com.hoangtien2k3.foody_order_app.repository.DAO;
import com.hoangtien2k3.foody_order_app.repositoryInit.DataInitFragmentHome;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private Intent intent;
    private View mainView;
    private LinearLayout layout_container;
    private DAO dao;
    private ConstraintLayout btnOrderFood;

    // loaf
    private ViewPager viewPager;
    private List<Photo> listPhoto; // danh sách các ảnh để hiển thị
    private Timer timer; // thằng này sẽ sét thời gian các ảnh hiển thị

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Khởi tạo RecyclerView và hiển thị danh sách nhà hàng
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_Restaurant);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(HomeActivity.dao.getRestaurantList());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(restaurantAdapter);

        // Khởi tạo ViewPager và PhotoAdapter
        viewPager = rootView.findViewById(R.id.viewpager);
        CircleIndicator circleIndicator = rootView.findViewById(R.id.circle_indicator);

        listPhoto = DataInitFragmentHome.listPhoto;
        PhotoAdapter photoAdapter = new PhotoAdapter(requireContext(), listPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSildeImage(); // thiết lập thời gian hiển thị ảnh

        rootView.findViewById(R.id.imageCart).setOnClickListener(v -> {
            intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("request", "cart");
            startActivity(intent);
        });


//        SearchView searchBar = mainView.findViewById(R.id.search_bar);
//        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                String textSearch = searchBar.getQuery().toString();
//                intent = new Intent(getActivity(), CategoryActivity.class);
//                intent.putExtra("nameFood", textSearch);
//                startActivity(intent);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                return false;
//            }
//        });

        return rootView;
    }

    // thiết lập thời gian lặp lại ảnh
    private void autoSildeImage() {
        if (listPhoto == null || listPhoto.isEmpty() || viewPager == null) return;

        // init timer
        if (timer == null) timer = new Timer();
        timer.schedule((new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(() -> {
                    int currentItem = viewPager.getCurrentItem();
                    int totalItem = listPhoto.size() - 1;
                    if (currentItem < totalItem) {
                        currentItem++;
                        viewPager.setCurrentItem(currentItem);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                });
            }
        }), 500, 3000);
    }

    // hủy thằng timer đi, nếu fragment không tồn tại nữa
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}