package com.btl.beauty_new.activity.ActivityImpl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.btl.beauty_new.R;
import com.btl.beauty_new.model.Notify;
import com.btl.beauty_new.model.NotifyToUser;
import com.btl.beauty_new.model.User;
import com.btl.beauty_new.repository.DAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserInformationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edUser_name, edUser_phone, edUser_DoB, edUser_password;
    private Spinner spUser_gender;
    private Calendar calendar;
    private String newUser_name, newUser_phone, newUser_DoB, newUser_gender, newUser_password;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        referencesComponent();

//        Toast.makeText(this, HomeActivity.user.getGender(), Toast.LENGTH_SHORT).show();

        edUser_name.setText(HomeActivity.user.getName());
        edUser_phone.setText(HomeActivity.user.getPhone());
        edUser_DoB.setText(HomeActivity.user.getDateOfBirth());
        edUser_password.setText(HomeActivity.user.getPassword());

        switch (HomeActivity.user.getGender()){
            case "Nam":
                spUser_gender.setSelection(0);
                break;
            case "Nữ":
                spUser_gender.setSelection(1);
                break;
            default:
                spUser_gender.setSelection(2);
                break;
        }

        dao = new DAO(this);
    }

    private void referencesComponent(){
        edUser_name = findViewById(R.id.editText_user_name);
        edUser_phone = findViewById(R.id.editText_user_phone);
        edUser_DoB = findViewById(R.id.user_birthday_pick);
        edUser_DoB.setOnClickListener(view -> PickDate());

        edUser_password = findViewById(R.id.editText_user_password);

        spUser_gender = findViewById(R.id.spinner_user_gender);
        ArrayAdapter<CharSequence> genders = ArrayAdapter.createFromResource(this, R.array.genders, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        genders.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spUser_gender.setAdapter(genders);
        spUser_gender.setOnItemSelectedListener(this);

        Button btnChange = findViewById(R.id.btnChangeUserInformation);
        btnChange.setOnClickListener(view -> {
            newUser_name = edUser_name.getText().toString().trim();
            newUser_gender = spUser_gender.getSelectedItem().toString();
            newUser_phone = edUser_phone.getText().toString().trim();
            newUser_DoB = edUser_DoB.getText().toString();
            newUser_password = edUser_password.getText().toString();

            if(newUser_name.isEmpty() || newUser_phone.isEmpty() || newUser_DoB.isEmpty() || newUser_password.isEmpty()){
                Toast.makeText(this, getResources().getString(R.string.fill_full_the_information), Toast.LENGTH_SHORT).show();
            } else {
                String oldPassword = HomeActivity.user.getPassword();
                User editUser = new User(HomeActivity.user.getId(),
                        newUser_name, newUser_gender, newUser_DoB, newUser_phone,
                        HomeActivity.user.getUsername(),
                        newUser_password);
                dao.updateUser(editUser);

                if(!oldPassword.equals(newUser_password)){
                    Toast.makeText(this, getResources().getString(R.string.login_again), Toast.LENGTH_SHORT).show();
                    // Make notify
                    dao.addNotify(new Notify(1, getResources().getString(R.string.password_change),
                            getResources().getString(R.string.login_again_update_information),
                            dao.getDate()));
                    dao.addNotifyToUser(new NotifyToUser(dao.getNewestNotifyId(), dao.getNewestUserId()));
                } else {
                    Toast.makeText(this, getResources().getString(R.string.chang_information_successfully), Toast.LENGTH_SHORT).show();
                }
                HomeActivity.user = editUser;
                finish();
            }
        });

        Button btnCancel = findViewById(R.id.btnCancelChangeUserInformation);
        btnCancel.setOnClickListener(view -> finish());
    }

    private void PickDate(){
        calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, year, month, day) -> {
            calendar.set(year, month, day);
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            edUser_DoB.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spUser_gender.setTextAlignment(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        spUser_gender.setTransitionName(HomeActivity.user.getGender());
    }
}