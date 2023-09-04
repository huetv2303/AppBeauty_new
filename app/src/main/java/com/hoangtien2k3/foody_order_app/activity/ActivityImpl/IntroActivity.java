package com.hoangtien2k3.foody_order_app.activity.ActivityImpl;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.hoangtien2k3.foody_order_app.R;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.MainActivity;
import com.hoangtien2k3.foody_order_app.activity.ActivityImpl.SignInActivity;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        findViewById(R.id.startBtn).setOnClickListener(view -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
    }
}
