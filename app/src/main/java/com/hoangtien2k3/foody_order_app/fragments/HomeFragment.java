package com.hoangtien2k3.foody_order_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.HomeActivity;
import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.adapter.RestaurantAdapter;
import com.hoangtien2k3.foody_order_app.image.Photo;
import com.hoangtien2k3.foody_order_app.image.PhotoAdapter;
import com.hoangtien2k3.foody_order_app.repository.DAO;
import com.hoangtien2k3.foody_order_app.repositoryInit.DataInitFragmentHome;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private Intent intent;
    private View mainView;
    private LinearLayout layout_container;
    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;
    private DAO dao;

    private ConstraintLayout btnOrderFood;


    // loaf
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> listPhoto; // danh sách các ảnh để hiển thị
    private Timer timer; // thằng này sẽ sét thời gian các ảnh hiển thị



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        recyclerView = rootView.findViewById(R.id.recyclerView_Restaurant);
        restaurantAdapter = new RestaurantAdapter(HomeActivity.dao.getRestaurantList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(restaurantAdapter);


        // Khởi tạo ViewPager và PhotoAdapter
        viewPager = rootView.findViewById(R.id.viewpager);
        circleIndicator = rootView.findViewById(R.id.circle_indicator);

        listPhoto = DataInitFragmentHome.listPhoto;
        photoAdapter = new PhotoAdapter(requireContext(), listPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSildeImage(); // thiết lập thời gian hiển thị ảnh

        return rootView;
    }

    // thiết lập thời gian lặp lại ảnh
    private void autoSildeImage() {
        if (listPhoto == null || listPhoto.isEmpty() || viewPager == null) return;

        // init timer
        if (timer == null) timer = new Timer();
        timer.schedule(new TimerTask() {
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
        }, 500, 3000);
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