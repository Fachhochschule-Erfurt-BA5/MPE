package com.pme.mpe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pme.mpe.core.MainApplication;
import com.pme.mpe.model.user.User;
import com.pme.mpe.model.util.PasswordHashing;
import com.pme.mpe.storage.repository.UserRepository;

import java.util.List;
import java.util.logging.Logger;


@SuppressWarnings("deprecation")
public class LoginActivity extends AppCompatActivity {
    View mContentView;
    Button loginBtn;
    EditText userName;
    EditText password;
    UserRepository userRepository;
    MainApplication app;
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
        password = findViewById(R.id.input_Passwort);
        loginBtn.setOnClickListener(this.startMainActivityClickListener);

        // get the App Context and instance the User repository
        app = (MainApplication)getApplication();
        userRepository = new UserRepository(app.getApplicationContext());
    }


    private final View.OnClickListener startMainActivityClickListener = v -> {

        if (v.getId() == R.id.enter_button) {

            long UserId = userRepository.logIN(userName.getText().toString(), password.getText().toString());

            if(UserId > 0)
            {
                //save the username in the KVS for the later Usage of the App
                app.putUsername(userName.getText().toString());
                app.storeUserId((int)UserId);

                // forward to the Main Activity
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
            else {
                if(UserId == -1)
                {
                    CharSequence text = "User with that email not found on database ";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(app, text, duration);
                    toast.show();
                    /*********TODO:delete***/
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else if(UserId == -2)
                {
                    CharSequence text = "password wrong, please try again ";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(app, text, duration);
                    toast.show();
                }
            }
        }
    };

}
