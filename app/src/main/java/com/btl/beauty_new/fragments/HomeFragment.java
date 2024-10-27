package com.btl.beauty_new.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class HomeFragment extends Fragment {
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Khởi tạo RecyclerView và hiển thị danh sách nhà hàng
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView_Store);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        StoreAdapter storeAdapter = new StoreAdapter(HomeActivity.dao.getStoreList());
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(storeAdapter);

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

}