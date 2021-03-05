package com.pme.mpe;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pme.mpe.core.MainApplication;

@SuppressWarnings("deprecation")
public class SplashActivity extends AppCompatActivity {

    Handler handler;
    View mContentView;
    Animation textAnimation, imageAnimation;
    TextView textSplash;
    ImageView imageSplash;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_splash);
       mContentView = findViewById(R.id.splash_activity);
       mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

       textAnimation = AnimationUtils.loadAnimation(this,R.anim.text_splash_animation);
       imageAnimation = AnimationUtils.loadAnimation(this,R.anim.image_splash_animation);
       textSplash = findViewById(R.id.splash_text);
       imageSplash = findViewById(R.id.splash_image);
       textSplash.setAnimation(textAnimation);
       imageSplash.setAnimation(imageAnimation);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainApplication app = (MainApplication)getApplication();

                boolean isFirstUse = app.getStore().getBoolValue("isFirstUse");

                Intent intent;
                if(isFirstUse){
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                }
                else{
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();


            }
        },3000);

    }
}