package com.btl.beauty_new.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.activity.FoodDetailsActivityImpl;
import com.btl.beauty_new.repository.DAO;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;
import com.btl.beauty_new.model.Food;
import com.btl.beauty_new.model.FoodSaved;
import com.btl.beauty_new.model.FoodSize;
import com.btl.beauty_new.model.Order;
import com.btl.beauty_new.model.OrderDetail;
import com.btl.beauty_new.model.Restaurant;

import java.util.ArrayList;

public class FoodDetailsActivity extends AppCompatActivity implements FoodDetailsActivityImpl {
    private ImageView image, btnAddQuantity, btnSubQuantity;
    private LinearLayout layout_sizeS, layout_sizeM, layout_sizeL, btnAddToCart, btnSavedFood;
    private CheckBox checkBoxFavorite, checkBoxCart;
    private TextView tvName, tvDescription, tvPrice,
            tvRestaurantName, tvRestaurantAddress,
            tvPriceSizeS, tvPriceSizeM, tvPriceSizeL,
            tvQuantity;

    //    private Button btnAddToCart, btnSavedFood;
    public static Integer userID; // lấy ra userId
    private static int quantity;
    public static FoodSize foodSize;    // lấy ra gias cả và kích thước
    private DAO dao;    // kết nối với CSDL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        quantity = 1;
        dao = new DAO(this);

