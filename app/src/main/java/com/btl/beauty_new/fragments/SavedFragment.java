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
import com.btl.beauty_new.activity.ActivityImpl.CosmeticDetailsActivity;
import com.btl.beauty_new.activity.ActivityImpl.HomeActivity;
import com.btl.beauty_new.components.CosmeticSavedCard;
import com.btl.beauty_new.components.StoreCard;
import com.btl.beauty_new.model.Cosmetic;
import com.btl.beauty_new.model.CosmeticSaved;
import com.btl.beauty_new.model.CosmeticSize;
import com.btl.beauty_new.model.Store;
import com.btl.beauty_new.model.StoreSaved;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static LinearLayout saved_container;
    private LinearLayout btn_saved_cosmetic, btn_saved_store;
    private TextView tv_saved_cosmetic, tv_saved_store;
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
        btn_saved_cosmetic = mainView.findViewById(R.id.btn_saved_cosmetic);
        tv_saved_cosmetic = mainView.findViewById(R.id.tv_saved_cosmetic);
        btn_saved_store = mainView.findViewById(R.id.btn_saved_store);
        tv_saved_store = mainView.findViewById(R.id.tv_saved_store);
        searchView = mainView.findViewById(R.id.searchView); // Thêm dòng này

        // Sự kiện click cho nút "Lưu món ăn"
        btn_saved_cosmetic.setOnClickListener(view -> {
            btn_saved_cosmetic.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tv_saved_cosmetic.setTextColor(Color.BLUE);
            btn_saved_store.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_white));
            tv_saved_store.setTextColor(Color.BLACK);
            LoadSavedCard("cosmetic", "");
        });

        // Sự kiện click cho nút "Lưu nhà hàng"
        btn_saved_store.setOnClickListener(view -> {
            btn_saved_cosmetic.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_white));
            tv_saved_cosmetic.setTextColor(Color.BLACK);
            btn_saved_store.setBackground(ContextCompat.getDrawable(requireContext(), R.color.silver));
            tv_saved_store.setTextColor(Color.BLUE);

            LoadSavedCard("store", "");
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
                String type = btn_saved_cosmetic.getBackground().getConstantState().equals(ContextCompat.getDrawable(requireContext(), R.color.silver).getConstantState()) ? "cosmetic" : "store";
                LoadSavedCard(type, newText);  // Xử lý tìm kiếm trong onQueryTextChange
                return true;  // Trả về true để thông báo rằng sự kiện đã được xử lý
            }
        });

        // Tải danh sách món ăn đã lưu lúc đầu
        LoadSavedCard("cosmetic", "");

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

        if (type.equals("cosmetic")) {
            ArrayList<CosmeticSaved> cosmeticSavedArrayList = HomeActivity.dao.getCosmeticSaveList(HomeActivity.user.getId());

            if (cosmeticSavedArrayList.size() > 0) {
                for (CosmeticSaved cosmeticSaved : cosmeticSavedArrayList) {
                    Cosmetic cosmetic = HomeActivity.dao.getCosmeticById(cosmeticSaved.getCosmeticId());
                    // Loại bỏ dấu khỏi tên thực phẩm trước khi so sánh
                    String cosmeticName = removeAccents(cosmetic.getName().toLowerCase());
                    if (cosmeticName.contains(searchQuery)) {
                        Store store = HomeActivity.dao.getStoreInformation(cosmetic.getStoreId());
                        CosmeticSize cosmeticSize = HomeActivity.dao.getCosmeticSize(cosmeticSaved.getCosmeticId(), cosmeticSaved.getSize());

                        CosmeticSavedCard savedCard = new CosmeticSavedCard(getContext(), cosmetic, store.getName(), cosmeticSize);
                        savedCard.setOnClickListener(view -> {
                            CosmeticDetailsActivity.cosmeticSize = cosmeticSize;
                            Intent intent = new Intent(getContext(), CosmeticDetailsActivity.class);
                            intent.putExtra("cosmetic", cosmetic);
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
            ArrayList<StoreSaved> storeSavedArrayList = HomeActivity.dao.getStoreSavedList(HomeActivity.user.getId());

            for (StoreSaved storeSaved : storeSavedArrayList) {
                Store store = HomeActivity.dao.getStoreInformation(storeSaved.getStoreId());
                // Loại bỏ dấu khỏi tên nhà hàng trước khi so sánh
                String storeName = removeAccents(store.getName().toLowerCase());
                if (storeName.contains(searchQuery)) {
                    StoreCard card = new StoreCard(getContext(), store, true);
                    card.setOnClickListener(view -> {
                        Intent intent = new Intent(getActivity(), CategoryActivity.class);
                        intent.putExtra("storeId", store.getId());
                        startActivity(intent);
                    });
                    saved_container.addView(card);
                }
            }
        }
    }
}
