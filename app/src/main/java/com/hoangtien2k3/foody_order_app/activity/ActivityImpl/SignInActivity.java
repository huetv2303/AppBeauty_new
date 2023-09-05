package com.hoangtien2k3.foody_order_app.activity.ActivityImpl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.repository.DAO;
import com.hoangtien2k3.foody_order_app.model.User;

public class SignInActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText txtUsername, txtPassword;
    private TextView validateUsername, validatePassword, textSignUpApp;
    public static final String PREFERENCES = "store_info";
    private SharedPreferences sharedPreferences;
    public static DAO dao;
    private boolean isPasswordVisible = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initializeUI();
        dao = new DAO(this);

        checkLogin();
        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        txtUsername.setText(sharedPreferences.getString("username", ""));
        txtPassword.setText(sharedPreferences.getString("password", ""));

        setNextActivityListener();
    }

    private void initializeUI() {
        btnLogin = findViewById(R.id.btnLogin);
        txtUsername = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        validateUsername = findViewById(R.id.validateUsername);
        validatePassword = findViewById(R.id.validatePassword);
        textSignUpApp = findViewById(R.id.textSignUpApp);
    }

    @SuppressLint("SetTextI18n")
    private void checkLogin() {
        // đăng nhập
        btnLogin.setOnClickListener(v-> {
            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            User userExist = dao.getUserByUsernameAndPassword(username, password);

            if (!username.isEmpty() && !password.isEmpty()) {
                boolean isRightAuthentication = false;
                if (userExist != null) {
                    isRightAuthentication = dao.signIn(userExist);
                }
                if (isRightAuthentication) { // đăng nhập thành công
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", userExist.getUsername());
                    editor.putString("password", userExist.getPassword());
                    editor.apply();

                    // setup thông báo về rỗng
                    validateUsername.setText("");
                    validatePassword.setText("");

                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    HomeActivity.user = userExist; // truyền cái User đã đăng ký tài khoản vào HomeActivity
                    startActivity(intent); // start nó lên

                } else { // đăng nhập thất bại
                    if (!dao.checkUsername(username)) {
                        validateUsername.setText(getResources().getString(R.string.error_username_login));
                    } else if (!dao.checkPasswordToCurrentUsername(username, password)) {
                        validatePassword.setText(getResources().getString(R.string.error_password_login));
                    }
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_information_login), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignInActivity.this, getResources().getString(R.string.input_full_username_and_password), Toast.LENGTH_SHORT).show();
            }
        });


        // hiện password
        findViewById(R.id.checkPassword).setOnClickListener(v -> togglePasswordVisibility());

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isPasswordVisible) {
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);  // Nếu mật khẩu đang ẩn, đặt lại InputType để ẩn mật khẩu
                    txtPassword.setSelection(txtPassword.getText().length());  // Di chuyển con trỏ đến cuối văn bản để tránh việc mất vị trí khi thay đổi InputType
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }

    // Hàm để hiển thị hoặc ẩn mật khẩu
    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        int inputType = isPasswordVisible
                ? (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                : (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtPassword.setInputType(inputType);

        // Di chuyển con trỏ đến cuối văn bản để tránh việc mất vị trí khi thay đổi InputType
        txtPassword.setSelection(txtPassword.getText().length());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            txtUsername.setText(data.getStringExtra("username"));
            txtPassword.setText(data.getStringExtra("password"));
        }
    }

    private void setNextActivityListener() {
        textSignUpApp.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
