package com.pme.mpe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


@SuppressWarnings("deprecation")
public class LoginActivity extends AppCompatActivity {
    View mContentView;
    Button loginBtn;
    EditText userName;


    private final View.OnClickListener startMainActivityClickListener = v -> {

        if (v.getId() == R.id.enter_button) {
            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainIntent);
            finish();
        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContentView = findViewById(R.id.login_activity);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        loginBtn = findViewById(R.id.enter_button);
        userName = findViewById(R.id.input_name);

        loginBtn.setOnClickListener(this.startMainActivityClickListener);

    }
}
