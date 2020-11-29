package com.lorenzana.hackafest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splashActivity extends Activity {
    //variables for animation
    Animation topAnim;
    ImageView image;
    TextView contactrace, hellohello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(splashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 5000);

        //animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        //hooks
        image = findViewById(R.id.logo_id);
        contactrace = findViewById(R.id.textView2);
        hellohello = findViewById(R.id.textView);

        image.setAnimation(topAnim);
        contactrace.setAnimation(topAnim);
        hellohello.setAnimation(topAnim);
    }
}