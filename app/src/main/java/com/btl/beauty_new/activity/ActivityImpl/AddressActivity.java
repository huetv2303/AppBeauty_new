package com.btl.beauty_new.activity.ActivityImpl;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.btl.beauty_new.R;
import com.btl.beauty_new.adapter.AddressAdapter;
import com.btl.beauty_new.fragments.SavedFragment;
import com.btl.beauty_new.model.Address;
import com.btl.beauty_new.repository.DAO;
import com.btl.beauty_new.repositoryInit.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity {
    private LinearLayout btn_address;
    private ImageView btnback;
    private ListView lv_address;
    private DAO dao;
    public static int userId;
    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        dao = new DAO(this);
        db = new DatabaseHandler(this);
       initializeUI();
        back();
        InsertAddress();
       List<Address> addresses = dao.getAllAddresses(userId);
        AddressAdapter adapter = new AddressAdapter(this, addresses);
        lv_address.setAdapter(adapter);
        Toast.makeText(this, "User :" + userId , Toast.LENGTH_SHORT).show();
      // reload();
        lv_address.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Address address = (Address) adapterView.getItemAtPosition(i);
                chooseMethod(address);
                return false;
            }
        });


    }

    public void initializeUI() {
        btn_address = findViewById(R.id.btn_address);
        lv_address = findViewById(R.id.lv_address);
        btnback = findViewById(R.id.btnback);

    }

    public void back() {
        btnback.setOnClickListener(view -> finish());
    }

    public void InsertAddress() {
        btn_address.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
            builder.setTitle("Thêm địa chỉ mới"); // Title

            LinearLayout layout = new LinearLayout(AddressActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(16, 16, 16, 16);

            // Set LayoutParams for EditText
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 30, 30); // Set margins here

            // Create EditText for name
            final EditText name = new EditText(AddressActivity.this);
            name.setHint("Nhập tên người nhận");
            name.setLayoutParams(params);
            layout.addView(name);

            // Create EditText for phone
            final EditText phone = new EditText(AddressActivity.this);
            phone.setHint("Nhập số điện thoại");
            phone.setInputType(InputType.TYPE_CLASS_PHONE); // Only allow phone input
            phone.setLayoutParams(params);
            layout.addView(phone);

            // Create EditText for address
            final EditText building = new EditText(AddressActivity.this);
            building.setHint("Tòa nhà, số tầng...");
            building.setLayoutParams(params);
            layout.addView(building);

            // Create EditText for gate
            final EditText gate = new EditText(AddressActivity.this);
            gate.setHint("Cổng(không bắt buộc)");
            gate.setLayoutParams(params);
            layout.addView(gate);

            final RadioGroup radioGroup = new RadioGroup(AddressActivity.this);
            radioGroup.setLayoutParams(params);
            layout.addView(radioGroup); // Add RadioGroup to layout

            // Create RadioButton for Home
            final RadioButton rdHome = new RadioButton(AddressActivity.this);
            rdHome.setText("Home");
            radioGroup.addView(rdHome);

            // Create RadioButton for Work
            final RadioButton rdWork = new RadioButton(AddressActivity.this);
            rdWork.setText("Work");
            radioGroup.addView(rdWork);

            final EditText note = new EditText(AddressActivity.this);
            note.setHint("Ghi chú (nếu có)");
            note.setLayoutParams(params);
            layout.addView(note);

            // Set layout for dialog
            builder.setView(layout);

            builder.setPositiveButton("Thêm", (dialog, which) -> {
                String nameRecipient = name.getText().toString();
                String numberphone = phone.getText().toString();
                String address = building.getText().toString();
                String gates = gate.getText().toString();
                int typeAddress = radioGroup.getCheckedRadioButtonId();
                String notes = note.getText().toString();
                String type = "";

                // Determine the type of address
                if (typeAddress == rdHome.getId()) {
                    type = "Home";
                } else if (typeAddress == rdWork.getId()) {
                    type = "Work";
                }

                // Create new Address object
                Address newAddress = new Address(
                        0,
                        userId,
                        nameRecipient,
                        numberphone,
                        address,
                        gates,
                        type,
                        notes
                );

                boolean isAdded = dao.addAddress(newAddress);
                if (isAdded) {
                    Toast.makeText(AddressActivity.this, "Thêm địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddressActivity.this, "Thêm địa chỉ thất bại!", Toast.LENGTH_SHORT).show();
                }
                // Add address to the database
                reload();
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            // Create and show dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void reload() {
        List<Address> addresses = dao.getAllAddresses(userId);
        AddressAdapter adapter = new AddressAdapter(this, addresses);
        lv_address.setAdapter(adapter);

    }

    private void chooseMethod(Address address){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn tùy chọn")
                .setItems(new CharSequence[]{"Sửa", "Xóa"}, (dialog, which) -> {
                    if (which == 0) {
                        EditAddress(address,userId); // Hiển thị dialog sửa địa chỉ
                    } else if (which == 1) {
                        // Xử lý xóa địa chỉ
                            AlertDialog.Builder dialogs = new AlertDialog.Builder(AddressActivity.this);
                            dialogs.setMessage("Bạn có muốn xóa sản phầm " +  address.getBuilding() + " không?");
                            dialogs.setPositiveButton("Có", (dialogInterface, i) -> {
                                if (dao.deleteAddress(address.getIdAddress())) {
                                    Toast.makeText(this, "Đã xóa địa chỉ: " + address.getBuilding(), Toast.LENGTH_SHORT).show();
                                    reload(); // Tải lại danh sách địa chỉ
                                } else {
                                    Toast.makeText(this, "Không thể xóa địa chỉ!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialogs.setNegativeButton("Không", (dialogInterface, i) -> {
                            });
                            dialogs.show();

                            reload(); // Tải lại danh sách địa chỉ

                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }


    public void EditAddress(Address addressToEdit, int user_id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
            builder.setTitle("Sửa địa chỉ"); // Title

            LinearLayout layout = new LinearLayout(AddressActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(16, 16, 16, 16);

            // Set LayoutParams for EditText
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 30, 30); // Set margins here

            // Create EditText for name
            final EditText name = new EditText(AddressActivity.this);
            name.setHint("Nhập tên người nhận");
            name.setText(addressToEdit.getNameRecipient()); // Set current value
            name.setLayoutParams(params);
            layout.addView(name);

            // Create EditText for phone
            final EditText phone = new EditText(AddressActivity.this);
            phone.setHint("Nhập số điện thoại");
            phone.setInputType(InputType.TYPE_CLASS_PHONE); // Only allow phone input
            phone.setText(addressToEdit.getPhone()); // Set current value
            phone.setLayoutParams(params);
            layout.addView(phone);

            // Create EditText for address
            final EditText building = new EditText(AddressActivity.this);
            building.setHint("Tòa nhà, số tầng...");
            building.setText(addressToEdit.getBuilding()); // Set current value
            building.setLayoutParams(params);
            layout.addView(building);

            // Create EditText for gate
            final EditText gate = new EditText(AddressActivity.this);
            gate.setHint("Cổng(không bắt buộc)");
            gate.setText(addressToEdit.getGate()); // Set current value
            gate.setLayoutParams(params);
            layout.addView(gate);

            // Create RadioGroup for address type
            final RadioGroup radioGroup = new RadioGroup(AddressActivity.this);
            radioGroup.setLayoutParams(params);

            // Create RadioButton for Home
            final RadioButton rdHome = new RadioButton(AddressActivity.this);
            rdHome.setText("Home");
            rdHome.setId(View.generateViewId()); // Generate unique ID for RadioButton
            radioGroup.addView(rdHome);

            // Create RadioButton for Work
            final RadioButton rdWork = new RadioButton(AddressActivity.this);
            rdWork.setText("Work");
            rdWork.setId(View.generateViewId()); // Generate unique ID for RadioButton
            radioGroup.addView(rdWork);

            // Set selected radio button based on existing address type
            if ("Home".equals(addressToEdit.getType_address())) {
                rdHome.setChecked(true);
            } else if ("Work".equals(addressToEdit.getType_address())) {
                rdWork.setChecked(true);
            }

            layout.addView(radioGroup); // Add RadioGroup to layout

            final EditText note = new EditText(AddressActivity.this);
            note.setHint("Ghi chú (nếu có)");
            note.setText(addressToEdit.getNote()); // Set current value
            note.setLayoutParams(params);
            layout.addView(note);

            // Set layout for dialog
            builder.setView(layout);

            builder.setPositiveButton("Lưu", (dialog, which) -> {
                String nameRecipient = name.getText().toString();
                String numberphone = phone.getText().toString();
                String address = building.getText().toString();
                String gates = gate.getText().toString();
                int typeAddress = radioGroup.getCheckedRadioButtonId();
                String notes = note.getText().toString();
                String type = "";

                // Determine the type of address
                if (typeAddress == rdHome.getId()) {
                    type = "Home";
                } else if (typeAddress == rdWork.getId()) {
                    type = "Work";
                }

                // Create updated Address object
                Address updatedAddress = new Address(
                        addressToEdit.getIdAddress(),
                        addressToEdit.getUserID(),// Use existing ID
                        nameRecipient,
                        numberphone,
                        address,
                        gates,
                        type,
                        notes
                );

                boolean isUpdated = dao.updateAddress(updatedAddress,user_id);
                if (isUpdated) {
                    Toast.makeText(AddressActivity.this, "Cập nhật địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddressActivity.this, "Cập nhật địa chỉ thất bại!", Toast.LENGTH_SHORT).show();
                }

                // Reload addresses to reflect changes
                reload();
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            // Create and show dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }




}
