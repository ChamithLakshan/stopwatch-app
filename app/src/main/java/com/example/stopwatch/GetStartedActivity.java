package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

/**
 * Starts the app with get started button
 * Clicking the Get started button main activity is loaded.
 */
public class GetStartedActivity extends AppCompatActivity {


    private Button mBtn;
    private Animation mFadeAndTranslate;
    private Animation mOnlyFade;
    private ImageView mIcon;
    private TextView mStartText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //Action bar hidden in the get started activity
        Objects.requireNonNull(getSupportActionBar()).hide();

        mBtn = (Button) findViewById(R.id.get_started_button);
        mIcon = (ImageView) findViewById(R.id.watchIcon);
        mStartText =(TextView) findViewById(R.id.startingText);

        //Referring to animations
        mFadeAndTranslate = AnimationUtils.loadAnimation(this,R.anim.fade);
        mOnlyFade =AnimationUtils.loadAnimation(this,R.anim.onlyfade);

        //setting animations on objects
        mBtn.startAnimation(mFadeAndTranslate);
        mIcon.startAnimation(mOnlyFade);
        mStartText.startAnimation(mOnlyFade);

        //Get Started Button
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToStopWatch();

                //Animating the movement between activities
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out_left);
            }
        });
    }

    /**
     * Creates intent to move between activities
     */
    public void goToStopWatch(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}