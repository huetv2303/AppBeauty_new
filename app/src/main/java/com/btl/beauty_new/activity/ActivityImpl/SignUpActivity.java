package com.btl.beauty_new.activity.ActivityImpl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.btl.beauty_new.R;
import com.btl.beauty_new.repository.DAO;
import com.btl.beauty_new.model.Notify;
import com.btl.beauty_new.model.NotifyToUser;
import com.btl.beauty_new.model.User;

public class SignUpActivity extends AppCompatActivity {
    private EditText txtUsername, txtPassword, txtConfirmPassword;
    public DAO dao;
    private Button btnSignUp;
    private TextView txtSignInApp, checkUsername, checkPassword, checkConfirmPassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        referenceSignUpAccount();
        dao = new DAO(this);
        signInForm();
    }

    private void init() {
        txtPassword = findViewById(R.id.txtPassword);
        txtUsername = findViewById(R.id.txtUsername);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtSignInApp = findViewById(R.id.txtSignInApp);
        checkUsername = findViewById(R.id.checkUsername);
        checkPassword = findViewById(R.id.checkPassword);
        checkConfirmPassword = findViewById(R.id.checkConfirmPassword);
    }

    @SuppressLint("SetTextI18n")
    private void referenceSignUpAccount(){
        btnSignUp.setOnClickListener(v -> {
            String username = txtUsername.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();
            String confirm = txtConfirmPassword.getText().toString().trim();

            if(username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.fill_full_the_information), Toast.LENGTH_SHORT).show();
                return;
            }
            if(!confirm.equals(password)) {
                checkConfirmPassword.setText(getResources().getString(R.string.password_again_failed));
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_again_failed), Toast.LENGTH_SHORT).show();
                return;
            }

            if(dao.UserExited(username)){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.user_existed), Toast.LENGTH_SHORT).show();
                checkUsername.setText(getResources().getString(R.string.account_existed));
            } else if (!dao.UserExited(username) && username.length() >= 8 && password.length() >= 6){
                dao.addUser(new User(null, "", "Male", "12/04/2003", "", username, password));
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.account_create_success), Toast.LENGTH_SHORT).show();

                // Make notify
                dao.addNotify(new Notify(1, getResources().getString(R.string.title_intro),
                        getResources().getString(R.string.contex_intro),
                        dao.getDate()));
                dao.addNotifyToUser(new NotifyToUser(dao.getNewestNotifyId(), dao.getNewestUserId()));

                // Return Login
                Intent intent = new Intent();
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                setResult(0, intent);

                // finish();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });


        // hiện password
        findViewById(R.id.hintPassword).setOnClickListener(v -> togglePasswordVisibility());

        txtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtUsername.getText().toString().length() < 8) {
                    checkUsername.setText(getResources().getString(R.string.USERNAME_LONGER_8_CHARACTER));
                } else {
                    checkUsername.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtPassword.getText().toString().length() < 6) {
                    checkPassword.setText(getResources().getString(R.string.PASSWORD_LONGER_6_CHARACTER));
                } else {
                    checkPassword.setText("");
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


    private void signInForm() {
        txtSignInApp.setOnClickListener(view -> startActivity(new Intent(this, SignInActivity.class)));
    }
}
