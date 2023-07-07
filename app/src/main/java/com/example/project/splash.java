package com.example.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    TextView name, own1, own2;
    Animation topAnim, bottomAnim;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        name = findViewById(R.id.logonameimg);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        name.setAnimation(topAnim);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.nudge);

        Intent intent = new Intent(splash.this,MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                mp.start();

            }
        },870);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(intent);
                mp.stop();

            }
        },4000);
}

}