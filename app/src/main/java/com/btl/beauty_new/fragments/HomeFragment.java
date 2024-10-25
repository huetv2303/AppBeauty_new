package com.btl.beauty_new.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.btl.beauty_new.activity.ActivityImpl.CategoryActivity;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.R;
import com.btl.beauty_new.adapter.StoreAdapter;
import com.btl.beauty_new.repository.DAO;

import java.util.Timer;

public class HomeFragment extends Fragment {
    private Intent intent;
    private View mainView;
    private LinearLayout layout_container;
    private DAO dao;
    private ConstraintLayout btnOrderCosmetic;

    // loaf
    private ViewPager viewPager;
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
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_Store);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        StoreAdapter storeAdapter = new StoreAdapter(HomeActivity.dao.getStoreList());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(storeAdapter);




//        rootView.findViewById(R.id.imageCart).setOnClickListener(v -> {
//            intent = new Intent(getActivity(), HomeActivity.class);
//            intent.putExtra("request", "cart");
//            startActivity(intent);
//        });
//
//        rootView.findViewById(R.id.imageNotify).setOnClickListener(v -> {
//            intent = new Intent(getActivity(), HomeActivity.class);
//            intent.putExtra("request", "hint");
//            startActivity(intent);
//        });
//
//        rootView.findViewById(R.id.imageLogout).setOnClickListener(view -> {
//            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
//            dialog.setMessage("Bạn có muốn đăng xuất tài khoản ?");
//            dialog.setPositiveButton("Có", (dialogInterface, i) -> {
//                Toast.makeText(this.getActivity(), "Đã đăng xuất khỏi hệ thống!", Toast.LENGTH_SHORT).show();
//                requireActivity().finish();
//                startActivity(new Intent(getActivity(), SignInActivity.class));
//            });
//            dialog.setNegativeButton("Không", (dialogInterface, i) -> {
//            });
//            dialog.show();
//        });

        SearchView searchBar = rootView.findViewById(R.id.search_bar);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private boolean isSearchSubmitted = false;

            @Override
            public boolean onQueryTextSubmit(String s) {
                if (!isSearchSubmitted) {
                    isSearchSubmitted = true;
                    String textSearch = searchBar.getQuery().toString();
                    intent = new Intent(getActivity(), CategoryActivity.class);
                    intent.putExtra("nameCosmetic", textSearch);
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                isSearchSubmitted = false; // Reset flag nếu người dùng thay đổi text
                return false;
            }
        });


        return rootView;
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