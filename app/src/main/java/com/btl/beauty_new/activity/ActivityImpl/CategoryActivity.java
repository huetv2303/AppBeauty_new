package com.btl.beauty_new.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.CategoryActivityImpl;
import com.btl.beauty_new.components.CosmeticCard;
import com.btl.beauty_new.model.Cosmetic;
import com.btl.beauty_new.repository.DAO;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.model.CosmeticSize;
import com.btl.beauty_new.model.Store;


import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements CategoryActivityImpl {
    private LinearLayout cosmeticContainer;
    private DAO dao;
    private Intent intent_get_data;
    private Integer storeId;

    private ImageView image, storeImage;
    private TextView tvstoreName, tvstoreAddress, tvstorePhone;
    private SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        intent_get_data = getIntent();
        dao = new DAO(this);

        initializeUI();
        //setupSearchBar();
        loadStoreInformation();
        loadCosmeticData(null);
    }


    @Override
    public void initializeUI() {
        image = findViewById(R.id.imageCartC);
        storeImage = findViewById(R.id.imageStore_category);
        tvstoreName = findViewById(R.id.tvStoreName_category);
        tvstoreAddress = findViewById(R.id.tvStoreAddress_category);
        tvstorePhone = findViewById(R.id.tvStorePhone_category);
        cosmeticContainer = findViewById(R.id.cosmeticContainer);
        searchBar = findViewById(R.id.search_bar);
    }

    @Override
    public void setupSearchBar() {
        // thanh tìm kiếm thông tin
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String nameCosmeticOfThisstore = searchBar.getQuery().toString();
                loadCosmeticData(nameCosmeticOfThisstore);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    @Override
    public void loadStoreInformation() {
        // thoat về trang chủ
        image.setOnClickListener(view -> finish());


        // tìm kiếm thông tin về mỹ phẩm trên danh sách mỹ phâm.
        setupSearchBar();

        // store data: đẩy thông tin lên
        LinearLayout layoutstore = findViewById(R.id.layout_storeInformation);
        storeId = intent_get_data.getIntExtra("storeId", -1);
        if (storeId != -1) {
            Store store = dao.getStoreInformation(storeId);
            storeImage.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(store.getImage()));
            tvstoreName.setText(store.getName());
            tvstoreAddress.setText(String.format("\t+ Địa Chỉ: %s", store.getAddress()));
            tvstorePhone.setText(String.format("\t+ Số Điện Thoại: %s", store.getPhone()));
        } else {
            layoutstore.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadCosmeticData(String nameCosmeticOfThisStore) {
        cosmeticContainer.removeAllViews(); // Xóa tất cả các view hiện có trong cosmeticCartContainer

        ArrayList<Cosmetic> cosmeticArrayList; // Khai báo danh sách mỹ phẩm

        // Kiểm tra nếu tên mỹ phẩm là null
        if (nameCosmeticOfThisStore == null) {
            int getstoreId = intent_get_data.getIntExtra("storeId", -1); // Lấy storeId từ intent
            System.out.println(getstoreId); // In ra storeId để kiểm tra
            if (getstoreId == -1) {
                String cosmeticKeyword = intent_get_data.getStringExtra("nameCosmetic"); // Lấy tên mỹ phẩm từ intent
                cosmeticArrayList = dao.getCosmeticByKeyWord(cosmeticKeyword, null); // Gọi DAO để lấy danh sách mỹ phẩm theo từ khóa
                System.out.println(cosmeticArrayList); // In ra danh sách mỹ phẩm
            } else {
                cosmeticArrayList = dao.getCosmeticBystore(getstoreId); // Gọi DAO để lấy danh sách mỹ phẩm theo storeId
            }
        } else {
            cosmeticArrayList = dao.getCosmeticByKeyWord(nameCosmeticOfThisStore, storeId); // Lấy danh sách mỹ phẩm theo tên từ searchBar
        }

        // Duyệt qua danh sách mỹ phẩm và tạo CosmeticCard cho mỗi sản phẩm
        for (Cosmetic cosmetic : cosmeticArrayList) {
            Store store = dao.getStoreInformation(cosmetic.getStoreId()); // Lấy thông tin cửa hàng dựa trên storeId của mỹ phẩm
            CosmeticSize cosmeticSize = dao.getCosmeticDefaultSize(cosmetic.getId()); // Lấy kích thước mặc định của mỹ phẩm

            // Tạo CosmeticCard với thông tin mỹ phẩm, giá và tên cửa hàng
            CosmeticCard cosmeticCard = new CosmeticCard(this, cosmetic, cosmeticSize.getPrice(), store.getName());

            // Đặt listener cho CosmeticCard để mở chi tiết mỹ phẩm khi nhấn
            cosmeticCard.setOnClickListener(view -> {
                CosmeticDetailsActivity.cosmeticSize = cosmeticSize; // Lưu kích thước mỹ phẩm vào activity chi tiết
                Intent intent = new Intent(this, CosmeticDetailsActivity.class); // Tạo intent để mở CosmeticDetailsActivity
                intent.putExtra("cosmetic", cosmetic); // Truyền thông tin mỹ phẩm qua intent
                try {
                    startActivity(intent); // Mở activity chi tiết
                } catch (Exception e) {
                    // Hiển thị thông báo nếu không mở được activity
                    Toast.makeText(this, getResources().getString(R.string.not_view_information), Toast.LENGTH_SHORT).show();
                }
            });

            // Thêm CosmeticCard vào cosmeticCartContainer
            cosmeticContainer.addView(cosmeticCard);
        }

    }
}