
package com.example.project;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

        import java.util.Timer;
        import java.util.TimerTask;

public class HomeScreen extends AppCompatActivity {

    TextView name, own1, own2;
    Animation topAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        name = findViewById(R.id.logonameimg);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        name.setAnimation(bottomAnim);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);
        StartHomeScreen();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        StartHomeScreen();
                    }
                }
                ,4000);
    }

    public void StartHomeScreen()
    {
        Intent i=new Intent(HomeScreen.this, MainActivity.class);
        startActivity(i);
    }

}