        initializeUI();
        referenceComponent();
        LoadData();
    }

    private String getRoundPrice(Double price) {
        return Math.round(price) + " VNĐ";
    }

    private String getTotalPrice() {
        return Math.round(foodSize.getPrice() * quantity) + " VNĐ";
    }

    @Override
    public void initializeUI() {
        // initUI
        tvName = findViewById(R.id.tvFoodName);
        tvDescription = findViewById(R.id.tvDescription);
        tvPrice = findViewById(R.id.tvPrice);
        image = findViewById(R.id.image);

        layout_sizeS = findViewById(R.id.layout_size_S);
        layout_sizeM = findViewById(R.id.layout_size_M);
        layout_sizeL = findViewById(R.id.layout_size_L);

        tvPriceSizeS = findViewById(R.id.tvPriceSizeS);
        tvPriceSizeM = findViewById(R.id.tvPriceSizeM);
        tvPriceSizeL = findViewById(R.id.tvPriceSizeL);

        tvQuantity = findViewById(R.id.tvFoodQuantity_Food);

        tvRestaurantName = findViewById(R.id.tvRestaurantName);
        tvRestaurantAddress = findViewById(R.id.tvRestaurantAddress);

        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnSavedFood = findViewById(R.id.btnSavedFood);
        checkBoxCart = findViewById(R.id.checkBoxCart);
        checkBoxFavorite = findViewById(R.id.checkBoxFavorite);

        btnAddQuantity = findViewById(R.id.btnAddQuantity_Food);
        btnSubQuantity = findViewById(R.id.btnSubQuantity_Food);
    }

    @Override
    public void referenceComponent() {
        // quay về màn hình trước đó.
        findViewById(R.id.btnBack).setOnClickListener(view -> this.finish());

        btnAddToCart.setOnClickListener(view -> {
            addCartProduct();
        });

        checkBoxCart.setOnClickListener(view -> {
            addCartProduct();
        });

        btnSavedFood.setOnClickListener(view -> {
            saveFood();
        });

        checkBoxFavorite.setOnClickListener(view -> {
            saveFood();
        });

        // tăng số lượng món ăn
        btnAddQuantity.setOnClickListener(view -> {
            quantity++;
            tvQuantity.setText(String.format("%s", quantity));
            tvPrice.setText(getTotalPrice());
        });


        // giảm số lượng món ăn
        btnSubQuantity.setOnClickListener(view -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.format("%s", quantity));
                tvPrice.setText(getTotalPrice());
            }
        });
    }


    // đẩy thông tin vào giỏ hàng
    private void addCartProduct() {
        // Make cart if don't have
        Cursor cursor = dao.getCart(userID);

        // (di chuyển con trỏ Cursor đến vị trí đúng trước khi truy cập dữ liệu.)
        // và kiểm tra xem cursor này đang không trỏ đến vị trí đầu tiên không.
        if (!cursor.moveToFirst()) {
            dao.addOrder(new Order(1, userID, "", "", 0d, "Craft")); // ta sẽ add một order và (hay thêm vào phần giỏ hàng)
            cursor = dao.getCart(userID); // lấy ra đối tượng cursor đấy với userID
//            System.out.println("đã được add đối tượng vào đây rồi.");
        }

        // add order detail
        cursor.moveToFirst();   // (di chuyển con trỏ Cursor đến vị trí đúng trước khi truy cập dữ liệu.)

        OrderDetail orderDetail = dao.getExistOrderDetail(cursor.getInt(0), foodSize);
        if (orderDetail != null) {
            orderDetail.setQuantity(orderDetail.getQuantity() + quantity);
            if (dao.updateQuantity(orderDetail)) {
                Toast.makeText(this, getResources().getString(R.string.add_dish_successfully), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.add_error), Toast.LENGTH_SHORT).show();
            }

        } else {
            boolean addOrderDetail = dao.addOrderDetail(new OrderDetail(cursor.getInt(0),
                    foodSize.getFoodId(), foodSize.getSize(), foodSize.getPrice(), quantity));

            if (addOrderDetail) {
                Toast.makeText(this, getResources().getString(R.string.add_dish_successfully), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getResources().getString(R.string.add_error), Toast.LENGTH_SHORT).show();
            }
        }
    }


    // lưu lại thông tin món ăn
    private void saveFood() {
        boolean addFoodSaved = dao.addFoodSaved(new FoodSaved(foodSize.getFoodId(), foodSize.getSize(), userID));
        if (addFoodSaved) {
            Toast.makeText(this, getResources().getString(R.string.SAVED_DISHED), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.INFORMATION_EXISTED), Toast.LENGTH_SHORT).show();
        }
    }


    // setting giá mặc định
    private void SetPriceDefault(Double price) {
        tvPrice.setText(getRoundPrice(price));
        quantity = 1;
        tvQuantity.setText("1");
    }

    // load để đẩy tất cả thông tin về sản phẩm lên
    @Override
    public void LoadData() {
        Intent intent = getIntent();
        if (intent != null) {
            Food food = (Food) intent.getSerializableExtra("food");

            assert food != null;
            ArrayList<FoodSize> foodSizeArrayList = dao.getAllFoodSize(food.getId());

            // size S
            if (foodSizeArrayList.get(0) != null) {
                if (foodSize == null) foodSize = foodSizeArrayList.get(0);
                tvPriceSizeS.setText(String.format("+ %s", foodSizeArrayList.get(0).getPrice()));
                layout_sizeS.setOnClickListener(view -> {
                    SetPriceDefault(foodSizeArrayList.get(0).getPrice());
                    foodSize = foodSizeArrayList.get(0);
                });
            } else {
                layout_sizeS.setVisibility(View.INVISIBLE);
            }

            // size M
            if (foodSizeArrayList.get(1) != null) {
                tvPriceSizeM.setText(String.format("+ %s", foodSizeArrayList.get(1).getPrice()));
                layout_sizeM.setOnClickListener(view -> {
                    SetPriceDefault(foodSizeArrayList.get(1).getPrice());
                    foodSize = foodSizeArrayList.get(1);
                });
            } else {
                layout_sizeM.setVisibility(View.INVISIBLE);
            }

            // Size L
            if (foodSizeArrayList.get(2) != null) {
                tvPriceSizeL.setText(String.format("+ %s", foodSizeArrayList.get(2).getPrice()));
                layout_sizeL.setOnClickListener(view -> {
                    SetPriceDefault(foodSizeArrayList.get(2).getPrice());
                    foodSize = foodSizeArrayList.get(2);
                });
            } else {
                layout_sizeL.setVisibility(View.INVISIBLE);
            }

            // Set information: đẩy lên các thông tin cơ bản (tên, miêu tả, ảnh)
            tvName.setText(food.getName());
            tvDescription.setText(food.getDescription());
            image.setImageBitmap(DatabaseHandler.convertByteArrayToBitmap(food.getImage()));

            Restaurant restaurant = dao.getRestaurantInformation(food.getRestaurantId());
            tvRestaurantName.setText(String.format("+ Tên cửa hàng \n \t\t %s", restaurant.getName()));
            tvRestaurantAddress.setText(String.format("+ Địa chỉ\n \t\t %s", restaurant.getAddress()));

            // set giá tổng
            tvPrice.setText(getRoundPrice(foodSize.getPrice()));
        }
    }
}