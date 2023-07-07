package com.example.project;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    TextView  own1, own2;
    Animation topAnim, bottomAnim;
    ImageView logo;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.imageView);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo.setAnimation(topAnim);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.nudge);
        View view = findViewById(R.id.owntwo);

        ObjectAnimator animators1 = ObjectAnimator.ofFloat(view, "textSize", 20f, 15f);
        animators1.setDuration(300);

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "rotationY", -70f, 0f);
        animator1.setDuration(3000);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "rotationY", 0f, 50f);
        animator2.setDuration(3000);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "rotationY", 50f, 0f);
        animator3.setDuration(500);

        ObjectAnimator animators2 = ObjectAnimator.ofFloat(view, "textSize", 15f, 20f);
        animators2.setDuration(300);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animators1,animator1, animator2, animator3,animators2);
        animatorSet.start();

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
        },7500);
}

}